package com.codementor.backend.dto.leetcode.graphql.skills;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SkillStatsMatchedUser {

    @JsonProperty("tagProblemCounts")
    private TagProblemCounts tagProblemCounts;

}