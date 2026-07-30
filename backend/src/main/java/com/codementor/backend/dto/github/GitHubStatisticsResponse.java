package com.codementor.backend.dto.github;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GitHubStatisticsResponse {

    private double developerScore;

    private int repositories;

    private int followers;

    private int following;

    private int publicGists;

    private int accountAgeYears;

}