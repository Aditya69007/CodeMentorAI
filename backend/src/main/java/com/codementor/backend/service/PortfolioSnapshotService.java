package com.codementor.backend.service;

import com.codementor.backend.dto.LeetCodeProfileResponse;
import com.codementor.backend.dto.GrowthReportResponse;
import com.codementor.backend.dto.github.GitHubDashboardResponse;
import com.codementor.backend.dto.portfolio.AiDeveloperSummaryResponse;
import com.codementor.backend.dto.portfolio.AiSkillsSummaryResponse;
import com.codementor.backend.dto.portfolio.PortfolioScoreResponse;

public interface PortfolioSnapshotService {

    GitHubDashboardResponse getGitHubData(String email);

    LeetCodeProfileResponse getLeetCodeData(String email);

    GrowthReportResponse getGrowthReport(String email);

    AiDeveloperSummaryResponse getDeveloperSummary(String email);

    AiSkillsSummaryResponse getSkillsSummary(String email);

    PortfolioScoreResponse getPortfolioScore(String email);

    void refreshSnapshot(String email);
}