package com.codementor.backend.service;

import com.codementor.backend.dto.DeveloperSkillResponse;
import com.codementor.backend.dto.GrowthReportResponse;
import com.codementor.backend.dto.LeetCodeProfileResponse;
import com.codementor.backend.dto.github.GitHubDashboardResponse;
import com.codementor.backend.dto.portfolio.AiDeveloperSummaryResponse;
import com.codementor.backend.dto.portfolio.AiSkillsSummaryResponse;
import com.codementor.backend.dto.portfolio.PortfolioScoreResponse;

import java.util.List;

public interface PortfolioAiService {

    AiDeveloperSummaryResponse generateDeveloperSummary(
            String email
    );

    AiSkillsSummaryResponse generateSkillsSummary(
            String email
    );

        PortfolioScoreResponse generatePortfolioScore(
                String email
        );

        AiDeveloperSummaryResponse generateDeveloperSummary(
                String email,
                GitHubDashboardResponse githubDashboard,
                LeetCodeProfileResponse leetcodeProfile,
                GrowthReportResponse growthReport,
                List<DeveloperSkillResponse> developerSkills
        );

        AiSkillsSummaryResponse generateSkillsSummary(
                String email,
                GitHubDashboardResponse githubDashboard,
                List<DeveloperSkillResponse> developerSkills,
                GrowthReportResponse growthReport
        );

        PortfolioScoreResponse generatePortfolioScore(
                String email,
                GitHubDashboardResponse githubDashboard,
                LeetCodeProfileResponse leetcodeProfile,
                GrowthReportResponse growthReport
        );

}