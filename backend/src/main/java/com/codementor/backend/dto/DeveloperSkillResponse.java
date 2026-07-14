package com.codementor.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeveloperSkillResponse {

    private Long topicId;

    private String topicName;

    private Long totalSubmissions;

    private Long acceptedSubmissions;

    private Long totalMistakes;

    private Double acceptanceRate;

    private Integer skillScore;

    private String skillLevel;

    private String message;
}