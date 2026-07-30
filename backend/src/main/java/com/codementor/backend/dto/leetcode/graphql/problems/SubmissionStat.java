package com.codementor.backend.dto.leetcode.graphql.problems;

import lombok.Data;

@Data
public class SubmissionStat {

    private String difficulty;

    private Integer count;

    private Integer submissions;

}