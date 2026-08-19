package com.codementor.backend.service.impl;

import com.codementor.backend.dto.IndependentSolveSessionResponse;

import com.codementor.backend.entity.IndependentSolveSession;
import com.codementor.backend.entity.Problem;
import com.codementor.backend.entity.Submission;
import com.codementor.backend.entity.User;

import com.codementor.backend.exception.ResourceNotFoundException;
import java.time.ZoneOffset;
import com.codementor.backend.repository.IndependentSolveSessionRepository;
import com.codementor.backend.repository.ProblemRepository;
import com.codementor.backend.repository.SubmissionRepository;
import com.codementor.backend.repository.UserRepository;

import com.codementor.backend.service.IndependentSolveSessionService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class IndependentSolveSessionServiceImpl
        implements IndependentSolveSessionService {

    private final IndependentSolveSessionRepository
            independentSolveSessionRepository;

    private final UserRepository userRepository;

    private final ProblemRepository problemRepository;

    private final SubmissionRepository submissionRepository;


    // ==================================================
    // START SESSION
    // ==================================================

    @Override
    @Transactional
    public IndependentSolveSessionResponse startSession(
            Long problemId,
            String userEmail) {

        User user =
                getUser(userEmail);


        Problem problem =
                problemRepository
                        .findById(problemId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Problem not found with id: "
                                                + problemId
                                )
                        );


        boolean activeSessionExists =
                independentSolveSessionRepository
                        .existsByUserIdAndProblemIdAndActiveTrue(
                                user.getId(),
                                problemId
                        );


        if (activeSessionExists) {

            throw new IllegalStateException(
                    "An independent solve session is already active for this problem."
            );
        }


        IndependentSolveSession session =
                IndependentSolveSession
                        .builder()

                        .user(user)

                        .problem(problem)

                        .active(true)

                        .startedAt(
                                Instant.now()
                        )

                        .submissionsDuringSession(0L)

                        .solvedIndependently(false)

                        .build();


        IndependentSolveSession savedSession =
                independentSolveSessionRepository
                        .save(session);


        return buildResponse(
                savedSession,
                "Independent solving mode started. AI guidance is now locked for this problem."
        );
    }


    // ==================================================
    // GET ACTIVE SESSION
    // ==================================================

    @Override
    @Transactional(readOnly = true)
    public IndependentSolveSessionResponse getActiveSession(
            Long problemId,
            String userEmail) {

        User user =
                getUser(userEmail);


        IndependentSolveSession session =
                independentSolveSessionRepository
                        .findByUserIdAndProblemIdAndActiveTrue(
                                user.getId(),
                                problemId
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "No active independent solve session found."
                                )
                        );


        return buildResponse(
                session,
                "Independent solving mode is active."
        );
    }


    // ==================================================
    // FINISH SESSION
    // ==================================================

    @Override
    @Transactional
    public IndependentSolveSessionResponse finishSession(
            Long problemId,
            String userEmail) {

        User user =
                getUser(userEmail);

        IndependentSolveSession session =
                independentSolveSessionRepository
                        .findByUserIdAndProblemIdAndActiveTrue(
                                user.getId(),
                                problemId
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "No active independent solve session found."
                                )
                        );

        Instant endedAt =
                Instant.now();


        long durationSeconds =
                Duration.between(
                        session.getStartedAt(),
                        endedAt
                ).getSeconds();


        /*
         * Get all submissions for this user.
         *
         * We filter by problem and session time below.
         */

        List<Submission> submissions =
                submissionRepository
                        .findByUserIdOrderByCreatedAtDesc(
                                user.getId()
                        );


        List<Submission> sessionSubmissions =
                submissions
                        .stream()

                        .filter(submission ->
                                submission
                                        .getProblem()
                                        .getId()
                                        .equals(problemId)
                        )

                .filter(submission -> {

                Instant submissionCreatedAt =
                        submission
                                .getCreatedAt()
                                .atOffset(ZoneOffset.UTC)
                                .toInstant();

                return !submissionCreatedAt.isBefore(
                        session.getStartedAt()
                );

                })

                .filter(submission -> {

                Instant submissionCreatedAt =
                        submission
                                .getCreatedAt()
                                .atOffset(ZoneOffset.UTC)
                                .toInstant();

                return !submissionCreatedAt.isAfter(
                        endedAt
                );

                })

                        .toList();


        long submissionsDuringSession =
                sessionSubmissions.size();


        boolean solvedIndependently =
                sessionSubmissions
                        .stream()
                        .anyMatch(submission ->
                                "ACCEPTED".equals(
                                        submission
                                                .getStatus()
                                                .name()
                                )
                        );


        session.setActive(false);

        session.setEndedAt(
                endedAt
        );

        session.setDurationSeconds(
                durationSeconds
        );

        session.setSubmissionsDuringSession(
                submissionsDuringSession
        );

        session.setSolvedIndependently(
                solvedIndependently
        );


        IndependentSolveSession savedSession =
                independentSolveSessionRepository
                        .save(session);


        String message =
                solvedIndependently

                        ? "Excellent! You solved this problem independently without AI guidance."

                        : "Independent solving session completed. Review your attempts and try again after studying the problem concepts.";


        return buildResponse(
                savedSession,
                message
        );
    }


    // ==================================================
    // CHECK ACTIVE SESSION
    // ==================================================

    @Override
    @Transactional(readOnly = true)
    public boolean hasActiveSession(
            Long problemId,
            String userEmail) {

        User user =
                getUser(userEmail);


        return independentSolveSessionRepository
                .existsByUserIdAndProblemIdAndActiveTrue(
                        user.getId(),
                        problemId
                );
    }

        // ==================================================
        // RECORD SUBMISSION DURING ACTIVE SESSION
        // ==================================================

        @Override
        @Transactional
        public void recordSubmission(
                Submission submission,
                String userEmail) {

        User user =
                getUser(userEmail);

        Long problemId =
                submission
                        .getProblem()
                        .getId();

        independentSolveSessionRepository
                .findByUserIdAndProblemIdAndActiveTrue(
                        user.getId(),
                        problemId
                )
                .ifPresent(session -> {

                        Long currentCount =
                                session.getSubmissionsDuringSession();

                        if (currentCount == null) {
                        currentCount = 0L;
                        }

                        session.setSubmissionsDuringSession(
                                currentCount + 1
                        );

                        if (
                                "ACCEPTED".equals(
                                        submission
                                                .getStatus()
                                                .name()
                                )
                        ) {

                        session.setSolvedIndependently(
                                true
                        );
                        }

                        independentSolveSessionRepository
                                .save(session);
                });
        }


    // ==================================================
    // GET USER
    // ==================================================

    private User getUser(
            String userEmail) {

        return userRepository
                .findByEmail(userEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found."
                        )
                );
    }


    // ==================================================
    // BUILD RESPONSE
    // ==================================================

    private IndependentSolveSessionResponse buildResponse(
            IndependentSolveSession session,
            String message) {

        return IndependentSolveSessionResponse
                .builder()

                .problemId(
                        session
                                .getProblem()
                                .getId()
                )

                .active(
                        session.getActive()
                )

                .startedAt(
                        session.getStartedAt()
                )

                .endedAt(
                        session.getEndedAt()
                )

                .durationSeconds(
                        session.getDurationSeconds()
                )

                .submissionsDuringSession(
                        session.getSubmissionsDuringSession()
                )

                .solvedIndependently(
                        session.getSolvedIndependently()
                )

                .message(message)

                .build();
    }

        @Override
        public List<IndependentSolveSessionResponse> getSessionHistory(
                Long problemId,
                String userEmail
        ) {

        User user = userRepository
                .findByEmail(userEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found."
                        )
                );

        Problem problem = problemRepository
                .findById(problemId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Problem not found."
                        )
                );

        List<IndependentSolveSession> sessions =
                independentSolveSessionRepository
                        .findByUserIdAndProblemIdOrderByStartedAtDesc(
                                user.getId(),
                                problem.getId()
                        );

        return sessions
                .stream()
                .map(session ->
                        buildResponse(
                                session,
                                "Independent solve session history."
                        )
                )
                .toList();
        }
}