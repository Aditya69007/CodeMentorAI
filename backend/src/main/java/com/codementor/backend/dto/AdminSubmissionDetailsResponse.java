package com.codementor.backend.dto;

import com.codementor.backend.entity.Language;
import com.codementor.backend.entity.SubmissionStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminSubmissionDetailsResponse {

    private Long id;

    private Long userId;

    private String userName;

    private String userEmail;

    private Long problemId;

    private String problemTitle;

    private String sourceCode;

    private String output;

    private String errorMessage;

    private Language language;

    private SubmissionStatus status;

    private Integer passedTestCases;

    private Integer totalTestCases;

    private Integer executionTime;

    private Integer memoryUsed;

    private Boolean failedOnHiddenTest;

    private LocalDateTime createdAt;
}