package com.codementor.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminAiAnalyticsResponse {

    private long totalAnalyses;

    private long totalChatMessages;

    private long totalProgressiveHints;

    private long totalMistakesDetected;

    private long usersWithMistakes;

    private String mostCommonMistakeType;

    private String mostCommonConcept;

    private Map<String, Long> mistakeTypeDistribution;

    private Map<String, Long> severityDistribution;

    private Map<String, Long> conceptDistribution;
}