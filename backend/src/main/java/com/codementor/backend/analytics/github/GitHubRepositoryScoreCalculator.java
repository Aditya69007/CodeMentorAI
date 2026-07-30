package com.codementor.backend.analytics.github;

import com.codementor.backend.dto.GitHubRepositoryResponse;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class GitHubRepositoryScoreCalculator {

    public double calculate(List<GitHubRepositoryResponse> repositories) {

        if (repositories == null || repositories.isEmpty()) {
            return 0;
        }

        double score = 0;

        int repositoryCount = repositories.size();

        if (repositoryCount >= 20) {
            score += 30;
        } else if (repositoryCount >= 11) {
            score += 25;
        } else if (repositoryCount >= 6) {
            score += 20;
        } else if (repositoryCount >= 1) {
            score += 10;
        }

        int totalStars = repositories.stream()
                .mapToInt(repo -> repo.getStars() == null ? 0 : repo.getStars())
                .sum();

        if (totalStars >= 100) {
            score += 20;
        } else if (totalStars >= 51) {
            score += 15;
        } else if (totalStars >= 11) {
            score += 10;
        } else if (totalStars >= 1) {
            score += 5;
        }

        int totalForks = repositories.stream()
                .mapToInt(repo -> repo.getForks() == null ? 0 : repo.getForks())
                .sum();

        if (totalForks >= 20) {
            score += 15;
        } else if (totalForks >= 6) {
            score += 10;
        } else if (totalForks >= 1) {
            score += 5;
        }

        long repositoriesWithDescription = repositories.stream()
                .filter(repo ->
                        repo.getDescription() != null &&
                        !repo.getDescription().isBlank())
                .count();

        score += Math.min(repositoriesWithDescription * 2, 15);

        Set<String> languages = new HashSet<>();

        repositories.forEach(repo -> {

            if (repo.getLanguage() != null &&
                    !repo.getLanguage().isBlank()) {

                languages.add(repo.getLanguage());

            }

        });

        int languageCount = languages.size();

        if (languageCount >= 4) {
            score += 20;
        } else if (languageCount == 3) {
            score += 15;
        } else if (languageCount == 2) {
            score += 10;
        } else if (languageCount == 1) {
            score += 5;
        }

        return Math.min(score, 100);

    }

}