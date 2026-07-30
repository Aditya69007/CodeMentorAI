package com.codementor.backend.dto.leetcode.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AnalyticsInfo {

    private Double developerScore;

    private Double acceptanceRate;

    private Double contestScore;

    private Double consistencyScore;

    private Double difficultyScore;

    private Double skillScore;

    private List<String> strongestSkills;

    private List<String> weakestSkills;

}