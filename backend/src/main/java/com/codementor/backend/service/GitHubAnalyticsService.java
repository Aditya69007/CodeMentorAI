package com.codementor.backend.service;

import com.codementor.backend.dto.GitHubRepositoryResponse;
import com.codementor.backend.dto.github.GitHubAnalyticsResponse;

import java.util.List;

public interface GitHubAnalyticsService {

    GitHubAnalyticsResponse calculate(
            List<GitHubRepositoryResponse> repositories
    );

}