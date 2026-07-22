package com.codementor.backend.dto.leetcode.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContestInfo {

    private Double rating;

    private Integer globalRanking;

    private Integer attendedContestsCount;

    private Double topPercentage;

    private String badge;

}