package com.codementor.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PracticeRecommendationResponse {

    private String concept;

    private Long totalMistakes;

    private Long successfulRecoveries;

    private String growthStatus;

    private String priority;

    private Integer recommendedProblemCount;

    private String reason;

    private String recommendation;

    private List<RecommendedProblemResponse> problems;
}