package com.codementor.backend.service.impl;

import com.codementor.backend.dto.activity.DailyActivityResponse;
import com.codementor.backend.dto.activity.DeveloperActivityResponse;
import com.codementor.backend.entity.Submission;
import com.codementor.backend.entity.SubmissionStatus;
import com.codementor.backend.entity.User;
import com.codementor.backend.exception.ResourceNotFoundException;
import com.codementor.backend.repository.SubmissionRepository;
import com.codementor.backend.repository.UserRepository;
import com.codementor.backend.service.DeveloperActivityService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DeveloperActivityServiceImpl
        implements DeveloperActivityService {

    private final UserRepository userRepository;

    private final SubmissionRepository submissionRepository;


    @Override
    @Transactional(readOnly = true)
    public DeveloperActivityResponse getMyActivity(
            String userEmail
    ) {

        // ==================================================
        // GET LOGGED-IN USER
        // ==================================================

        User user =
                userRepository
                        .findByEmail(userEmail)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found."
                                )
                        );


        // ==================================================
        // ACTIVITY RANGE
        //
        // Last 365 days including today.
        // ==================================================

        LocalDate today =
                LocalDate.now();

        LocalDate startDate =
                today.minusDays(364);


        LocalDateTime startDateTime =
                startDate.atStartOfDay();

        LocalDateTime endDateTime =
                today
                        .plusDays(1)
                        .atStartOfDay();


        // ==================================================
        // GET REAL USER SUBMISSIONS
        // ==================================================

        List<Submission> submissions =
                submissionRepository
                        .findByUserIdAndCreatedAtBetweenOrderByCreatedAtAsc(
                                user.getId(),
                                startDateTime,
                                endDateTime
                        );


        // ==================================================
        // GROUP SUBMISSIONS BY DATE
        // ==================================================

        Map<LocalDate, List<Submission>> submissionsByDate =
                new LinkedHashMap<>();


        for (Submission submission : submissions) {

            LocalDate activityDate =
                    submission
                            .getCreatedAt()
                            .toLocalDate();


            submissionsByDate
                    .computeIfAbsent(
                            activityDate,
                            date -> new ArrayList<>()
                    )
                    .add(submission);
        }


        // ==================================================
        // BUILD ALL 365 CALENDAR DAYS
        //
        // IMPORTANT:
        // We return zero-activity dates too.
        // This makes frontend calendar rendering easier.
        // ==================================================

        List<DailyActivityResponse> activity =
                new ArrayList<>();


        LocalDate currentDate =
                startDate;


        while (!currentDate.isAfter(today)) {

            List<Submission> dailySubmissions =
                    submissionsByDate.getOrDefault(
                            currentDate,
                            List.of()
                    );


            long acceptedSubmissionCount =
                    dailySubmissions
                            .stream()
                            .filter(submission ->
                                    submission.getStatus()
                                            == SubmissionStatus.ACCEPTED
                            )
                            .count();


            activity.add(

                    DailyActivityResponse
                            .builder()

                            .date(
                                    currentDate
                            )

                            .submissionCount(
                                    dailySubmissions.size()
                            )

                            .acceptedSubmissionCount(
                                    acceptedSubmissionCount
                            )

                            .build()
            );


            currentDate =
                    currentDate.plusDays(1);
        }


        // ==================================================
        // ACTIVE DAYS
        // ==================================================

        long totalActiveDays =
                activity
                        .stream()

                        .filter(day ->
                                day.getSubmissionCount() > 0
                        )

                        .count();


        // ==================================================
        // CURRENT STREAK
        // ==================================================

        int currentStreak =
                calculateCurrentStreak(
                        activity,
                        today
                );


        // ==================================================
        // LONGEST STREAK
        // ==================================================

        int longestStreak =
                calculateLongestStreak(
                        activity
                );


        // ==================================================
        // BUILD RESPONSE
        // ==================================================

        return DeveloperActivityResponse
                .builder()

                .currentStreak(
                        currentStreak
                )

                .longestStreak(
                        longestStreak
                )

                .totalActiveDays(
                        totalActiveDays
                )

                .totalSubmissions(
                        submissions.size()
                )

                .activity(
                        activity
                )

                .build();
    }


    // ======================================================
    // CURRENT STREAK CALCULATION
    // ======================================================

    private int calculateCurrentStreak(
            List<DailyActivityResponse> activity,
            LocalDate today
    ) {

        if (activity.isEmpty()) {
            return 0;
        }


        int currentStreak = 0;


        int index =
                activity.size() - 1;


        /*
         * If user has no activity today,
         * allow streak calculation from yesterday.
         *
         * Example:
         *
         * Monday = active
         * Tuesday = active
         * Wednesday = today, no activity yet
         *
         * Current streak should still be 2.
         */

        DailyActivityResponse todayActivity =
                activity.get(index);


        if (
                todayActivity
                        .getSubmissionCount()
                        == 0
        ) {

            index--;
        }


        while (index >= 0) {

            DailyActivityResponse day =
                    activity.get(index);


            if (day.getSubmissionCount() == 0) {
                break;
            }


            currentStreak++;

            index--;
        }


        return currentStreak;
    }


    // ======================================================
    // LONGEST STREAK CALCULATION
    // ======================================================

    private int calculateLongestStreak(
            List<DailyActivityResponse> activity
    ) {

        int longestStreak = 0;

        int runningStreak = 0;


        for (DailyActivityResponse day : activity) {

            if (day.getSubmissionCount() > 0) {

                runningStreak++;

                longestStreak =
                        Math.max(
                                longestStreak,
                                runningStreak
                        );

            } else {

                runningStreak = 0;
            }
        }


        return longestStreak;
    }
}