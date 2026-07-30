package com.codementor.backend.dto.leetcode.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProblemStats {

    private Integer totalSolved;

    private Integer easySolved;

    private Integer mediumSolved;

    private Integer hardSolved;

    private Integer totalSubmissions;

    private Integer easySubmissions;

    private Integer mediumSubmissions;

    private Integer hardSubmissions;

    private Double acceptanceRate;

}