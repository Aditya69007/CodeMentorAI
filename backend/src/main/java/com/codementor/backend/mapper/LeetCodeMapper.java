package com.codementor.backend.mapper;

import com.codementor.backend.dto.leetcode.graphql.badges.BadgesResponse;
import com.codementor.backend.dto.leetcode.graphql.contest.ContestRanking;
import com.codementor.backend.dto.leetcode.graphql.contest.ContestResponse;
import com.codementor.backend.dto.leetcode.response.BadgeInfo;
import com.codementor.backend.dto.leetcode.response.ContestInfo;
import com.codementor.backend.dto.leetcode.response.ProblemStats;
import com.codementor.backend.dto.leetcode.graphql.calendar.CalendarResponse;
import com.codementor.backend.dto.leetcode.calculator.CalendarCalculator;
import com.codementor.backend.dto.leetcode.graphql.calendar.UserCalendar;
import com.codementor.backend.dto.leetcode.response.CalendarInfo;
import com.codementor.backend.dto.leetcode.graphql.problems.ProblemProgressResponse;
import com.codementor.backend.dto.leetcode.graphql.problems.SubmissionStat;

import com.codementor.backend.dto.leetcode.graphql.skills.SkillCategory;
import com.codementor.backend.dto.leetcode.graphql.skills.SkillStatsResponse;
import com.codementor.backend.dto.leetcode.response.SkillInfo;
import com.codementor.backend.dto.leetcode.response.SkillStats;

