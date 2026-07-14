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
public class SolutionEvolutionResponse {

    private Long submissionId;

    private Integer attemptNumber;

    private SubmissionStatus status;

    private Language language;

    private Integer passedTestCases;

    private Integer totalTestCases;

    private Integer executionTime;

    private Integer memoryUsed;

    private Boolean failedOnHiddenTest;

    private String sourceCode;

    private String aiExplanation;

    private String aiHint;

    private String conceptToStudy;

    private String evolutionStatus;

    private Integer passedTestCasesChange;

    private Boolean improvedFromPreviousAttempt;

    private String evolutionMessage;

    private LocalDateTime createdAt;
}