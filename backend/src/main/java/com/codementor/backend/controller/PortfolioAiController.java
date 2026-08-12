package com.codementor.backend.controller;

import com.codementor.backend.dto.portfolio.AiDeveloperSummaryResponse;
import com.codementor.backend.dto.portfolio.AiSkillsSummaryResponse;
import com.codementor.backend.service.PortfolioAiService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.codementor.backend.dto.portfolio.PortfolioScoreResponse;

@RestController
@RequestMapping("/api/v1/portfolio/ai")
@RequiredArgsConstructor
public class PortfolioAiController {

    private final PortfolioAiService portfolioAiService;

    @GetMapping("/developer-summary")
    public AiDeveloperSummaryResponse getDeveloperSummary(
            Authentication authentication
    ) {

        return portfolioAiService.generateDeveloperSummary(
                authentication.getName()
        );
    }

    @GetMapping("/skills-summary")
    public AiSkillsSummaryResponse getSkillsSummary(
            Authentication authentication
    ) {

        return portfolioAiService.generateSkillsSummary(
                authentication.getName()
        );

    }

        @GetMapping("/portfolio-score")
        public PortfolioScoreResponse getPortfolioScore(
                Authentication authentication
        ) {

        return portfolioAiService.generatePortfolioScore(
                authentication.getName()
        );

        }

}