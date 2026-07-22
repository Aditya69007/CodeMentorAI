package com.codementor.backend.dto.leetcode.graphql.contest;

import lombok.Data;

@Data
public class ContestRanking {

    private Double rating;

    private Integer globalRanking;

    private Integer attendedContestsCount;

    private Double topPercentage;

    private ContestBadge badge;

}