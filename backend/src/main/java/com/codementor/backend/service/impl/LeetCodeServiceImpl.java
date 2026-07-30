package com.codementor.backend.service.impl;

import com.codementor.backend.client.LeetCodeGraphQLClient;
import com.codementor.backend.dto.LeetCodeProfileResponse;
import com.codementor.backend.mapper.LeetCodeMapper;
import com.codementor.backend.dto.leetcode.graphql.badges.BadgesResponse;
import com.codementor.backend.dto.leetcode.graphql.contest.ContestResponse;
import com.codementor.backend.dto.leetcode.response.RecentSubmission;
import com.codementor.backend.dto.leetcode.response.AnalyticsInfo;
import com.codementor.backend.dto.leetcode.response.BadgeInfo;
import com.codementor.backend.dto.leetcode.response.ContestInfo;
import com.codementor.backend.dto.leetcode.response.ProblemStats;
import com.codementor.backend.service.LeetCodeService;
import com.codementor.backend.dto.leetcode.response.SkillStats;
import com.codementor.backend.dto.leetcode.graphql.calendar.CalendarResponse;
import com.codementor.backend.dto.leetcode.response.CalendarInfo;
import com.codementor.backend.dto.leetcode.analytics.LeetCodeAnalyticsService;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LeetCodeServiceImpl implements LeetCodeService {

    private final LeetCodeGraphQLClient leetCodeGraphQLClient;
    private final LeetCodeMapper leetCodeMapper;
    private final LeetCodeAnalyticsService analyticsService;
    private final Executor leetCodeExecutor;

@Override
public LeetCodeProfileResponse getProfile(String username) {

        CompletableFuture<ContestInfo> contestFuture =
                CompletableFuture.supplyAsync(
                        () -> leetCodeMapper.mapContest(
                                leetCodeGraphQLClient.getContestRanking(username)
                        ),
                        leetCodeExecutor
                );
        ContestInfo contest = contestFuture.join();

    List<BadgeInfo> badges =
            leetCodeMapper.mapBadges(
                    leetCodeGraphQLClient.getBadges(username)
            );

    CalendarInfo calendar =
            leetCodeMapper.mapCalendar(
                    leetCodeGraphQLClient.getCalendar(username)
            );
    
    ProblemStats problems =
            leetCodeMapper.mapProblemStats(
                    leetCodeGraphQLClient.getProblemProgress(username)
            );
            

        SkillStats skills =
                leetCodeMapper.mapSkillStats(
                        leetCodeGraphQLClient.getSkillStats(username)
                );

        List<RecentSubmission> recentSubmissions =
                leetCodeMapper.mapRecentSubmissions(
                        leetCodeGraphQLClient.getRecentSubmissions(username)
                );

        AnalyticsInfo analytics =
                analyticsService.generateAnalytics(
                        contest,
                        calendar,
                        problems,
                        skills
                );

        return LeetCodeProfileResponse.builder()
                .username(username)
                .contest(contest)
                .badges(badges)
                .calendar(calendar)
                .problems(problems)
                .skills(skills)
                .recentSubmissions(recentSubmissions)
                .analytics(analytics)
                .build();
}

}