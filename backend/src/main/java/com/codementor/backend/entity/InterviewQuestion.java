package com.codementor.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "interview_questions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InterviewQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // =====================================================
    // INTERVIEW SESSION
    // =====================================================

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "interview_session_id",
            nullable = false
    )
    private InterviewSession interviewSession;


    // =====================================================
    // QUESTION
    // =====================================================

    @Column(
            name = "question_number",
            nullable = false
    )
    private Integer questionNumber;

    @Column(
            name = "question",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String question;

    @Column(
            name = "question_type",
            nullable = false,
            length = 50
    )
    private String questionType;

    @Column(
            name = "concept",
            length = 100
    )
    private String concept;

    @Column(
            name = "difficulty",
            nullable = false,
            length = 50
    )
    private String difficulty;


    // =====================================================
    // USER ANSWER
    // =====================================================

    @Column(
            name = "user_answer",
            columnDefinition = "TEXT"
    )
    private String userAnswer;


    // =====================================================
    // AI EVALUATION
    // =====================================================

    @Column(
            name = "answer_score"
    )
    private Integer answerScore;

    @Column(
            name = "ai_feedback",
            columnDefinition = "TEXT"
    )
    private String aiFeedback;

    @Column(
            name = "strengths",
            columnDefinition = "TEXT"
    )
    private String strengths;

    @Column(
            name = "improvements",
            columnDefinition = "TEXT"
    )
    private String improvements;


    // =====================================================
    // STATE
    // =====================================================

    @Column(
            name = "answered",
            nullable = false
    )
    private Boolean answered;


    // =====================================================
    // TIMESTAMPS
    // =====================================================

    @Column(
            name = "created_at",
            nullable = false
    )
    private LocalDateTime createdAt;

    @Column(
            name = "answered_at"
    )
    private LocalDateTime answeredAt;
}