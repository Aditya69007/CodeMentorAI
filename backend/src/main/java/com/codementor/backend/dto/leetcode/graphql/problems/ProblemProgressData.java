package com.codementor.backend.dto.leetcode.graphql.problems;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ProblemProgressData {

    @JsonProperty("allQuestionsCount")
    private List<QuestionCount> allQuestionsCount;

    @JsonProperty("matchedUser")
    private ProblemProgressMatchedUser matchedUser;

}