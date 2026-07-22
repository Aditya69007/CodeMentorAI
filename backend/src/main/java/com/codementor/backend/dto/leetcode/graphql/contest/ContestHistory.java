package com.codementor.backend.dto.leetcode.graphql.contest;

import lombok.Data;

@Data
public class ContestHistory {

    private Boolean attended;

    private String trendDirection;

    private Double rating;

    private Integer ranking;

    private Integer problemsSolved;

    private Integer totalProblems;

    private Long finishTimeInSeconds;

    private Contest contest;

}