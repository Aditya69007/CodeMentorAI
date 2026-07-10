package com.codementor.backend.dto;

import com.codementor.backend.entity.Language;
import com.codementor.backend.entity.SubmissionStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubmissionResponse {

    private String sourceCode;

    private Long id;

    private Long problemId;

    private String problemTitle;

    private String output;

    private String errorMessage;

    private Language language;

    private SubmissionStatus status;

    private Integer executionTime;

    private Integer memoryUsed;

    private LocalDateTime createdAt;

    private Integer passedTestCases;

    private Integer totalTestCases;

    private Boolean failedOnHiddenTest;
}