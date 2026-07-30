package com.codementor.backend.analytics.github;

import com.codementor.backend.dto.GitHubRepositoryResponse;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class GitHubLanguageCalculator {

    public double calculate(List<GitHubRepositoryResponse> repositories) {

        Set<String> languages = repositories.stream()
                .map(GitHubRepositoryResponse::getLanguage)
                .filter(Objects::nonNull)
                .filter(language -> !language.isBlank())
                .collect(Collectors.toSet());

        int count = languages.size();

        if (count >= 5) return 100;
        if (count == 4) return 80;
        if (count == 3) return 60;
        if (count == 2) return 40;
        if (count == 1) return 20;

        return 0;
    }

    public List<String> strongestTechnologies(
            List<GitHubRepositoryResponse> repositories
    ) {

        Map<String, Integer> frequency = new HashMap<>();

        for (GitHubRepositoryResponse repository : repositories) {

            if (repository.getLanguage() == null) {
                continue;
            }

            frequency.merge(
                    repository.getLanguage(),
                    1,
                    Integer::sum
            );

        }

        return frequency.entrySet()

                .stream()

                .sorted(
                        Map.Entry.<String, Integer>comparingByValue()
                                .reversed()
                )

                .limit(5)

                .map(Map.Entry::getKey)

                .toList();

    }

    public List<String> recommendedTechnologies(
            List<String> strongestTechnologies
    ) {

        List<String> recommendations = new ArrayList<>();

        if (strongestTechnologies.contains("Java")) {

            recommendations.add("Spring Boot");
            recommendations.add("Docker");
            recommendations.add("Redis");
            recommendations.add("PostgreSQL");
            recommendations.add("AWS");

        }

        if (strongestTechnologies.contains("JavaScript")) {

            recommendations.add("TypeScript");
            recommendations.add("Next.js");
            recommendations.add("Node.js");
            recommendations.add("MongoDB");

        }

        if (strongestTechnologies.contains("TypeScript")) {

            recommendations.add("React");
            recommendations.add("NestJS");

        }

        return recommendations

                .stream()

                .distinct()

                .toList();

    }

}