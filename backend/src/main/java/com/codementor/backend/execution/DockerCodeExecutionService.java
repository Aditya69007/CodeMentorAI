package com.codementor.backend.execution;

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

@Service
public class DockerCodeExecutionService implements CodeExecutionService {

    @Override
    public ExecutionResult execute(
            String sourceCode,
            Language language,
            List<TestCase> testCases) {

        Path tempDirectory = null;

        try {

            tempDirectory = Files.createTempDirectory(
                    "codementor-" + UUID.randomUUID()
            );

            ExecutionResult preparationResult =
                    prepareCode(
                            tempDirectory,
                            sourceCode,
                            language
                    );

            if (preparationResult != null) {
                return preparationResult;
            }

            return runTestCases(
                    tempDirectory,
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
            String sourceCode,
            Language language)
            throws IOException, InterruptedException {

        switch (language) {

            case CPP -> {

                Files.writeString(
                        tempDirectory.resolve("main.cpp"),
                        sourceCode,
                        StandardCharsets.UTF_8
                );

                return compileCode(
                        tempDirectory,
                        "gcc:14",
                        "g++ -std=c++20 /workspace/main.cpp -o /workspace/main"
                );
            }


            case JAVA -> {

                Files.writeString(
                        tempDirectory.resolve("Main.java"),
                        sourceCode,
                        StandardCharsets.UTF_8
                );

                return compileCode(
                        tempDirectory,
                        "eclipse-temurin:21-jdk",
                        "javac /workspace/Main.java"
                );
            }


            case PYTHON -> {

                Files.writeString(
                        tempDirectory.resolve("main.py"),
                        sourceCode,
                        StandardCharsets.UTF_8
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
            Path tempDirectory,
            String dockerImage,
            String compileCommand)
            throws IOException, InterruptedException {

        ProcessBuilder processBuilder =
                createDockerProcess(
                        tempDirectory,
                        dockerImage,
                        compileCommand,
                        "256m",
                        "1"
                );

        Process process = processBuilder.start();

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
            Language language,
            List<TestCase> testCases)
            throws IOException, InterruptedException {


        int totalExecutionTime = 0;

        int passedTestCases = 0;

        int totalTestCases = testCases.size();


        for (int i = 0; i < testCases.size(); i++) {


            TestCase testCase =
                    testCases.get(i);


            Files.writeString(
                    tempDirectory.resolve("input.txt"),
                    testCase.getInput(),
                    StandardCharsets.UTF_8
            );


            String dockerImage =
                    getDockerImage(language);


            String runCommand =
                    getRunCommand(language);


            long startTime =
                    System.currentTimeMillis();


            ProcessBuilder processBuilder =
                    createDockerProcess(
                            tempDirectory,
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

                        // Do not expose hidden test error
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

                        // Never expose output for hidden test
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


            // Current test passed
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
    // CREATE DOCKER PROCESS
    // ==================================================

    private ProcessBuilder createDockerProcess(
            Path tempDirectory,
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

                tempDirectory
                        .toAbsolutePath()
                        + ":/workspace",

                dockerImage,

                "sh",

                "-c",

                command
        );
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