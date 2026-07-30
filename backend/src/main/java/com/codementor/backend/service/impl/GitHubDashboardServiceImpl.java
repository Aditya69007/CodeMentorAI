package com.codementor.backend.service.impl;

import com.codementor.backend.client.GitHubClient;
import com.codementor.backend.dto.GitHubProfileResponse;
import com.codementor.backend.dto.GitHubRepositoryResponse;
import com.codementor.backend.dto.github.GitHubDashboardResponse;
import com.codementor.backend.mapper.GitHubDashboardMapper;
import com.codementor.backend.service.GitHubAnalyticsService;
import com.codementor.backend.service.GitHubDashboardService;

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

    @Override
    public GitHubDashboardResponse getDashboard(
            String username
    ) {

        GitHubProfileResponse profile =
                gitHubClient.getUserProfile(username);

        List<GitHubRepositoryResponse> repositories =
                List.of(
                        gitHubClient.getRepositories(username)
                );

        return GitHubDashboardResponse.builder()

                .profile(profile)

                .statistics(
                        mapper.mapStatistics(profile)
                )

                .languages(
                        mapper.mapLanguages(repositories)
                )

                .topRepositories(
                        mapper.mapTopRepositories(repositories)
                )

                .analytics(
                        gitHubAnalyticsService.calculate(
                                repositories
                        )
                )

                .build();

    }

}