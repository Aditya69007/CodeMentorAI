package com.codementor.backend.mapper;

import com.codementor.backend.dto.GitHubProfileResponse;
import com.codementor.backend.dto.GitHubRepositoryResponse;
import com.codementor.backend.dto.github.*;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class GitHubDashboardMapper {

    public GitHubStatisticsResponse mapStatistics(
            GitHubProfileResponse profile
    ) {

        int accountAge = 0;

        if (profile.getCreatedAt() != null) {

            accountAge = (int) ChronoUnit.YEARS.between(
                    OffsetDateTime.parse(profile.getCreatedAt()).toLocalDate(),
                    LocalDate.now()
            );

        }

        return GitHubStatisticsResponse.builder()
                .developerScore(0)
                .repositories(profile.getPublicRepositories())
                .followers(profile.getFollowers())
                .following(profile.getFollowing())
                .publicGists(profile.getPublicGists())
                .accountAgeYears(accountAge)
                .build();

    }

    public List<GitHubTopRepositoryResponse> mapTopRepositories(
            List<GitHubRepositoryResponse> repositories
    ) {

        return repositories.stream()

                .sorted(
                        Comparator
                                .comparing(
                                        GitHubRepositoryResponse::getStars
                                )
                                .reversed()
                )

                .limit(6)

                .map(repo ->

                        GitHubTopRepositoryResponse.builder()

                                .name(repo.getName())

                                .description(repo.getDescription())

                                .language(repo.getLanguage())

                                .stars(repo.getStars())

                                .forks(repo.getForks())

                                .repositoryUrl(repo.getRepositoryUrl())

                                .watchers(repo.getWatchers())

                                .openIssues(repo.getOpenIssues())

                                .updatedAt(repo.getUpdatedAt())

                                .isPrivate(repo.getIsPrivate())

                                .topics(repo.getTopics())

                                .build()

                )

                .toList();

    }

    public List<GitHubLanguageResponse> mapLanguages(
            List<GitHubRepositoryResponse> repositories
    ) {

        Map<String, Integer> counts = new HashMap<>();

        int total = 0;

        for (GitHubRepositoryResponse repo : repositories) {

            if (repo.getLanguage() == null)
                continue;

            counts.merge(
                    repo.getLanguage(),
                    1,
                    Integer::sum
            );

            total++;

        }

        int finalTotal = Math.max(total, 1);

        return counts.entrySet()

                .stream()

                .sorted(
                        Map.Entry
                                .<String, Integer>comparingByValue()
                                .reversed()
                )

                .map(entry ->

                        GitHubLanguageResponse.builder()

                                .language(entry.getKey())

                                .percentage(

                                        entry.getValue()

                                                * 100.0

                                                / finalTotal

                                )

                                .build()

                )

                .collect(Collectors.toList());

    }

        public List<GitHubRepositoryDto> mapRepositories(
                List<GitHubRepositoryResponse> repositories
        ) {

        return repositories.stream()

                .map(repo ->

                        GitHubRepositoryDto.builder()

                                .name(repo.getName())

                                .description(repo.getDescription())

                                .language(repo.getLanguage())

                                .stars(repo.getStars())

                                .forks(repo.getForks())

                                .repositoryUrl(repo.getRepositoryUrl())

                                .watchers(repo.getWatchers())

                                .openIssues(repo.getOpenIssues())

                                .updatedAt(repo.getUpdatedAt())

                                .isPrivate(repo.getIsPrivate())

                                .topics(repo.getTopics())

                                .build()

                )

                .toList();

        }

    
}