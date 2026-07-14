package com.codementor.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class AdminDashboardAnalyticsResponse {

    private List<DailySubmissionStatsResponse> submissionActivity;

    private Map<String, Long> submissionStatusDistribution;

    private Map<String, Long> difficultyDistribution;

    private double acceptanceRate;

    private double aiAnalysisCoverage;
}