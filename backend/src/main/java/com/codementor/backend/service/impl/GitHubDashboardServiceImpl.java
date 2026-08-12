package com.codementor.backend.service.impl;

import com.codementor.backend.client.GitHubClient;
import com.codementor.backend.dto.GitHubProfileResponse;
import com.codementor.backend.dto.GitHubRepositoryResponse;
import com.codementor.backend.dto.github.GitHubDashboardResponse;
import com.codementor.backend.mapper.GitHubDashboardMapper;
import com.codementor.backend.service.GitHubAnalyticsService;
import com.codementor.backend.service.GitHubDashboardService;
import com.codementor.backend.service.GitHubRepositoryRankingService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GitHubDashboardServiceImpl
        implements GitHubDashboardService {

    private final GitHubClient gitHubClient;

    private final GitHubDashboardMapper mapper;

    private final GitHubAnalyticsService gitHubAnalyticsService;

    private final GitHubRepositoryRankingService
        gitHubRepositoryRankingService;

@Override
public GitHubDashboardResponse getDashboard(String username) {

    System.out.println("STEP 1");

    GitHubProfileResponse profile =
            gitHubClient.getUserProfile(username);

    System.out.println("STEP 2");

    List<GitHubRepositoryResponse> repositories =
            gitHubClient.getRepositories(username);

    System.out.println("STEP 3");

    List<GitHubRepositoryResponse> rankedRepositories =
            gitHubRepositoryRankingService
                    .rankRepositories(repositories);

    System.out.println("STEP 4");

    var statistics =
            mapper.mapStatistics(profile);

    System.out.println("STEP 5");

    var languages =
            mapper.mapLanguages(rankedRepositories);

    System.out.println("STEP 6");

    var topRepositories =
            mapper.mapTopRepositories(rankedRepositories);

    System.out.println("STEP 7");

    var analytics =
            gitHubAnalyticsService.calculate(
                    rankedRepositories
            );

    System.out.println("STEP 8");

    var repositoryDtos =
            mapper.mapRepositories(rankedRepositories);

    System.out.println("STEP 9");

    return GitHubDashboardResponse.builder()
            .profile(profile)
            .statistics(statistics)
            .languages(languages)
            .topRepositories(topRepositories)
            .analytics(analytics)
            .repositories(repositoryDtos)
            .build();
}

}