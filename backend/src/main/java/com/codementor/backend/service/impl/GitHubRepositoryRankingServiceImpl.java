package com.codementor.backend.service.impl;

import com.codementor.backend.dto.GitHubRepositoryResponse;
import com.codementor.backend.service.GitHubRepositoryRankingService;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;

@Service
public class GitHubRepositoryRankingServiceImpl
        implements GitHubRepositoryRankingService {

    @Override
    public List<GitHubRepositoryResponse> rankRepositories(
            List<GitHubRepositoryResponse> repositories
    ) {

        return repositories.stream()

                .sorted(
                        Comparator.comparingInt(this::calculateScore)
                                .reversed()
                )

                .toList();

    }

    private int calculateScore(
        GitHubRepositoryResponse repository
    ) {

        int score = 0;

        score += repository.getStars() * 5;

        score += repository.getForks() * 4;

        score += repository.getWatchers() * 2;

        if (repository.getDescription() != null &&
            !repository.getDescription().isBlank()) {

            score += 10;

        }

        if (repository.getTopics() != null &&
            !repository.getTopics().isEmpty()) {

            score += 10;

        }

        if (!Boolean.TRUE.equals(repository.getIsPrivate())) {

            score += 5;

        }

        return score;

    }

}