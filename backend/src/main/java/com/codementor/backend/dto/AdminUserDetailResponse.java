package com.codementor.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class AdminUserDetailResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String profilePicture;

    private Boolean enabled;

    private LocalDateTime createdAt;


    // PERFORMANCE

    private long totalSubmissions;

    private long solvedProblems;

    private long acceptedSubmissions;

    private double acceptanceRate;

    private long totalAiAnalyses;

    private long totalMistakes;


    // ANALYTICS

    private Map<String, Long> submissionStatusDistribution;

    private Map<String, Long> mistakeTypeDistribution;

    private List<AdminUserActivityResponse> submissionActivity;

    private List<AdminUserTopicPerformanceResponse> topicPerformance;

    private List<AdminRecentSubmissionResponse> recentSubmissions;
}