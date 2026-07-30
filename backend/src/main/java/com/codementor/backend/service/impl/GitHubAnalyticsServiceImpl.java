package com.codementor.backend.service.impl;

import com.codementor.backend.analytics.github.GitHubInsightCalculator;
import com.codementor.backend.analytics.github.GitHubLanguageCalculator;
import com.codementor.backend.analytics.github.GitHubRepositoryScoreCalculator;
import com.codementor.backend.dto.GitHubRepositoryResponse;
import com.codementor.backend.dto.github.GitHubAnalyticsResponse;
import com.codementor.backend.service.GitHubAnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GitHubAnalyticsServiceImpl
        implements GitHubAnalyticsService {

    private final GitHubRepositoryScoreCalculator repositoryCalculator;

    private final GitHubLanguageCalculator languageCalculator;

    private final GitHubInsightCalculator insightCalculator;

    @Override
    public GitHubAnalyticsResponse calculate(
            List<GitHubRepositoryResponse> repositories
    ) {

        List<String> strongest =
                languageCalculator
                        .strongestTechnologies(repositories);

        double repositoryScore =
                repositoryCalculator.calculate(repositories);

        double languageScore =
                languageCalculator.calculate(repositories);

        return GitHubAnalyticsResponse.builder()

                .repositoryScore(repositoryScore)

                .languageDiversityScore(languageScore)

                .technologyScore(0)

                .consistencyScore(0)

                .openSourceScore(0)

                .strongestTechnologies(strongest)

                .recommendedTechnologies(
                        languageCalculator
                                .recommendedTechnologies(strongest)
                )

                .strengths(
                        insightCalculator.strengths(
                                repositories,
                                strongest
                        )
                )

                .improvements(
                        insightCalculator.improvements(
                                repositories
                        )
                )

                .insights(
                        insightCalculator.insights(
                                repositoryScore,
                                languageScore
                        )
                )

                .build();

    }

}