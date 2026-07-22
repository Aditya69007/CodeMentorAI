package com.codementor.backend.dto.leetcode.graphql.badges;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class BadgesData {

    @JsonProperty("matchedUser")
    private MatchedUser matchedUser;

}