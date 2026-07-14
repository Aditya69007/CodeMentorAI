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
public class PersonalizedRevisionPlanResponse {

    private Integer revisionScore;

    private String revisionLevel;

    private List<String> urgentConcepts;

    private List<String> improvingConcepts;

    private List<String> masteredConcepts;

    private List<RecommendedProblemResponse> revisionProblems;

    private String recommendedAction;

    private String message;
}