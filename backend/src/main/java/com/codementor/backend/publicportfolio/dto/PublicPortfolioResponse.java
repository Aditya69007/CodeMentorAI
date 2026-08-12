package com.codementor.backend.publicportfolio.dto;

import com.codementor.backend.dto.github.GitHubDashboardResponse;
import com.codementor.backend.dto.portfolio.AiDeveloperSummaryResponse;
import com.codementor.backend.dto.portfolio.AiSkillsSummaryResponse;
import com.codementor.backend.dto.portfolio.PortfolioScoreResponse;
import com.codementor.backend.dto.GrowthReportResponse;
import com.codementor.backend.dto.LeetCodeProfileResponse;
import com.codementor.backend.dto.UserProfileResponse;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PublicPortfolioResponse {

    private UserProfileResponse profile;
    private PortfolioScoreResponse portfolioScore;
    private AiDeveloperSummaryResponse developerSummary;
    private AiSkillsSummaryResponse skillsSummary;
    private GitHubDashboardResponse githubDashboard;
    private LeetCodeProfileResponse leetCodeProfile;
    private GrowthReportResponse growthReport;
}