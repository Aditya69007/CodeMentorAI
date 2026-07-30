package com.codementor.backend.dto.leetcode.graphql.problems;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProblemProgressMatchedUser {

    @JsonProperty("submitStats")
    private SubmitStats submitStats;

}