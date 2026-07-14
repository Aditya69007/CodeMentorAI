package com.codementor.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GrowthReportResponse {

    private int overallGrowthScore;

    private String developerLevel;

    private int hintDependencyScore;

    private double independentSolveRate;

    private long totalCompletedIndependentSessions;

    private long independentlySolvedProblems;

    private List<ConceptGrowthResponse> conceptGrowth;

    private List<String> recurringMistakes;

    private List<String> achievements;

    private String growthSummary;

    private String recommendedNextAction;
}