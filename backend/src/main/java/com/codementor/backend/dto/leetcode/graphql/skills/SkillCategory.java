package com.codementor.backend.dto.leetcode.graphql.skills;

import lombok.Data;

@Data
public class SkillCategory {

    private String tagName;

    private String tagSlug;

    private Integer problemsSolved;

}