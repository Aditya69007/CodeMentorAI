package com.codementor.backend.service.impl;

import com.codementor.backend.dto.AdminDashboardAnalyticsResponse;
import com.codementor.backend.dto.AdminDashboardStatsResponse;
import com.codementor.backend.dto.AdminPlatformAnalyticsResponse;
import com.codementor.backend.dto.DailySubmissionStatsResponse;

import com.codementor.backend.entity.Difficulty;
import com.codementor.backend.entity.Language;
import com.codementor.backend.entity.Role;
import com.codementor.backend.entity.Submission;
import com.codementor.backend.entity.SubmissionStatus;

import com.codementor.backend.repository.AiAnalysisRepository;
import com.codementor.backend.repository.ProblemRepository;
import com.codementor.backend.repository.SubmissionRepository;
import com.codementor.backend.repository.TopicRepository;
import com.codementor.backend.repository.UserRepository;

import com.codementor.backend.service.AdminDashboardService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class AdminDashboardServiceImpl
        implements AdminDashboardService {


    private final UserRepository userRepository;

    private final ProblemRepository problemRepository;

    private final TopicRepository topicRepository;

    private final SubmissionRepository submissionRepository;

    private final AiAnalysisRepository aiAnalysisRepository;



    // ==================================================
    // DASHBOARD STATS
    // ==================================================

    @Override
    @Transactional(readOnly = true)
    public AdminDashboardStatsResponse getDashboardStats() {


        long totalUsers =
                userRepository.countByRole(
                        Role.USER
                );


        long totalAdmins =
                userRepository.countByRole(
                        Role.ADMIN
                );


        long totalProblems =
                problemRepository.count();


        long totalTopics =
                topicRepository.count();


        long totalSubmissions =
                submissionRepository.count();


        long acceptedSubmissions =
                submissionRepository.countByStatus(
                        SubmissionStatus.ACCEPTED
                );


        long totalAiAnalyses =
                aiAnalysisRepository.count();


        return AdminDashboardStatsResponse
                .builder()

                .totalUsers(totalUsers)

                .totalAdmins(totalAdmins)

                .totalProblems(totalProblems)

                .totalTopics(totalTopics)

                .totalSubmissions(totalSubmissions)

                .acceptedSubmissions(
                        acceptedSubmissions
                )

                .totalAiAnalyses(
                        totalAiAnalyses
                )

                .build();
    }



    // ==================================================
    // DASHBOARD ANALYTICS
    // ==================================================

    @Override
    @Transactional(readOnly = true)
    public AdminDashboardAnalyticsResponse
    getDashboardAnalytics() {


        List<DailySubmissionStatsResponse>
                submissionActivity =
                buildSubmissionActivity();


        Map<String, Long>
                submissionStatusDistribution =
                buildSubmissionStatusDistribution();


        Map<String, Long>
                difficultyDistribution =
                buildDifficultyDistribution();


        long totalSubmissions =
                submissionRepository.count();


        long acceptedSubmissions =
                submissionRepository.countByStatus(
                        SubmissionStatus.ACCEPTED
                );


        double acceptanceRate =
                calculatePercentage(
                        acceptedSubmissions,
                        totalSubmissions
                );


        long totalAiAnalyses =
                aiAnalysisRepository.count();


        double aiAnalysisCoverage =
                calculatePercentage(
                        totalAiAnalyses,
                        totalSubmissions
                );


        return AdminDashboardAnalyticsResponse
                .builder()

                .submissionActivity(
                        submissionActivity
                )

                .submissionStatusDistribution(
                        submissionStatusDistribution
                )

                .difficultyDistribution(
                        difficultyDistribution
                )

                .acceptanceRate(
                        acceptanceRate
                )

                .aiAnalysisCoverage(
                        aiAnalysisCoverage
                )

                .build();
    }



    // ==================================================
    // PLATFORM ANALYTICS
    // ==================================================

    @Override
    @Transactional(readOnly = true)
    public AdminPlatformAnalyticsResponse
    getPlatformAnalytics() {


        // ------------------------------------------
        // USERS
        // ------------------------------------------

        long totalUsers =
                userRepository.countByRole(
                        Role.USER
                );


        // ------------------------------------------
        // PROBLEMS
        // ------------------------------------------

        long totalProblems =
                problemRepository.count();


        // ------------------------------------------
        // TOPICS
        // ------------------------------------------

        long totalTopics =
                topicRepository.count();


        // ------------------------------------------
        // SUBMISSIONS
        // ------------------------------------------

        long totalSubmissions =
                submissionRepository.count();


        long acceptedSubmissions =
                submissionRepository.countByStatus(
                        SubmissionStatus.ACCEPTED
                );


        // ------------------------------------------
        // ACCEPTANCE RATE
        // ------------------------------------------

        double acceptanceRate =
                calculatePercentage(
                        acceptedSubmissions,
                        totalSubmissions
                );


        // ------------------------------------------
        // AI ANALYSES
        // ------------------------------------------

        long totalAiAnalyses =
                aiAnalysisRepository.count();


        double aiAnalysisCoverage =
                calculatePercentage(
                        totalAiAnalyses,
                        totalSubmissions
                );


        // ------------------------------------------
        // SUBMISSION ACTIVITY
        // ------------------------------------------

        List<DailySubmissionStatsResponse>
                submissionActivity =
                buildSubmissionActivity();


        // ------------------------------------------
        // STATUS DISTRIBUTION
        // ------------------------------------------

        Map<String, Long>
                submissionStatusDistribution =
                buildSubmissionStatusDistribution();


        // ------------------------------------------
        // LANGUAGE DISTRIBUTION
        // ------------------------------------------

        Map<String, Long>
                languageDistribution =
                buildLanguageDistribution();


        // ------------------------------------------
        // DIFFICULTY DISTRIBUTION
        // ------------------------------------------

        Map<String, Long>
                difficultyDistribution =
                buildDifficultyDistribution();



        return AdminPlatformAnalyticsResponse
                .builder()

                .totalUsers(
                        totalUsers
                )

                .totalProblems(
                        totalProblems
                )

                .totalTopics(
                        totalTopics
                )

                .totalSubmissions(
                        totalSubmissions
                )

                .acceptedSubmissions(
                        acceptedSubmissions
                )

                .acceptanceRate(
                        acceptanceRate
                )

                .totalAiAnalyses(
                        totalAiAnalyses
                )

                .aiAnalysisCoverage(
                        aiAnalysisCoverage
                )

                .submissionActivity(
                        submissionActivity
                )

                .submissionStatusDistribution(
                        submissionStatusDistribution
                )

                .languageDistribution(
                        languageDistribution
                )

                .difficultyDistribution(
                        difficultyDistribution
                )

                .build();
    }



    // ==================================================
    // BUILD 7 DAY SUBMISSION ACTIVITY
    // ==================================================

    private List<DailySubmissionStatsResponse>
    buildSubmissionActivity() {


        LocalDate today =
                LocalDate.now();


        LocalDate startDay =
                today.minusDays(6);


        LocalDateTime startDate =
                startDay.atStartOfDay();


        List<Submission> recentSubmissions =
                submissionRepository
                        .findByCreatedAtGreaterThanEqualOrderByCreatedAtAsc(
                                startDate
                        );


        Map<LocalDate, Long>
                submissionsByDate =
                new LinkedHashMap<>();


        for (int day = 0; day < 7; day++) {

            submissionsByDate.put(
                    startDay.plusDays(day),
                    0L
            );
        }


        for (Submission submission :
                recentSubmissions) {


            LocalDate submissionDate =
                    submission
                            .getCreatedAt()
                            .toLocalDate();


            submissionsByDate.computeIfPresent(

                    submissionDate,

                    (date, count) ->
                            count + 1

            );
        }


        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern(
                        "MMM dd"
                );


        return submissionsByDate
                .entrySet()
                .stream()

                .map(entry ->

                        DailySubmissionStatsResponse
                                .builder()

                                .date(
                                        entry
                                                .getKey()
                                                .format(
                                                        formatter
                                                )
                                )

                                .submissions(
                                        entry.getValue()
                                )

                                .build()
                )

                .toList();
    }



    // ==================================================
    // BUILD SUBMISSION STATUS DISTRIBUTION
    // ==================================================

    private Map<String, Long>
    buildSubmissionStatusDistribution() {


        Map<String, Long>
                distribution =
                new LinkedHashMap<>();


        for (SubmissionStatus status :
                SubmissionStatus.values()) {


            distribution.put(

                    status.name(),

                    submissionRepository
                            .countByStatus(
                                    status
                            )

            );
        }


        return distribution;
    }



    // ==================================================
    // BUILD LANGUAGE DISTRIBUTION
    // ==================================================

    private Map<String, Long>
    buildLanguageDistribution() {


        Map<String, Long>
                distribution =
                new LinkedHashMap<>();


        // Add every language first.
        // This ensures languages with zero submissions
        // are also returned to the frontend.

        for (Language language :
                Language.values()) {

            distribution.put(
                    language.name(),
                    0L
            );
        }


        List<Object[]> results =
                submissionRepository
                        .findLanguageDistribution();


        for (Object[] result :
                results) {


            Language language =
                    (Language) result[0];


            Long count =
                    (Long) result[1];


            distribution.put(
                    language.name(),
                    count
            );
        }


        return distribution;
    }



    // ==================================================
    // BUILD PROBLEM DIFFICULTY DISTRIBUTION
    // ==================================================

    private Map<String, Long>
    buildDifficultyDistribution() {


        Map<String, Long>
                distribution =
                new LinkedHashMap<>();


        for (Difficulty difficulty :
                Difficulty.values()) {


            distribution.put(

                    difficulty.name(),

                    problemRepository
                            .countByDifficulty(
                                    difficulty
                            )

            );
        }


        return distribution;
    }



    // ==================================================
    // CALCULATE PERCENTAGE
    // ==================================================

    private double calculatePercentage(
            long value,
            long total
    ) {


        if (total == 0) {

            return 0;
        }


        double percentage =
                (
                        (double) value
                                / total
                ) * 100;


        return roundPercentage(
                percentage
        );
    }



    // ==================================================
    // ROUND PERCENTAGE
    // ==================================================

    private double roundPercentage(
            double value
    ) {

        return Math.round(
                value * 100.0
        ) / 100.0;
    }
}