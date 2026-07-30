package com.codementor.backend.analytics.github;

import com.codementor.backend.dto.GitHubRepositoryResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GitHubInsightCalculator {

    public List<String> strengths(
            List<GitHubRepositoryResponse> repositories,
            List<String> strongestTechnologies
    ) {

        List<String> strengths = new ArrayList<>();

        if (repositories.size() >= 5) {
            strengths.add("Maintains a healthy portfolio of public repositories.");
        }

        if (strongestTechnologies.contains("Java")) {
            strengths.add("Strong backend development skills using Java.");
        }

        if (strongestTechnologies.contains("TypeScript")) {
            strengths.add("Uses modern TypeScript for scalable applications.");
        }

        if (strongestTechnologies.contains("JavaScript")) {
            strengths.add("Good frontend development experience.");
        }

        if (strengths.isEmpty()) {
            strengths.add("Building an active GitHub profile.");
        }

        return strengths;
    }

    public List<String> improvements(
            List<GitHubRepositoryResponse> repositories
    ) {

        List<String> improvements = new ArrayList<>();

        long repositoriesWithoutDescription =
                repositories.stream()
                        .filter(repository ->
                                repository.getDescription() == null ||
                                repository.getDescription().isBlank())
                        .count();

        if (repositoriesWithoutDescription > 0) {
            improvements.add("Add meaningful descriptions to all repositories.");
        }

        improvements.add("Increase open-source contributions.");

        improvements.add("Add Docker and CI/CD to showcase DevOps skills.");

        improvements.add("Write comprehensive README files.");

        return improvements;
    }

    public List<String> insights(
            double repositoryScore,
            double languageScore
    ) {

        List<String> insights = new ArrayList<>();

        if (repositoryScore >= 70) {
            insights.add("Repository portfolio demonstrates strong project experience.");
        } else {
            insights.add("Expanding repository quality will improve recruiter confidence.");
        }

        if (languageScore >= 60) {
            insights.add("Technology diversity is good.");
        } else {
            insights.add("Learning additional technologies will strengthen your profile.");
        }

        insights.add("Keep building production-ready projects to improve your Developer Score.");

        return insights;
    }

}