import com.codementor.backend.dto.leetcode.graphql.recent.RecentSubmissionItem;
import com.codementor.backend.dto.leetcode.graphql.recent.RecentSubmissionResponse;
import com.codementor.backend.dto.leetcode.response.RecentSubmission;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LeetCodeMapper {

    private final CalendarCalculator calendarCalculator;

    public LeetCodeMapper(CalendarCalculator calendarCalculator) {
        this.calendarCalculator = calendarCalculator;
    }

        private String getBadgeIcon(String icon) {

        if (icon == null || icon.isBlank()) {
                return null;
        }

        // Already a complete URL
        if (icon.startsWith("http://") || icon.startsWith("https://")) {
                return icon;
        }

        // LeetCode sometimes returns relative paths
        return "https://leetcode.com" + icon;
        }


    public ContestInfo mapContest(ContestResponse response) {

        if (response == null
                || response.getData() == null
                || response.getData().getContestRanking() == null) {

            return ContestInfo.builder().build();
        }

        ContestRanking ranking = response.getData().getContestRanking();

        return ContestInfo.builder()
                .rating(ranking.getRating())
                .globalRanking(ranking.getGlobalRanking())
                .attendedContestsCount(ranking.getAttendedContestsCount())
                .topPercentage(ranking.getTopPercentage())
                .badge(
                        ranking.getBadge() != null
                                ? ranking.getBadge().getName()
                                : null
                )
                .build();
    }

    public List<BadgeInfo> mapBadges(BadgesResponse response) {

        if (response == null
                || response.getData() == null
                || response.getData().getMatchedUser() == null
                || response.getData().getMatchedUser().getBadges() == null) {

            return List.of();
        }

        return response.getData()
                .getMatchedUser()
                .getBadges()
                .stream()
                .map(badge -> BadgeInfo.builder()
                        .id(badge.getId())
                        .displayName(badge.getDisplayName())
                        .icon(getBadgeIcon(badge.getIcon()))
                        .creationDate(badge.getCreationDate())
                        .category(badge.getCategory())
                        .build())
                .toList();
    }

    public CalendarInfo mapCalendar(CalendarResponse response) {

        if (response == null
                || response.getData() == null
                || response.getData().getMatchedUser() == null
                || response.getData().getMatchedUser().getUserCalendar() == null) {

            return CalendarInfo.builder().build();
        }

        UserCalendar calendar =
                response.getData()
                        .getMatchedUser()
                        .getUserCalendar();

        return CalendarInfo.builder()
                .currentStreak(
                        calendarCalculator.calculateCurrentStreak(
                                calendar.getSubmissionCalendar()
                        )
                )
                .maxStreak(
                        calendarCalculator.calculateMaxStreak(
                                calendar.getSubmissionCalendar()
                        )
                )
                .totalActiveDays(calendar.getTotalActiveDays())
                .submissionCalendar(calendar.getSubmissionCalendar())
                .build();
    }
        
public ProblemStats mapProblemStats(ProblemProgressResponse response) {

        if (response == null
                || response.getData() == null
                || response.getData().getMatchedUser() == null
                || response.getData().getMatchedUser().getSubmitStats() == null
                || response.getData().getMatchedUser().getSubmitStats().getAcSubmissionNum() == null) {

            return ProblemStats.builder().build();
        }

        int totalSolved = 0;
        int easySolved = 0;
        int mediumSolved = 0;
        int hardSolved = 0;

        int totalSubmissions = 0;
        int easySubmissions = 0;
        int mediumSubmissions = 0;
        int hardSubmissions = 0;

        for (SubmissionStat stat :
                response.getData()
                        .getMatchedUser()
                        .getSubmitStats()
                        .getAcSubmissionNum()) {

            switch (stat.getDifficulty()) {

                case "All" -> {
                    totalSolved = stat.getCount();
                    totalSubmissions = stat.getSubmissions();
                }

                case "Easy" -> {
                    easySolved = stat.getCount();
                    easySubmissions = stat.getSubmissions();
                }

                case "Medium" -> {
                    mediumSolved = stat.getCount();
                    mediumSubmissions = stat.getSubmissions();
                }

                case "Hard" -> {
                    hardSolved = stat.getCount();
                    hardSubmissions = stat.getSubmissions();
                }
            }
        }

        double acceptanceRate =
                totalSubmissions == 0
                        ? 0.0
                        : ((double) totalSolved / totalSubmissions) * 100;

        return ProblemStats.builder()
                .totalSolved(totalSolved)
                .easySolved(easySolved)
                .mediumSolved(mediumSolved)
                .hardSolved(hardSolved)
                .totalSubmissions(totalSubmissions)
                .easySubmissions(easySubmissions)
                .mediumSubmissions(mediumSubmissions)
                .hardSubmissions(hardSubmissions)
                .acceptanceRate(acceptanceRate)
                .build();
    }

    public SkillStats mapSkillStats(SkillStatsResponse response) {

        if (response == null
                || response.getData() == null
                || response.getData().getMatchedUser() == null
                || response.getData().getMatchedUser().getTagProblemCounts() == null) {

            return SkillStats.builder().build();
        }

        return SkillStats.builder()
                .fundamental(
                        response.getData()
                                .getMatchedUser()
                                .getTagProblemCounts()
                                .getFundamental()
                                .stream()
                                .map(this::mapSkillInfo)
                                .toList()
                )
                .intermediate(
                        response.getData()
                                .getMatchedUser()
                                .getTagProblemCounts()
                                .getIntermediate()
                                .stream()
                                .map(this::mapSkillInfo)
                                .toList()
                )
                .advanced(
                        response.getData()
                                .getMatchedUser()
                                .getTagProblemCounts()
                                .getAdvanced()
                                .stream()
                                .map(this::mapSkillInfo)
                                .toList()
                )
                .build();
    }

    private SkillInfo mapSkillInfo(SkillCategory category) {

        return SkillInfo.builder()
                .tagName(category.getTagName())
                .tagSlug(category.getTagSlug())
                .problemsSolved(category.getProblemsSolved())
                .build();
    }

    public List<RecentSubmission> mapRecentSubmissions(RecentSubmissionResponse response) {

        if (response == null
                || response.getData() == null
                || response.getData().getRecentAcSubmissionList() == null) {

            return List.of();
        }

        return response.getData()
                .getRecentAcSubmissionList()
                .stream()
                .map(this::mapRecentSubmission)
                .toList();
    }

    private RecentSubmission mapRecentSubmission(RecentSubmissionItem item) {

        return RecentSubmission.builder()
                .id(item.getId())
                .title(item.getTitle())
                .titleSlug(item.getTitleSlug())
                .timestamp(item.getTimestamp())
                .build();
    }




}