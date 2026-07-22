package com.codementor.backend.dto.leetcode.graphql.badges;

import lombok.Data;

import java.util.List;

@Data
public class MatchedUser {

    private List<Badge> badges;

}