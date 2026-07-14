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
public class PersonalizedLearningPlanResponse {

    private Integer overallReadinessScore;

    private String learningLevel;

    private List<String> weakConcepts;

    private List<String> revisionPriorities;

    private List<String> strengths;

    private List<RecommendedProblemResponse> recommendedProblems;

    private Integer hintDependencyScore;

    private Double independentSolveRate;

    private String recommendedAction;

    private String message;
}