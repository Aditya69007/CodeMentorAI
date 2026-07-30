package com.codementor.backend.dto.leetcode.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SkillInfo {

    private String tagName;

    private String tagSlug;

    private Integer problemsSolved;

}