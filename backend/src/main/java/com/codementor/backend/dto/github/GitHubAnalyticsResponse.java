package com.codementor.backend.dto.github;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GitHubAnalyticsResponse {

    private double repositoryScore;

    private double languageDiversityScore;

    private double openSourceScore;

    private double consistencyScore;

    private double technologyScore;

    private List<String> strongestTechnologies;

    private List<String> recommendedTechnologies;

    private List<String> strengths;

    private List<String> improvements;

    private List<String> insights;

}