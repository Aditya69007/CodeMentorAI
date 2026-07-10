package com.codementor.backend.service.impl;

import com.codementor.backend.dto.ExecutionResult;
import com.codementor.backend.dto.SubmissionRequest;
import com.codementor.backend.dto.SubmissionResponse;
import com.codementor.backend.entity.*;
import com.codementor.backend.exception.ResourceNotFoundException;
import com.codementor.backend.execution.CodeExecutionService;
import com.codementor.backend.repository.ProblemRepository;
import com.codementor.backend.repository.SubmissionRepository;
import com.codementor.backend.repository.TestCaseRepository;
import com.codementor.backend.repository.UserRepository;
import com.codementor.backend.service.SubmissionService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubmissionServiceImpl implements SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final UserRepository userRepository;
    private final ProblemRepository problemRepository;
    private final TestCaseRepository testCaseRepository;
    private final CodeExecutionService codeExecutionService;


    // ==================================================
    // CREATE SUBMISSION
    // ==================================================

    @Override
    public SubmissionResponse createSubmission(
            SubmissionRequest request,
            String userEmail) {

        // Find logged-in user
        User user = userRepository
                .findByEmail(userEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        )
                );


        // Find problem
        Problem problem = problemRepository
                .findById(request.getProblemId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Problem not found with id: "
                                        + request.getProblemId()
                        )
                );


        // Create submission
        Submission submission = Submission.builder()
                .sourceCode(request.getSourceCode())
                .language(request.getLanguage())
                .status(SubmissionStatus.PENDING)
                .user(user)
                .problem(problem)
                .build();


        // Save pending submission
        submission = submissionRepository.save(submission);


        // Get all test cases
        List<TestCase> testCases =
                testCaseRepository.findByProblemId(
                        problem.getId()
                );


        // Check test cases
        if (testCases.isEmpty()) {

            throw new ResourceNotFoundException(
                    "No test cases found for problem id: "
                            + problem.getId()
            );
        }


        // Change status to RUNNING
        submission.setStatus(
                SubmissionStatus.RUNNING
        );

        submissionRepository.save(submission);


        // Execute code
        ExecutionResult result =
                codeExecutionService.execute(
                        submission.getSourceCode(),
                        submission.getLanguage(),
                        testCases
                );


        // Store execution result
        submission.setStatus(
                result.getStatus()
        );

        submission.setExecutionTime(
                result.getExecutionTime()
        );

        submission.setMemoryUsed(
                result.getMemoryUsed()
        );

        submission.setOutput(
                result.getOutput()
        );

        submission.setErrorMessage(
                result.getErrorMessage()
        );

        submission.setPassedTestCases(
                result.getPassedTestCases()
        );

        submission.setTotalTestCases(
                result.getTotalTestCases()
        );

        submission.setFailedOnHiddenTest(
                result.getFailedOnHiddenTest()
        );


        // Save final result
        submission =
                submissionRepository.save(submission);


        // Return DTO
        return mapToResponse(submission);
    }


    // ==================================================
    // GET SUBMISSION BY ID
    // ==================================================

    @Override
    @Transactional(readOnly = true)
    public SubmissionResponse getSubmissionById(
            Long id,
            String userEmail) {

        Submission submission =
                submissionRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Submission not found with id: "
                                                + id
                                )
                        );


        // User can only access their own submission
        if (!submission
                .getUser()
                .getEmail()
                .equals(userEmail)) {

            throw new ResourceNotFoundException(
                    "Submission not found with id: "
                            + id
            );
        }


        return mapToResponse(submission);
    }


    // ==================================================
    // GET ALL LOGGED-IN USER SUBMISSIONS
    // ==================================================

    @Override
    @Transactional(readOnly = true)
    public Page<SubmissionResponse> getMySubmissions(
            String userEmail,
            int page,
            int size) {

        User user = userRepository
                .findByEmail(userEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        )
                );


        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("createdAt").descending()
        );


        return submissionRepository
                .findByUserId(
                        user.getId(),
                        pageable
                )
                .map(this::mapToResponse);
    }


    // ==================================================
    // GET LOGGED-IN USER SUBMISSIONS FOR ONE PROBLEM
    // ==================================================

    @Override
    @Transactional(readOnly = true)
    public Page<SubmissionResponse> getMyProblemSubmissions(
            Long problemId,
            String userEmail,
            int page,
            int size) {

        // Find logged-in user
        User user = userRepository
                .findByEmail(userEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        )
                );


        // Check whether problem exists
        if (!problemRepository.existsById(problemId)) {

            throw new ResourceNotFoundException(
                    "Problem not found with id: "
                            + problemId
            );
        }


        // Create pagination
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("createdAt").descending()
        );


        // Get submissions using User ID + Problem ID
        return submissionRepository
                .findByUserIdAndProblemId(
                        user.getId(),
                        problemId,
                        pageable
                )
                .map(this::mapToResponse);
    }


    // ==================================================
    // CONVERT SUBMISSION ENTITY → RESPONSE DTO
    // ==================================================

    private SubmissionResponse mapToResponse(
            Submission submission) {

        return SubmissionResponse.builder()

                .id(
                        submission.getId()
                )

                .problemId(
                        submission
                                .getProblem()
                                .getId()
                )

                .problemTitle(
                        submission
                                .getProblem()
                                .getTitle()
                )
                .sourceCode(
                        submission.getSourceCode()
                )

                .output(
                        submission.getOutput()
                )

                .errorMessage(
                        submission.getErrorMessage()
                )

                .language(
                        submission.getLanguage()
                )

                .status(
                        submission.getStatus()
                )

                .executionTime(
                        submission.getExecutionTime()
                )

                .memoryUsed(
                        submission.getMemoryUsed()
                )

                .passedTestCases(
                        submission.getPassedTestCases()
                )

                .totalTestCases(
                        submission.getTotalTestCases()
                )

                .failedOnHiddenTest(
                        submission.getFailedOnHiddenTest()
                )

                .createdAt(
                        submission.getCreatedAt()
                )

                .build();
    }
}