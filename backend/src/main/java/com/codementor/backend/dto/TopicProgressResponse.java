package com.codementor.backend.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TopicProgressResponse {

    private Long topicId;

    private String topicName;

    private String topicSlug;

    private long totalProblems;

    private long solvedProblems;

    private long attemptedProblems;

    private long totalAttempts;

    private long acceptedSubmissions;

    private long aiMistakes;

    private double acceptanceRate;

    private double masteryPercentage;

    private String level;

    private List<String> weakConcepts;

    private List<String> strongConcepts;

    private String recommendedProblemTitle;

    private Long recommendedProblemId;

    private String recommendedDifficulty;

    private String recommendationReason;

    private Integer estimatedLearningGain;

}