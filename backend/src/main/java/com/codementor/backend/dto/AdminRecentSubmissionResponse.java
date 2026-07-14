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
public class AdminRecentSubmissionResponse {

    private Long id;

    private Long problemId;

    private String problemTitle;

    private String topicName;

    private String difficulty;

    private String language;

    private String status;

    private Integer passedTestCases;

    private Integer totalTestCases;

    private Integer executionTime;

    private LocalDateTime createdAt;
}