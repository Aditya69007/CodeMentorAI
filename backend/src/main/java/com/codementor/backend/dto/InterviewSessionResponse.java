package com.codementor.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterviewSessionResponse {

    private Long sessionId;

    private String interviewLevel;

    private String developerLevel;

    private Boolean active;

    private Integer currentQuestionNumber;

    private Integer totalQuestions;

    private Integer finalScore;

    private String finalFeedback;

    private LocalDateTime startedAt;

    private LocalDateTime completedAt;

    private InterviewQuestionResponse currentQuestion;

    private List<InterviewQuestionResponse> questions;

    private String message;
}