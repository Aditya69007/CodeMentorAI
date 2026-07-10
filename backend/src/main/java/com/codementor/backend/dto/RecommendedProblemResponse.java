package com.codementor.backend.dto;

import com.codementor.backend.entity.Difficulty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendedProblemResponse {

    private Long id;

    private String title;

    private Difficulty difficulty;

    private Boolean solved;
}