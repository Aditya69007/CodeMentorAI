package com.codementor.backend.dto.leetcode.response;

import lombok.Data;

@Data
public class ProblemStats {

    private Integer easySolved;

    private Integer mediumSolved;

    private Integer hardSolved;

    private Integer easyFailed;

    private Integer mediumFailed;

    private Integer hardFailed;

}