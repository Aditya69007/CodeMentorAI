package com.codementor.backend.dto;

import com.codementor.backend.entity.Problem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class RecommendationScore {

    private Problem problem;

    private int score;

    private String reason;
}