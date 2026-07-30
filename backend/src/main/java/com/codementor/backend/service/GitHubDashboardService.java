package com.codementor.backend.service;

import com.codementor.backend.dto.github.GitHubDashboardResponse;

public interface GitHubDashboardService {

    GitHubDashboardResponse getDashboard(String username);

}