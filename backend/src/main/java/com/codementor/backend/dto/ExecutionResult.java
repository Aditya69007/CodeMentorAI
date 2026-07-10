package com.codementor.backend.dto;

import com.codementor.backend.entity.SubmissionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExecutionResult {

    private SubmissionStatus status;

    private String output;

    private String errorMessage;

    private Integer executionTime;

    private Integer memoryUsed;

    private Integer passedTestCases;

    private Integer totalTestCases;

    private Boolean failedOnHiddenTest;
}