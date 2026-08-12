package com.codementor.backend.dto.portfolio;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AiSkillsSummaryResponse {

    private List<SkillCategory> categories;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SkillCategory {

        private String category;

        private Integer score;

        private List<String> skills;

    }

    private String developerLevel;
}