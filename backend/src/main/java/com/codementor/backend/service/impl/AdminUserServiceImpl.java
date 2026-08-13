package com.codementor.backend.service.impl;

import com.codementor.backend.dto.AdminRecentSubmissionResponse;
import com.codementor.backend.dto.AdminUserActivityResponse;
import com.codementor.backend.dto.AdminUserDetailResponse;
import com.codementor.backend.dto.AdminUserSummaryResponse;
import com.codementor.backend.dto.AdminUserTopicPerformanceResponse;

import com.codementor.backend.entity.Role;
import com.codementor.backend.entity.Submission;
import com.codementor.backend.entity.SubmissionStatus;
import com.codementor.backend.entity.User;

import com.codementor.backend.exception.ResourceNotFoundException;

import com.codementor.backend.repository.AiAnalysisRepository;
import com.codementor.backend.repository.AiMistakeRepository;
import com.codementor.backend.repository.SubmissionRepository;
import com.codementor.backend.repository.UserRepository;

import com.codementor.backend.service.AdminUserService;
import com.codementor.backend.repository.IndependentSolveSessionRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl
        implements AdminUserService {

    private final UserRepository userRepository;

    private final SubmissionRepository submissionRepository;

    private final AiAnalysisRepository aiAnalysisRepository;

    private final AiMistakeRepository aiMistakeRepository;

    private final IndependentSolveSessionRepository independentSolveSessionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<AdminUserSummaryResponse> getAllUsers() {

        return userRepository
                .findByRoleOrderByCreatedAtDesc(Role.USER)
                .stream()
                .map(this::mapToSummaryResponse)
                .toList();
    }


    @Override
    @Transactional(readOnly = true)
    public AdminUserDetailResponse getUserDetail(
            Long userId
    ) {

        User user = userRepository
                .findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id: " + userId
                        )
                );


        if (user.getRole() != Role.USER) {

            throw new ResourceNotFoundException(
                    "Regular user not found with id: " + userId
            );
        }


        long totalSubmissions =
                submissionRepository.countByUserId(userId);


        long acceptedSubmissions =
                submissionRepository.countByUserIdAndStatus(
                        userId,
                        SubmissionStatus.ACCEPTED
                );


        long solvedProblems =
                submissionRepository
                        .countDistinctSolvedProblemsByUserId(
                                userId
                        );


        long totalAiAnalyses =
                aiAnalysisRepository
                        .countBySubmissionUserId(userId);


        long totalMistakes =
                aiMistakeRepository.countByUserId(userId);


        double acceptanceRate =
                calculatePercentage(
                        acceptedSubmissions,
                        totalSubmissions
                );


        return AdminUserDetailResponse.builder()

                .id(user.getId())

                .firstName(user.getFirstName())

                .lastName(user.getLastName())

                .email(user.getEmail())

                .profilePicture(user.getProfilePicture())

                .enabled(user.getEnabled())

                .createdAt(user.getCreatedAt())

                .totalSubmissions(totalSubmissions)

                .solvedProblems(solvedProblems)

                .acceptedSubmissions(acceptedSubmissions)

                .acceptanceRate(acceptanceRate)

                .totalAiAnalyses(totalAiAnalyses)

                .totalMistakes(totalMistakes)

                .submissionStatusDistribution(
                        getSubmissionStatusDistribution(userId)
                )

                .mistakeTypeDistribution(
                        getMistakeTypeDistribution(userId)
                )

                .submissionActivity(
                        getSubmissionActivity(userId)
                )

                .topicPerformance(
                        getTopicPerformance(userId)
                )

                .recentSubmissions(
                        getRecentSubmissions(userId)
                )

                .build();
    }


    private AdminUserSummaryResponse mapToSummaryResponse(
            User user
    ) {

        long totalSubmissions =
                submissionRepository.countByUserId(
                        user.getId()
                );


        long acceptedSubmissions =
                submissionRepository.countByUserIdAndStatus(
                        user.getId(),
                        SubmissionStatus.ACCEPTED
                );


        long solvedProblems =
                submissionRepository
                        .countDistinctSolvedProblemsByUserId(
                                user.getId()
                        );


        long totalAiAnalyses =
                aiAnalysisRepository
                        .countBySubmissionUserId(
                                user.getId()
                        );


        long totalMistakes =
                aiMistakeRepository.countByUserId(
                        user.getId()
                );


        return AdminUserSummaryResponse.builder()

                .id(user.getId())

                .firstName(user.getFirstName())

                .lastName(user.getLastName())

                .email(user.getEmail())

                .profilePicture(user.getProfilePicture())

                .enabled(user.getEnabled())

                .createdAt(user.getCreatedAt())

                .totalSubmissions(totalSubmissions)

                .solvedProblems(solvedProblems)

                .acceptedSubmissions(acceptedSubmissions)

                .acceptanceRate(
                        calculatePercentage(
                                acceptedSubmissions,
                                totalSubmissions
                        )
                )

                .totalAiAnalyses(totalAiAnalyses)

                .totalMistakes(totalMistakes)

                .build();
    }


    private Map<String, Long>
    getSubmissionStatusDistribution(
            Long userId
    ) {

        Map<String, Long> distribution =
                new LinkedHashMap<>();


        for (SubmissionStatus status
                : SubmissionStatus.values()) {

            distribution.put(
                    status.name(),
                    0L
            );
        }


        List<Object[]> results =
                submissionRepository
                        .findStatusDistributionByUserId(
                                userId
                        );


        for (Object[] result : results) {

            SubmissionStatus status =
                    (SubmissionStatus) result[0];

            Long count =
                    (Long) result[1];

            distribution.put(
                    status.name(),
                    count
            );
        }


        return distribution;
    }


    private Map<String, Long>
    getMistakeTypeDistribution(
            Long userId
    ) {

        Map<String, Long> distribution =
                new LinkedHashMap<>();


        List<Object[]> results =
                aiMistakeRepository
                        .findMistakeSummaryByUserId(
                                userId
                        );


        for (Object[] result : results) {

            String mistakeType =
                    result[0].toString();

            Long count =
                    (Long) result[1];

            distribution.put(
                    mistakeType,
                    count
            );
        }


        return distribution;
    }


    private List<AdminUserActivityResponse>
    getSubmissionActivity(
            Long userId
    ) {

        LocalDate today =
                LocalDate.now();

        LocalDate startDate =
                today.minusDays(6);


        List<Submission> submissions =
                submissionRepository
                        .findByUserIdAndCreatedAtGreaterThanEqualOrderByCreatedAtAsc(
                                userId,
                                startDate.atStartOfDay()
                        );


        Map<LocalDate, Long> activity =
                new LinkedHashMap<>();


        for (int i = 0; i < 7; i++) {

            activity.put(
                    startDate.plusDays(i),
                    0L
            );
        }


        for (Submission submission : submissions) {

            LocalDate submissionDate =
                    submission
                            .getCreatedAt()
                            .toLocalDate();

            activity.computeIfPresent(
                    submissionDate,
                    (date, count) -> count + 1
            );
        }


        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern(
                        "MMM dd"
                );


        return activity
                .entrySet()
                .stream()
                .map(entry ->
                        AdminUserActivityResponse.builder()

                                .date(
                                        entry.getKey()
                                                .format(formatter)
                                )

                                .submissions(
                                        entry.getValue()
                                )

                                .build()
                )
                .toList();
    }


    private List<AdminUserTopicPerformanceResponse>
    getTopicPerformance(
            Long userId
    ) {

        List<Object[]> performanceResults =
                submissionRepository
                        .findTopicPerformanceByUserId(
                                userId
                        );


        List<Object[]> mistakeResults =
                aiMistakeRepository
                        .findMistakeCountByTopicForUser(
                                userId
                        );


        Map<Long, Long> mistakeCountByTopic =
                new HashMap<>();


        for (Object[] result : mistakeResults) {

            Long topicId =
                    (Long) result[0];

            Long mistakeCount =
                    (Long) result[1];

            mistakeCountByTopic.put(
                    topicId,
                    mistakeCount
            );
        }


        List<AdminUserTopicPerformanceResponse>
                responses = new ArrayList<>();


        for (Object[] result : performanceResults) {

            Long topicId =
                    (Long) result[0];

            String topicName =
                    (String) result[1];

            Long totalSubmissions =
                    (Long) result[2];

            Long acceptedSubmissions =
                    (Long) result[3];


            responses.add(

                    AdminUserTopicPerformanceResponse
                            .builder()

                            .topicId(topicId)

                            .topicName(topicName)

                            .totalSubmissions(
                                    totalSubmissions
                            )

                            .acceptedSubmissions(
                                    acceptedSubmissions
                            )

                            .mistakes(
                                    mistakeCountByTopic
                                            .getOrDefault(
                                                    topicId,
                                                    0L
                                            )
                            )

                            .acceptanceRate(
                                    calculatePercentage(
                                            acceptedSubmissions,
                                            totalSubmissions
                                    )
                            )

                            .build()
            );
        }


        return responses;
    }


    private List<AdminRecentSubmissionResponse>
    getRecentSubmissions(
            Long userId
    ) {

        return submissionRepository
                .findTop10ByUserIdOrderByCreatedAtDesc(
                        userId
                )
                .stream()
                .map(submission ->

                        AdminRecentSubmissionResponse.builder()

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

                                .topicName(
                                        submission
                                                        .getProblem()
                                                        .getTopic()
                                                == null

                                                ? null

                                                : submission
                                                        .getProblem()
                                                        .getTopic()
                                                        .getName()
                                )

                                .difficulty(
                                        submission
                                                .getProblem()
                                                .getDifficulty()
                                                .name()
                                )

                                .language(
                                        submission
                                                .getLanguage()
                                                .name()
                                )

                                .status(
                                        submission
                                                .getStatus()
                                                .name()
                                )

                                .passedTestCases(
                                        submission
                                                .getPassedTestCases()
                                )

                                .totalTestCases(
                                        submission
                                                .getTotalTestCases()
                                )

                                .executionTime(
                                        submission
                                                .getExecutionTime()
                                )

                                .createdAt(
                                        submission
                                                .getCreatedAt()
                                )

                                .build()
                )
                .toList();
    }


    private double calculatePercentage(
            long value,
            long total
    ) {

        if (total == 0) {
            return 0.0;
        }


        double percentage =
                ((double) value / total)
                        * 100.0;


        return Math.round(
                percentage * 100.0
        ) / 100.0;
    }

        @Override
        @Transactional
        public void deleteUser(Long userId) {

        User user = userRepository
                .findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id: " + userId
                        )
                );


        if (user.getRole() != Role.USER) {

                throw new IllegalArgumentException(
                        "Administrator accounts cannot be deleted from user management."
                );
        }


        aiMistakeRepository.deleteByUserId(userId);

        aiAnalysisRepository.deleteBySubmissionUserId(userId);

        independentSolveSessionRepository.deleteByUserId(userId);

        submissionRepository.deleteByUserId(userId);

        userRepository.delete(user);
        }
}