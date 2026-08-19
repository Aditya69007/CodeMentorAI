package com.codementor.backend.execution;

import com.codementor.backend.dto.ExecutionResult;
import com.codementor.backend.entity.Language;
import com.codementor.backend.entity.SubmissionStatus;
import com.codementor.backend.entity.TestCase;

import com.fasterxml.jackson.databind.JsonNode;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@ConditionalOnProperty(
        name = "execution.provider",
        havingValue = "remote"
)
public class Judge0CodeExecutionService
        implements CodeExecutionService {


    private final RestClient restClient;


    public Judge0CodeExecutionService(

            @Value("${execution.judge0.url:https://ce.judge0.com}")
            String judge0Url

    ) {

        this.restClient =
                RestClient.builder()
                        .baseUrl(judge0Url)
                        .build();

    }


    // ==================================================
    // EXECUTE
    // ==================================================

    @Override
    public ExecutionResult execute(

            String sourceCode,

            Language language,

            List<TestCase> testCases

    ) {


        int passedTestCases = 0;

        int totalTestCases =
                testCases.size();

        int totalExecutionTime = 0;

        int maximumMemoryUsed = 0;


        for (

                int i = 0;

                i < testCases.size();

                i++

        ) {


            TestCase testCase =
                    testCases.get(i);


            boolean hidden =
                    Boolean.TRUE.equals(
                            testCase.getHidden()
                    );


            try {


                JsonNode result =
                        executeTestCase(

                                sourceCode,

                                language,

                                testCase.getInput(),

                                testCase.getExpectedOutput()

                        );


                int statusId =
                        result
                                .path("status")
                                .path("id")
                                .asInt();


                String output =
                        result
                                .path("stdout")
                                .asText("")
                                .trim();


                String stderr =
                        result
                                .path("stderr")
                                .asText("")
                                .trim();


                String compileOutput =
                        result
                                .path("compile_output")
                                .asText("")
                                .trim();


                int executionTime =
                        convertExecutionTime(
                                result.path("time").asText()
                        );


                int memoryUsed =
                        result
                                .path("memory")
                                .asInt(0);


                totalExecutionTime +=
                        executionTime;


                maximumMemoryUsed =
                        Math.max(

                                maximumMemoryUsed,

                                memoryUsed

                        );


                // ==========================================
                // ACCEPTED
                // ==========================================

                if (statusId == 3) {

                    passedTestCases++;

                    continue;

                }


                // ==========================================
                // WRONG ANSWER
                // ==========================================

                if (statusId == 4) {

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

                            .memoryUsed(
                                    maximumMemoryUsed
                            )

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


                // ==========================================
                // TIME LIMIT EXCEEDED
                // ==========================================

                if (statusId == 5) {

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

                            .memoryUsed(
                                    maximumMemoryUsed
                            )

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


                // ==========================================
                // COMPILATION ERROR
                // ==========================================

                if (statusId == 6) {

                    return ExecutionResult.builder()

                            .status(
                                    SubmissionStatus.COMPILATION_ERROR
                            )

                            .errorMessage(
                                    compileOutput.isBlank()

                                            ? stderr

                                            : compileOutput
                            )

                            .executionTime(
                                    totalExecutionTime
                            )

                            .memoryUsed(
                                    maximumMemoryUsed
                            )

                            .passedTestCases(
                                    0
                            )

                            .totalTestCases(
                                    totalTestCases
                            )

                            .failedOnHiddenTest(
                                    false
                            )

                            .build();

                }


                // ==========================================
                // RUNTIME ERROR
                // ==========================================

                return ExecutionResult.builder()

                        .status(
                                SubmissionStatus.RUNTIME_ERROR
                        )

                        .output(
                                hidden
                                        ? null
                                        : output
                        )

                        .errorMessage(

                                hidden

                                        ? "Runtime error on hidden test case"

                                        : getErrorMessage(
                                                stderr,
                                                result
                                        )

                        )

                        .executionTime(
                                totalExecutionTime
                        )

                        .memoryUsed(
                                maximumMemoryUsed
                        )

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


            } catch (Exception exception) {


                return ExecutionResult.builder()

                        .status(
                                SubmissionStatus.RUNTIME_ERROR
                        )

                        .errorMessage(
                                "Code execution service error: "
                                + exception.getMessage()
                        )

                        .executionTime(
                                totalExecutionTime
                        )

                        .memoryUsed(
                                maximumMemoryUsed
                        )

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

        }


        // ==========================================
        // ALL TEST CASES PASSED
        // ==========================================

        return ExecutionResult.builder()

                .status(
                        SubmissionStatus.ACCEPTED
                )

                .executionTime(
                        totalExecutionTime
                )

                .memoryUsed(
                        maximumMemoryUsed
                )

                .passedTestCases(
                        passedTestCases
                )

                .totalTestCases(
                        totalTestCases
                )

                .failedOnHiddenTest(
                        false
                )

                .build();

    }


    // ==================================================
    // EXECUTE SINGLE TEST CASE
    // ==================================================

    private JsonNode executeTestCase(

            String sourceCode,

            Language language,

            String input,

            String expectedOutput

    ) throws InterruptedException {


        Map<String, Object> request =
                new HashMap<>();


        request.put(

                "source_code",

                sourceCode

        );


        request.put(

                "language_id",

                getLanguageId(language)

        );


        request.put(

                "stdin",

                input

        );


        request.put(

                "expected_output",

                expectedOutput

        );


        request.put(

                "cpu_time_limit",

                5

        );


        request.put(

                "wall_time_limit",

                10

        );


        request.put(

                "memory_limit",

                131072

        );


        JsonNode submission =

                restClient

                        .post()

                        .uri(
                                "/submissions?base64_encoded=false"
                        )

                        .contentType(
                                MediaType.APPLICATION_JSON
                        )

                        .body(
                                request
                        )

                        .retrieve()

                        .body(
                                JsonNode.class
                        );


        if (

                submission == null

                || submission.path("token").isMissingNode()

        ) {

            throw new IllegalStateException(

                    "Judge0 did not return an execution token"

            );

        }


        String token =
                submission
                        .path("token")
                        .asText();


        // ==========================================
        // POLL FOR RESULT
        // ==========================================

        for (

                int attempt = 0;

                attempt < 100;

                attempt++

        ) {


            Thread.sleep(100);


            JsonNode result =

                    restClient

                            .get()

                            .uri(

                                    "/submissions/{token}"

                                            + "?base64_encoded=false",

                                    token

                            )

                            .retrieve()

                            .body(
                                    JsonNode.class
                            );


            if (result == null) {

                continue;

            }


            int statusId =

                    result

                            .path("status")

                            .path("id")

                            .asInt();


            // 1 = In Queue
            // 2 = Processing

            if (

                    statusId != 1

                    && statusId != 2

            ) {

                return result;

            }

        }


        throw new IllegalStateException(
                "Code execution timed out"
        );

    }


    // ==================================================
    // LANGUAGE MAPPING
    // ==================================================

    private int getLanguageId(
            Language language
    ) {

        return switch (language) {

            case CPP -> 105;

            case JAVA -> 91;

            case PYTHON -> 109;

            default ->

                    throw new IllegalArgumentException(
                            "Unsupported language: "
                            + language
                    );

        };

    }


    // ==================================================
    // EXECUTION TIME
    // ==================================================

    private int convertExecutionTime(
            String time
    ) {

        try {

            if (

                    time == null

                    || time.isBlank()

            ) {

                return 0;

            }


        return (int) (
                Double.parseDouble(time) * 1000
        );

        } catch (NumberFormatException exception) {

            return 0;

        }

    }


    // ==================================================
    // ERROR MESSAGE
    // ==================================================

    private String getErrorMessage(

            String stderr,

            JsonNode result

    ) {


        if (

                stderr != null

                && !stderr.isBlank()

        ) {

            return stderr;

        }


        String message =

                result

                        .path("message")

                        .asText("");


        if (!message.isBlank()) {

            return message;

        }


        return

                result

                        .path("status")

                        .path("description")

                        .asText(
                                "Runtime error"
                        );

    }

}