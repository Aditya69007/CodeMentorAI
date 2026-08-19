package com.codementor.backend.execution;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import com.codementor.backend.dto.ExecutionResult;
import com.codementor.backend.entity.Language;
import com.codementor.backend.entity.SubmissionStatus;
import com.codementor.backend.entity.TestCase;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

//@Service
@ConditionalOnProperty(
        name = "execution.provider",
        havingValue = "docker",
        matchIfMissing = true
)
public class DockerCodeExecutionService
        implements CodeExecutionService {

    @Override
    public ExecutionResult execute(
            String sourceCode,
            Language language,
            List<TestCase> testCases) {

        Path tempDirectory = null;
        String volumeName = "codementor-execution-" + UUID.randomUUID();

        try {

            // ==================================================
            // CREATE TEMP DIRECTORY
            // ==================================================

            tempDirectory = Files.createTempDirectory(
                    "codementor-" + UUID.randomUUID()
            );

            // ==================================================
            // CREATE DOCKER VOLUME
            // ==================================================

            createDockerVolume(volumeName);

            // ==================================================
            // PREPARE SOURCE CODE
            // ==================================================

            ExecutionResult preparationResult =
                    prepareCode(
                            tempDirectory,
                            volumeName,
                            sourceCode,
                            language
                    );

            if (preparationResult != null) {
                return preparationResult;
            }

            // ==================================================
            // RUN TEST CASES
            // ==================================================

            return runTestCases(
                    tempDirectory,
                    volumeName,
                    language,
                    testCases
            );

        } catch (InterruptedException exception) {

            Thread.currentThread().interrupt();

            return ExecutionResult.builder()
                    .status(SubmissionStatus.RUNTIME_ERROR)
                    .errorMessage("Execution interrupted")
                    .build();

        } catch (IOException exception) {

            return ExecutionResult.builder()
                    .status(SubmissionStatus.RUNTIME_ERROR)
                    .errorMessage(exception.getMessage())
                    .build();

        } finally {

            // ==================================================
            // REMOVE DOCKER VOLUME
            // ==================================================

            removeDockerVolume(volumeName);

            // ==================================================
            // REMOVE TEMP DIRECTORY
            // ==================================================

            if (tempDirectory != null) {
                deleteDirectory(tempDirectory);
            }
        }
    }


    // ==================================================
    // PREPARE SOURCE CODE
    // ==================================================

    private ExecutionResult prepareCode(
            Path tempDirectory,
            String volumeName,
            String sourceCode,
            Language language)
            throws IOException, InterruptedException {

        switch (language) {

            case CPP -> {

                Path sourceFile =
                        tempDirectory.resolve("main.cpp");

                Files.writeString(
                        sourceFile,
                        sourceCode,
                        StandardCharsets.UTF_8
                );

                copyFileToDockerVolume(
                        volumeName,
                        sourceFile,
                        "main.cpp"
                );

                return compileCode(
                        volumeName,
                        "gcc:14",
                        "g++ -std=c++20 /workspace/main.cpp -o /workspace/main"
                );
            }


            case JAVA -> {

                Path sourceFile =
                        tempDirectory.resolve("Main.java");

                Files.writeString(
                        sourceFile,
                        sourceCode,
                        StandardCharsets.UTF_8
                );

                copyFileToDockerVolume(
                        volumeName,
                        sourceFile,
                        "Main.java"
                );

                return compileCode(
                        volumeName,
                        "eclipse-temurin:21-jdk",
                        "javac /workspace/Main.java"
                );
            }


            case PYTHON -> {

                Path sourceFile =
                        tempDirectory.resolve("main.py");

                Files.writeString(
                        sourceFile,
                        sourceCode,
                        StandardCharsets.UTF_8
                );

                copyFileToDockerVolume(
                        volumeName,
                        sourceFile,
                        "main.py"
                );

                // Python does not require compilation
                return null;
            }


            default -> {

                return ExecutionResult.builder()
                        .status(SubmissionStatus.COMPILATION_ERROR)
                        .errorMessage(
                                "Language currently not supported: "
                                        + language
                        )
                        .build();
            }
        }
    }


    // ==================================================
    // COMPILE C++ / JAVA
    // ==================================================

    private ExecutionResult compileCode(
            String volumeName,
            String dockerImage,
            String compileCommand)
            throws IOException, InterruptedException {

        ProcessBuilder processBuilder =
                createDockerProcess(
                        volumeName,
                        dockerImage,
                        compileCommand,
                        "256m",
                        "1"
                );

        Process process =
                processBuilder.start();

        boolean finished =
                process.waitFor(
                        15,
                        TimeUnit.SECONDS
                );

        if (!finished) {

            process.destroyForcibly();

            return ExecutionResult.builder()
                    .status(
                            SubmissionStatus.COMPILATION_ERROR
                    )
                    .errorMessage(
                            "Compilation timed out"
                    )
                    .passedTestCases(0)
                    .totalTestCases(0)
                    .failedOnHiddenTest(false)
                    .build();
        }

        String error =
                new String(
                        process
                                .getErrorStream()
                                .readAllBytes(),
                        StandardCharsets.UTF_8
                ).trim();

        if (process.exitValue() != 0) {

            return ExecutionResult.builder()
                    .status(
                            SubmissionStatus.COMPILATION_ERROR
                    )
                    .errorMessage(error)
                    .passedTestCases(0)
                    .totalTestCases(0)
                    .failedOnHiddenTest(false)
                    .build();
        }

        return null;
    }


    // ==================================================
    // RUN ALL TEST CASES
    // ==================================================

    private ExecutionResult runTestCases(
            Path tempDirectory,
            String volumeName,
            Language language,
            List<TestCase> testCases)
            throws IOException, InterruptedException {

        int totalExecutionTime = 0;

        int passedTestCases = 0;

        int totalTestCases =
                testCases.size();


        for (int i = 0;
             i < testCases.size();
             i++) {

            TestCase testCase =
                    testCases.get(i);


            // ==================================================
            // WRITE INPUT FILE
            // ==================================================

            Path inputFile =
                    tempDirectory.resolve("input.txt");

            Files.writeString(
                    inputFile,
                    testCase.getInput(),
                    StandardCharsets.UTF_8
            );

            copyFileToDockerVolume(
                    volumeName,
                    inputFile,
                    "input.txt"
            );


            String dockerImage =
                    getDockerImage(language);


            String runCommand =
                    getRunCommand(language);


            long startTime =
                    System.currentTimeMillis();


            ProcessBuilder processBuilder =
                    createDockerProcess(
                            volumeName,
                            dockerImage,
                            runCommand,
                            "128m",
                            "0.5"
                    );


            Process process =
                    processBuilder.start();


            boolean finished =
                    process.waitFor(
                            10,
                            TimeUnit.SECONDS
                    );


            // ==================================================
            // EXECUTION PROCESS TIMEOUT
            // ==================================================

            if (!finished) {

                process.destroyForcibly();

                boolean hidden =
                        Boolean.TRUE.equals(
                                testCase.getHidden()
                        );

                return ExecutionResult.builder()
                        .status(
                                SubmissionStatus.TIME_LIMIT_EXCEEDED
                        )
                        .errorMessage(
                                hidden
                                        ? "Time limit exceeded on hidden test case"
                                        : "Time limit exceeded on test case "
                                        + (i + 1)
                        )
                        .executionTime(
                                totalExecutionTime
                        )
                        .memoryUsed(0)
                        .passedTestCases(
                                passedTestCases
                        )
                        .totalTestCases(
                                totalTestCases
                        )
                        .failedOnHiddenTest(
                                hidden
                        )
                        .build();
            }


            String output =
                    new String(
                            process
                                    .getInputStream()
                                    .readAllBytes(),
                            StandardCharsets.UTF_8
                    ).trim();


            String error =
                    new String(
                            process
                                    .getErrorStream()
                                    .readAllBytes(),
                            StandardCharsets.UTF_8
                    ).trim();


            int currentExecutionTime =
                    (int) (
                            System.currentTimeMillis()
                                    - startTime
                    );


            totalExecutionTime +=
                    currentExecutionTime;


            int exitCode =
                    process.exitValue();


            boolean hidden =
                    Boolean.TRUE.equals(
                            testCase.getHidden()
                    );


            // ==================================================
            // TIME LIMIT EXCEEDED
            // ==================================================

            if (exitCode == 124) {

                return ExecutionResult.builder()
                        .status(
                                SubmissionStatus.TIME_LIMIT_EXCEEDED
                        )
                        .errorMessage(
                                hidden
                                        ? "Time limit exceeded on hidden test case"
                                        : "Time limit exceeded on test case "
                                        + (i + 1)
                        )
                        .executionTime(
                                totalExecutionTime
                        )
                        .memoryUsed(0)
                        .passedTestCases(
                                passedTestCases
                        )
                        .totalTestCases(
                                totalTestCases
                        )
                        .failedOnHiddenTest(
                                hidden
                        )
                        .build();
            }


            // ==================================================
            // RUNTIME ERROR
            // ==================================================

            if (exitCode != 0) {

                return ExecutionResult.builder()
                        .status(
                                SubmissionStatus.RUNTIME_ERROR
                        )
                        .errorMessage(
                                hidden
                                        ? "Runtime error on hidden test case"
                                        : error
                        )
                        .executionTime(
                                totalExecutionTime
                        )
                        .memoryUsed(0)
                        .passedTestCases(
                                passedTestCases
                        )
                        .totalTestCases(
                                totalTestCases
                        )
                        .failedOnHiddenTest(
                                hidden
                        )
                        .build();
            }


            // ==================================================
            // COMPARE OUTPUT
            // ==================================================

            String expectedOutput =
                    testCase
                            .getExpectedOutput()
                            .trim();


            if (!output.equals(expectedOutput)) {

                return ExecutionResult.builder()
                        .status(
                                SubmissionStatus.WRONG_ANSWER
                        )
                        .output(
                                hidden
                                        ? null
                                        : output
                        )
                        .errorMessage(
                                hidden
                                        ? "Wrong answer on hidden test case"
                                        : "Wrong answer on test case "
                                        + (i + 1)
                        )
                        .executionTime(
                                totalExecutionTime
                        )
                        .memoryUsed(0)
                        .passedTestCases(
                                passedTestCases
                        )
                        .totalTestCases(
                                totalTestCases
                        )
                        .failedOnHiddenTest(
                                hidden
                        )
                        .build();
            }


            // ==================================================
            // CURRENT TEST PASSED
            // ==================================================

            passedTestCases++;
        }


        // ==================================================
        // ALL TEST CASES PASSED
        // ==================================================

        return ExecutionResult.builder()
                .status(
                        SubmissionStatus.ACCEPTED
                )
                .executionTime(
                        totalExecutionTime
                )
                .memoryUsed(0)
                .passedTestCases(
                        passedTestCases
                )
                .totalTestCases(
                        totalTestCases
                )
                .failedOnHiddenTest(false)
                .build();
    }


    // ==================================================
    // CREATE DOCKER VOLUME
    // ==================================================

    private void createDockerVolume(
            String volumeName)
            throws IOException, InterruptedException {

        Process process =
                new ProcessBuilder(
                        "docker",
                        "volume",
                        "create",
                        volumeName
                ).start();

        boolean finished =
                process.waitFor(
                        15,
                        TimeUnit.SECONDS
                );

        if (!finished) {

            process.destroyForcibly();

            throw new IOException(
                    "Docker volume creation timed out"
            );
        }

        if (process.exitValue() != 0) {

            String error =
                    new String(
                            process
                                    .getErrorStream()
                                    .readAllBytes(),
                            StandardCharsets.UTF_8
                    ).trim();

            throw new IOException(
                    "Failed to create Docker volume: "
                            + error
            );
        }
    }


    // ==================================================
    // COPY FILE INTO DOCKER VOLUME
    // ==================================================

    private void copyFileToDockerVolume(
            String volumeName,
            Path sourceFile,
            String targetFile)
            throws IOException, InterruptedException {

        ProcessBuilder processBuilder =
                new ProcessBuilder(
                        "docker",
                        "run",
                        "--rm",
                        "-i",
                        "-v",
                        volumeName + ":/workspace",
                        "alpine:3.20",
                        "sh",
                        "-c",
                        "cat > /workspace/" + targetFile
                );

        Process process =
                processBuilder.start();

        byte[] content =
                Files.readAllBytes(sourceFile);

        process.getOutputStream().write(content);
        process.getOutputStream().close();

        boolean finished =
                process.waitFor(
                        15,
                        TimeUnit.SECONDS
                );

        if (!finished) {

            process.destroyForcibly();

            throw new IOException(
                    "Copying file to Docker volume timed out"
            );
        }

        if (process.exitValue() != 0) {

            String error =
                    new String(
                            process
                                    .getErrorStream()
                                    .readAllBytes(),
                            StandardCharsets.UTF_8
                    ).trim();

            throw new IOException(
                    "Failed to copy file to Docker volume: "
                            + error
            );
        }
    }


    // ==================================================
    // CREATE DOCKER PROCESS
    // ==================================================

    private ProcessBuilder createDockerProcess(
            String volumeName,
            String dockerImage,
            String command,
            String memory,
            String cpus) {

        return new ProcessBuilder(
                "docker",
                "run",
                "--rm",
                "--network",
                "none",
                "--memory",
                memory,
                "--cpus",
                cpus,
                "-v",
                volumeName + ":/workspace",
                dockerImage,
                "sh",
                "-c",
                command
        );
    }


    // ==================================================
    // REMOVE DOCKER VOLUME
    // ==================================================

    private void removeDockerVolume(
            String volumeName) {

        try {

            Process process =
                    new ProcessBuilder(
                            "docker",
                            "volume",
                            "rm",
                            "-f",
                            volumeName
                    ).start();

            process.waitFor(
                    15,
                    TimeUnit.SECONDS
            );

        } catch (Exception ignored) {
        }
    }


    // ==================================================
    // GET DOCKER IMAGE
    // ==================================================

    private String getDockerImage(
            Language language) {

        return switch (language) {

            case CPP ->
                    "gcc:14";

            case JAVA ->
                    "eclipse-temurin:21-jdk";

            case PYTHON ->
                    "python:3.13-slim";

            default ->
                    throw new IllegalArgumentException(
                            "Unsupported language"
                    );
        };
    }


    // ==================================================
    // GET RUN COMMAND
    // ==================================================

    private String getRunCommand(
            Language language) {

        return switch (language) {

            case CPP ->
                    "timeout 5s /workspace/main < /workspace/input.txt";

            case JAVA ->
                    "timeout 5s java -cp /workspace Main < /workspace/input.txt";

            case PYTHON ->
                    "timeout 5s python /workspace/main.py < /workspace/input.txt";

            default ->
                    throw new IllegalArgumentException(
                            "Unsupported language"
                    );
        };
    }


    // ==================================================
    // DELETE TEMP DIRECTORY
    // ==================================================

    private void deleteDirectory(
            Path directory) {

        try (var paths =
                     Files.walk(directory)) {

            paths.sorted(
                            (first, second) ->
                                    second.compareTo(first)
                    )
                    .forEach(path -> {

                        try {

                            Files.deleteIfExists(path);

                        } catch (IOException ignored) {
                        }

                    });

        } catch (IOException ignored) {
        }
    }
}