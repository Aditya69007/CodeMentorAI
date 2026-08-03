package com.codementor.backend.service.impl;

import com.codementor.backend.client.GitHubClient;
import com.codementor.backend.dto.GitHubProfileResponse;
import com.codementor.backend.dto.GitHubRepositoryResponse;
import com.codementor.backend.service.GitHubService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GitHubServiceImpl implements GitHubService {

    private final GitHubClient gitHubClient;

        @Override
        public GitHubProfileResponse getProfile(String username) {

        return gitHubClient.getUserProfile(username);

        }

        @Override
        public List<GitHubRepositoryResponse> getRepositories(String username) {

        return gitHubClient.getRepositories(username);

        }

}