package com.codementor.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IndependentSolveSessionResponse {

    private Long problemId;

    private Boolean active;

    private LocalDateTime startedAt;

    private LocalDateTime endedAt;

    private Long durationSeconds;

    private Long submissionsDuringSession;

    private Boolean solvedIndependently;

    private String message;
}