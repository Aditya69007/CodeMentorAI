package com.codementor.backend.service.impl;

import com.codementor.backend.dto.*;
import com.codementor.backend.entity.*;
import com.codementor.backend.exception.ResourceNotFoundException;
import com.codementor.backend.execution.CodeExecutionService;
import com.codementor.backend.repository.*;
import com.codementor.backend.service.IndependentSolveSessionService;
import com.codementor.backend.service.SubmissionService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.codementor.backend.service.IndependentSolveSessionService;

import java.util.List;


@Service
@RequiredArgsConstructor
public class SubmissionServiceImpl
        implements SubmissionService {


    private final SubmissionRepository submissionRepository;

    private final UserRepository userRepository;

    private final ProblemRepository problemRepository;

    private final TestCaseRepository testCaseRepository;

    private final CodeExecutionService codeExecutionService;

    private final IndependentSolveSessionService independentSolveSessionService;


    // ==================================================
    // CREATE SUBMISSION
    // ==================================================

    @Override
    public SubmissionResponse createSubmission(
            SubmissionRequest request,
            String userEmail
    ) {

        User user =
                userRepository
                        .findByEmail(userEmail)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found"
                                )
                        );


        Problem problem =
                problemRepository
                        .findById(request.getProblemId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Problem not found with id: "
                                                + request.getProblemId()
                                )
                        );


        Submission submission =
                Submission
                        .builder()

                        .sourceCode(
                                request.getSourceCode()
                        )

                        .language(
                                request.getLanguage()
                        )

                        .status(
                                SubmissionStatus.PENDING
                        )

                        .user(user)

                        .problem(problem)

                        .build();


        submission =
                submissionRepository.save(submission);


        List<TestCase> testCases =
                testCaseRepository
                        .findByProblemId(
                                problem.getId()
                        );


        if (testCases.isEmpty()) {

            throw new ResourceNotFoundException(
                    "No test cases found for problem id: "
                            + problem.getId()
            );
        }


        submission.setStatus(
                SubmissionStatus.RUNNING
        );


        submissionRepository.save(submission);


        ExecutionResult result =
                codeExecutionService.execute(

                        submission.getSourceCode(),

                        submission.getLanguage(),

                        testCases
                );


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


        submission =
                submissionRepository.save(submission);


        // ==================================================
        // TRACK INDEPENDENT SOLVE SESSION SUBMISSION
        // ==================================================

        independentSolveSessionService
                .recordSubmission(
                        submission,
                        userEmail
                );


        return mapToResponse(submission);
        }



    // ==================================================
    // GET USER SUBMISSION BY ID
    // ==================================================

    @Override
    @Transactional(readOnly = true)
    public SubmissionResponse getSubmissionById(

            Long id,

            String userEmail
    ) {

        Submission submission =
                submissionRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Submission not found with id: "
                                                + id
                                )
                        );


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
    // GET MY SUBMISSIONS
    // ==================================================

    @Override
    @Transactional(readOnly = true)
    public Page<SubmissionResponse> getMySubmissions(

            String userEmail,

            int page,

            int size
    ) {

        User user =
                userRepository
                        .findByEmail(userEmail)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found"
                                )
                        );


        Pageable pageable =
                PageRequest.of(

                        page,

                        size,

                        Sort.by("createdAt")
                                .descending()
                );


        return submissionRepository
                .findByUserId(

                        user.getId(),

                        pageable
                )

                .map(this::mapToResponse);
    }



    // ==================================================
    // GET MY SUBMISSIONS FOR ONE PROBLEM
    // ==================================================

    @Override
    @Transactional(readOnly = true)
    public Page<SubmissionResponse>
    getMyProblemSubmissions(

            Long problemId,

            String userEmail,

            int page,

            int size
    ) {

        User user =
                userRepository
                        .findByEmail(userEmail)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found"
                                )
                        );


        if (!problemRepository.existsById(problemId)) {

            throw new ResourceNotFoundException(
                    "Problem not found with id: "
                            + problemId
            );
        }


        Pageable pageable =
                PageRequest.of(

                        page,

                        size,

                        Sort.by("createdAt")
                                .descending()
                );


        return submissionRepository
                .findByUserIdAndProblemId(

                        user.getId(),

                        problemId,

                        pageable
                )

                .map(this::mapToResponse);
    }



    // ==================================================
    // FILTER SUBMISSIONS - ADMIN
    // ==================================================

    @Override
    @Transactional(readOnly = true)
    public Page<AdminSubmissionResponse>
    filterSubmissionsForAdmin(

            String search,

            SubmissionStatus status,

            Language language,

            int page,

            int size
    ) {

        Pageable pageable =
                PageRequest.of(

                        page,

                        size,

                        Sort.by(
                                Sort.Direction.DESC,
                                "createdAt"
                        )
                );


        String normalizedSearch =
                search == null
                        ? ""
                        : search.trim();


        return submissionRepository
                .filterAdminSubmissions(

                        normalizedSearch,

                        status,

                        language,

                        pageable
                )

                .map(this::mapToAdminResponse);
    }



    // ==================================================
    // GET SUBMISSION DETAILS - ADMIN
    // ==================================================

    @Override
    @Transactional(readOnly = true)
    public AdminSubmissionDetailsResponse
    getSubmissionDetailsForAdmin(
            Long id
    ) {

        Submission submission =
                submissionRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Submission not found."
                                )
                        );


        return mapToAdminSubmissionDetailsResponse(
                submission
        );
    }



    // ==================================================
    // MAP USER SUBMISSION RESPONSE
    // ==================================================

    private SubmissionResponse mapToResponse(
            Submission submission
    ) {

        return SubmissionResponse
                .builder()

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



    // ==================================================
    // MAP ADMIN SUBMISSION LIST RESPONSE
    // ==================================================

    private AdminSubmissionResponse mapToAdminResponse(
            Submission submission
    ) {

        User user =
                submission.getUser();


        return AdminSubmissionResponse
                .builder()

                .id(
                        submission.getId()
                )

                .userId(
                        user.getId()
                )

                .userName(
                        user.getFirstName()
                                + " "
                                + user.getLastName()
                )

                .userEmail(
                        user.getEmail()
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

                .language(
                        submission.getLanguage()
                )

                .status(
                        submission.getStatus()
                )

                .passedTestCases(
                        submission.getPassedTestCases()
                )

                .totalTestCases(
                        submission.getTotalTestCases()
                )

                .executionTime(
                        submission.getExecutionTime()
                )

                .memoryUsed(
                        submission.getMemoryUsed()
                )

                .failedOnHiddenTest(
                        submission.getFailedOnHiddenTest()
                )

                .createdAt(
                        submission.getCreatedAt()
                )

                .build();
    }



    // ==================================================
    // MAP ADMIN SUBMISSION DETAILS RESPONSE
    // ==================================================

    private AdminSubmissionDetailsResponse
    mapToAdminSubmissionDetailsResponse(
            Submission submission
    ) {

        User user =
                submission.getUser();


        return AdminSubmissionDetailsResponse
                .builder()

                .id(
                        submission.getId()
                )

                .userId(
                        user.getId()
                )

                .userName(
                        user.getFirstName()
                                + " "
                                + user.getLastName()
                )

                .userEmail(
                        user.getEmail()
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

                .passedTestCases(
                        submission.getPassedTestCases()
                )

                .totalTestCases(
                        submission.getTotalTestCases()
                )

                .executionTime(
                        submission.getExecutionTime()
                )

                .memoryUsed(
                        submission.getMemoryUsed()
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