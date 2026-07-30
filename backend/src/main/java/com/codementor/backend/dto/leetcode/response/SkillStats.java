package com.codementor.backend.dto.leetcode.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SkillStats {

    private List<SkillInfo> fundamental;

    private List<SkillInfo> intermediate;

    private List<SkillInfo> advanced;

}