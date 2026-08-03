package com.codementor.backend.service;
import com.codementor.backend.dto.GitHubRepositoryResponse;
import java.util.List;


public interface GitHubRepositoryRankingService {

    List<GitHubRepositoryResponse> rankRepositories(
            List<GitHubRepositoryResponse> repositories
    );

}