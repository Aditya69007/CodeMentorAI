package com.codementor.backend.mapper;

import com.codementor.backend.dto.leetcode.graphql.badges.BadgesResponse;
import com.codementor.backend.dto.leetcode.graphql.contest.ContestRanking;
import com.codementor.backend.dto.leetcode.graphql.contest.ContestResponse;
import com.codementor.backend.dto.leetcode.response.BadgeInfo;
import com.codementor.backend.dto.leetcode.response.ContestInfo;

import com.codementor.backend.dto.leetcode.graphql.calendar.CalendarResponse;
import com.codementor.backend.dto.leetcode.graphql.calendar.UserCalendar;
import com.codementor.backend.dto.leetcode.response.CalendarInfo;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LeetCodeMapper {

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
                        .icon(badge.getIcon())
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
                response.getData().getMatchedUser().getUserCalendar();

        return CalendarInfo.builder()
                .streak(calendar.getStreak())
                .totalActiveDays(calendar.getTotalActiveDays())
                .submissionCalendar(calendar.getSubmissionCalendar())
                .build();
    }

}