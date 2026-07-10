package com.codementor.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProblemProgressResponse {

    private Long totalProblems;

    private Long solvedProblems;

    private Long unsolvedProblems;

    private Double solvedPercentage;
}