package com.codementor.backend.dto.github;

import com.codementor.backend.dto.GitHubProfileResponse;
import com.codementor.backend.dto.GitHubRepositoryResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GitHubDashboardResponse {

    private GitHubProfileResponse profile;

    private GitHubStatisticsResponse statistics;

    private GitHubAnalyticsResponse analytics;

    private List<GitHubLanguageResponse> languages;

    private List<GitHubTopRepositoryResponse> topRepositories;

    private List<GitHubRepositoryDto> repositories;
}