package com.codementor.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class AdminDashboardStatsResponse {

    private long totalUsers;

    private long totalAdmins;

    private long totalProblems;

    private long totalTopics;

    private long totalSubmissions;

    private long acceptedSubmissions;

    private long totalAiAnalyses;
}