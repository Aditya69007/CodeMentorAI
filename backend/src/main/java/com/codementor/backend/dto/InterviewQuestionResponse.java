package com.codementor.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterviewQuestionResponse {

    private Long questionId;

    private Integer questionNumber;

    private String question;

    private String questionType;

    private String concept;

    private String difficulty;

    private Boolean answered;

    private Integer answerScore;

    private String aiFeedback;

    private String strengths;

    private String improvements;
}