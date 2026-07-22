package com.codementor.backend.service;

import com.codementor.backend.dto.GitHubProfileResponse;
import com.codementor.backend.dto.GitHubRepositoryResponse;

import java.util.List;

public interface GitHubService {

    GitHubProfileResponse getProfile(String username);

    List<GitHubRepositoryResponse> getRepositories(String username);


}