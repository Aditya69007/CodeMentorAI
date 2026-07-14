package com.codementor.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HintDependencyScoreResponse {

    private Long totalSubmissions;

    private Long totalProblemsAttempted;

    private Long problemsWithHints;

    private Long totalHintsUsed;

    private Long level1HintsUsed;

    private Long level2HintsUsed;

    private Long level3HintsUsed;

    private Long level4HintsUsed;

    private Double hintUsageRate;

    private Integer dependencyScore;

    private String dependencyLevel;

    private String message;
}