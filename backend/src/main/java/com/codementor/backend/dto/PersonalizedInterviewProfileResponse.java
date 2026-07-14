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
public class PersonalizedInterviewProfileResponse {

    private String interviewLevel;

    private String developerLevel;

    private int overallReadinessScore;

    private int hintDependencyScore;

    private double independentSolveRate;

    private List<String> focusConcepts;

    private List<String> recurringMistakes;

    private String interviewStrategy;

    private String recommendedAction;

    private String message;
}