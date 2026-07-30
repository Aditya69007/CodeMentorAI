package com.codementor.backend.dto.leetcode.graphql.skills;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SkillStatsData {

    @JsonProperty("matchedUser")
    private SkillStatsMatchedUser matchedUser;

}