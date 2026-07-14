package com.codementor.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminPlatformAnalyticsResponse {

    private Long totalUsers;

    private Long totalProblems;

    private Long totalTopics;

    private Long totalSubmissions;

    private Long acceptedSubmissions;

    private Double acceptanceRate;

    private Long totalAiAnalyses;

    private Double aiAnalysisCoverage;

    private List<DailySubmissionStatsResponse> submissionActivity;

    private Map<String, Long> submissionStatusDistribution;

    private Map<String, Long> languageDistribution;

    private Map<String, Long> difficultyDistribution;
}