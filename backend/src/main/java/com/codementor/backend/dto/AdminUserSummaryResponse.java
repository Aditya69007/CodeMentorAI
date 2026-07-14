package com.codementor.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class AdminUserSummaryResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String profilePicture;

    private Boolean enabled;

    private LocalDateTime createdAt;

    private long totalSubmissions;

    private long solvedProblems;

    private long acceptedSubmissions;

    private double acceptanceRate;

    private long totalAiAnalyses;

    private long totalMistakes;
}