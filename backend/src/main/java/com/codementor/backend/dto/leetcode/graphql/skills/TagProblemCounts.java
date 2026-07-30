package com.codementor.backend.dto.leetcode.graphql.skills;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class TagProblemCounts {

    @JsonProperty("fundamental")
    private List<SkillCategory> fundamental;

    @JsonProperty("intermediate")
    private List<SkillCategory> intermediate;

    @JsonProperty("advanced")
    private List<SkillCategory> advanced;

}