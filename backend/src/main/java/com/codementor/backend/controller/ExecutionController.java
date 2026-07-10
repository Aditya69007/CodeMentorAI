package com.codementor.backend.controller;

import com.codementor.backend.dto.ExecutionResult;
import com.codementor.backend.dto.RunCodeRequest;

import com.codementor.backend.entity.Problem;
import com.codementor.backend.entity.ProblemExample;
import com.codementor.backend.entity.TestCase;

import com.codementor.backend.exception.ResourceNotFoundException;

import com.codementor.backend.execution.CodeExecutionService;

import com.codementor.backend.repository.ProblemExampleRepository;
import com.codementor.backend.repository.ProblemRepository;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/executions")
@RequiredArgsConstructor
public class ExecutionController {


    private final CodeExecutionService codeExecutionService;

    private final ProblemRepository problemRepository;

    private final ProblemExampleRepository
            problemExampleRepository;


    // ==================================================
    // RUN SELECTED EXAMPLE TESTCASE
    // ==================================================

    @PostMapping("/run")
    public ResponseEntity<ExecutionResult> runCode(

            @Valid
            @RequestBody
            RunCodeRequest request) {


        // ==================================================
        // FIND PROBLEM
        // ==================================================

        Problem problem = problemRepository

                .findById(
                        request.getProblemId()
                )

                .orElseThrow(() ->

                        new ResourceNotFoundException(

                                "Problem not found with id: "
                                        + request.getProblemId()

                        )

                );


        // ==================================================
        // FIND SELECTED EXAMPLE
        // ==================================================

        ProblemExample example =
                problemExampleRepository

                        .findById(
                                request.getExampleId()
                        )

                        .orElseThrow(() ->

                                new ResourceNotFoundException(

                                        "Problem example not found with id: "
                                                + request.getExampleId()

                                )

                        );


        // ==================================================
        // SECURITY / DATA VALIDATION
        //
        // MAKE SURE EXAMPLE BELONGS TO PROBLEM
        // ==================================================

        if (

                !example
                        .getProblem()
                        .getId()
                        .equals(
                                problem.getId()
                        )

        ) {

            throw new ResourceNotFoundException(

                    "Example does not belong to problem id: "
                            + problem.getId()

            );

        }


        // ==================================================
        // CONVERT EXAMPLE TO TEMPORARY TESTCASE
        //
        // IMPORTANT:
        //
        // THIS TESTCASE IS NOT SAVED TO DATABASE.
        // ==================================================

        TestCase selectedTestCase =

                TestCase
                        .builder()

                        .input(
                                example.getInput()
                        )

                        .expectedOutput(
                                example.getOutput()
                        )

                        .hidden(false)

                        .problem(problem)

                        .build();


        // ==================================================
        // CREATE SINGLE TESTCASE LIST
        // ==================================================

        List<TestCase> selectedTestCases =

                List.of(
                        selectedTestCase
                );


        // ==================================================
        // EXECUTE SELECTED TESTCASE
        // ==================================================

        ExecutionResult result =

                codeExecutionService.execute(

                        request.getSourceCode(),

                        request.getLanguage(),

                        selectedTestCases

                );


        // ==================================================
        // IMPORTANT
        //
        // RUN DOES NOT CREATE SUBMISSION
        //
        // RUN DOES NOT UPDATE PROGRESS
        //
        // RUN DOES NOT TRIGGER MISTAKE MEMORY
        //
        // ==================================================

        return ResponseEntity.ok(result);
    }
}