package com.codementor.backend.client;

import com.codementor.backend.dto.leetcode.GraphQLRequest;
import com.codementor.backend.dto.leetcode.graphql.badges.BadgesResponse;
import com.codementor.backend.dto.leetcode.graphql.calendar.CalendarResponse;
import com.codementor.backend.dto.leetcode.graphql.contest.ContestResponse;
import com.codementor.backend.dto.leetcode.graphql.problems.ProblemProgressResponse;
import com.codementor.backend.dto.leetcode.graphql.skills.SkillStatsResponse;
import com.codementor.backend.dto.leetcode.graphql.recent.RecentSubmissionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class LeetCodeGraphQLClient {

    private static final String GRAPHQL_URL =
            "https://leetcode.com/graphql/";

    private final RestClient restClient = RestClient.create();

    public <T> T execute(GraphQLRequest request, Class<T> responseType) {
        
        return restClient.post()
        .uri(GRAPHQL_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .body(request)
        .retrieve()
        .body(responseType);
        
    }
    
    public ContestResponse getContestRanking(String username) {
    
        GraphQLRequest request = GraphQLRequest.builder()
                .operationName("userContestRankingInfo")
                .query("""
                    query userContestRankingInfo($username: String!) {
                      userContestRanking(username: $username) {
                        attendedContestsCount
                        rating
                        globalRanking
                        totalParticipants
                        topPercentage
                        badge {
                          name
                        }
                      }
                      userContestRankingHistory(username: $username) {
                        attended
                        trendDirection
                        problemsSolved
                        totalProblems
                        finishTimeInSeconds
                        rating
                        ranking
                        contest {
                          title
                          startTime
                        }
                      }
                    }
                    """)
                .variables(Map.of("username", username))
                .build();
    
        return execute(request, ContestResponse.class);
    }

    public BadgesResponse getBadges(String username) {

        GraphQLRequest request = GraphQLRequest.builder()
                .operationName("userBadges")
                .query("""
                    query userBadges($username: String!) {
                    matchedUser(username: $username) {
                        badges {
                        id
                        displayName
                        icon
                        creationDate
                        category
                        }
                    }
                    }
                    """)
                .variables(Map.of("username", username))
                .build();

        return execute(request, BadgesResponse.class);
    }

    public CalendarResponse getCalendar(String username) {

        GraphQLRequest request = GraphQLRequest.builder()
                .operationName("userProfileCalendar")
                .query("""
                    query userProfileCalendar($username: String!, $year: Int) {
                    matchedUser(username: $username) {
                        userCalendar(year: $year) {
                        streak
                        totalActiveDays
                        submissionCalendar
                        }
                    }
                    }
                    """)
                .variables(Map.of("username", username))
                .build();

        CalendarResponse response =
                execute(request, CalendarResponse.class);

        System.out.println(
                "Calendar Streak = " +
                response.getData()
                        .getMatchedUser()
                        .getUserCalendar()
                        .getStreak()
        );

        System.out.println(
                "Total Active Days = " +
                response.getData()
                        .getMatchedUser()
                        .getUserCalendar()
                        .getTotalActiveDays()
        );

        return response;
    }

    public ProblemProgressResponse getProblemProgress(String username) {

        GraphQLRequest request = GraphQLRequest.builder()
                .operationName("userSessionProgress")
                .query("""
                    query userSessionProgress($username: String!) {
                    allQuestionsCount {
                        difficulty
                        count
                    }

                    matchedUser(username: $username) {
                        submitStats {
                        acSubmissionNum {
                            difficulty
                            count
                            submissions
                        }
                        totalSubmissionNum {
                            difficulty
                            count
                            submissions
                        }
                        }
                    }
                    }
                    """)
                .variables(Map.of("username", username))
                .build();

        return execute(request, ProblemProgressResponse.class);
    }

    public SkillStatsResponse getSkillStats(String username) {

        GraphQLRequest request = GraphQLRequest.builder()
                .operationName("skillStats")
                .query("""
                    query skillStats($username: String!) {
                    matchedUser(username: $username) {
                        tagProblemCounts {
                        advanced {
                            tagName
                            tagSlug
                            problemsSolved
                        }
                        intermediate {
                            tagName
                            tagSlug
                            problemsSolved
                        }
                        fundamental {
                            tagName
                            tagSlug
                            problemsSolved
                        }
                        }
                    }
                    }
                    """)
                .variables(Map.of("username", username))
                .build();

        return execute(request, SkillStatsResponse.class);
    }

    public RecentSubmissionResponse getRecentSubmissions(String username) {

        GraphQLRequest request = GraphQLRequest.builder()
                .operationName("recentAcSubmissions")
                .query("""
                    query recentAcSubmissions($username: String!, $limit: Int!) {
                    recentAcSubmissionList(username: $username, limit: $limit) {
                        id
                        title
                        titleSlug
                        timestamp
                    }
                    }
                    """)
                .variables(Map.of(
                        "username", username,
                        "limit", 15
                ))
                .build();

        return execute(request, RecentSubmissionResponse.class);
    }    

}