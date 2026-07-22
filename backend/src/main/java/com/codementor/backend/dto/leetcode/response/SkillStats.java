package com.codementor.backend.dto.leetcode.response;

import lombok.Data;

import java.util.Map;

@Data
public class SkillStats {

    private Map<String, Integer> fundamental;

    private Map<String, Integer> intermediate;

    private Map<String, Integer> advanced;

}