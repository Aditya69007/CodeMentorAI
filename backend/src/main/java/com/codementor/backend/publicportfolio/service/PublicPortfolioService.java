package com.codementor.backend.publicportfolio.service;

import com.codementor.backend.dto.GrowthReportResponse;
import com.codementor.backend.dto.LeetCodeProfileResponse;
import com.codementor.backend.dto.UserProfileResponse;
import com.codementor.backend.dto.github.GitHubDashboardResponse;
import com.codementor.backend.dto.portfolio.AiDeveloperSummaryResponse;
import com.codementor.backend.dto.portfolio.AiSkillsSummaryResponse;
import com.codementor.backend.dto.portfolio.PortfolioScoreResponse;
import com.codementor.backend.publicportfolio.dto.PublicPortfolioResponse;
import com.codementor.backend.service.AiMentorService;
import com.codementor.backend.service.GitHubDashboardService;
import com.codementor.backend.service.LeetCodeService;
import com.codementor.backend.service.PortfolioAiService;
import com.codementor.backend.service.PortfolioSnapshotService;
import com.codementor.backend.service.UserService;
import com.codementor.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PublicPortfolioService {   

    private final UserRepository userRepository;
    private final UserService userService;
    private final PortfolioAiService portfolioAiService;
    private final GitHubDashboardService gitHubDashboardService;
    private final LeetCodeService leetCodeService;
    private final AiMentorService aiMentorService;

        private final PortfolioSnapshotService portfolioSnapshotService;

    
        public PublicPortfolioResponse getPublicPortfolio(String email) {

        System.out.println("========== PUBLIC PORTFOLIO FROM SNAPSHOT ==========");

        UserProfileResponse profile =
                userService.getCurrentUser(email);

        GitHubDashboardResponse githubDashboard =
                portfolioSnapshotService.getGitHubData(email);

        LeetCodeProfileResponse leetCodeProfile =
                portfolioSnapshotService.getLeetCodeData(email);

        GrowthReportResponse growthReport =
                portfolioSnapshotService.getGrowthReport(email);

        AiDeveloperSummaryResponse developerSummary =
                portfolioSnapshotService.getDeveloperSummary(email);

        AiSkillsSummaryResponse skillsSummary =
                portfolioSnapshotService.getSkillsSummary(email);

        PortfolioScoreResponse portfolioScore =
                portfolioSnapshotService.getPortfolioScore(email);

        return PublicPortfolioResponse.builder()
                .profile(profile)
                .portfolioScore(portfolioScore)
                .developerSummary(developerSummary)
                .skillsSummary(skillsSummary)
                .githubDashboard(githubDashboard)
                .leetCodeProfile(leetCodeProfile)
                .growthReport(growthReport)
                .build();
        }

    public PublicPortfolioResponse getPublicPortfolioByUsername(
            String username
    ) {

        System.out.println("Requested username = " + username);

        var user = userRepository.findByUsername(username);

        System.out.println("User found = " + user.isPresent());

        if (user.isPresent()) {
            System.out.println("Email = " + user.get().getEmail());
        }

        String email = user
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                )
                .getEmail();

        return getPublicPortfolio(email);

    }

        public void refreshPortfolio(String username) {

        var user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        portfolioSnapshotService.refreshSnapshot(
                user.getEmail()
        );
        }

}