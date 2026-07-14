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
public class AdaptiveMentorProfileResponse {

    private String mentorMode;

    private String developerLevel;

    private Integer hintDependencyScore;

    private Double independentSolveRate;

    private List<String> weakConcepts;

    private List<String> recurringMistakes;

    private String teachingStrategy;

    private String message;
}