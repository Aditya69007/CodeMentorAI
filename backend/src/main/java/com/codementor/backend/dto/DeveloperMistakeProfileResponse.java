package com.codementor.backend.dto;

import com.codementor.backend.entity.MistakeType;
import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeveloperMistakeProfileResponse {

    private Long totalMistakes;

    private MistakeType mostCommonMistake;

    private String weakestConcept;

    private Map<String, Long> mistakeTypeBreakdown;

    private Map<String, Long> severityBreakdown;

    private Map<String, Long> conceptBreakdown;

    private List<String> insights;
}