package com.codementor.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "interview_sessions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InterviewSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // =====================================================
    // USER
    // =====================================================

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    private User user;


    // =====================================================
    // INTERVIEW CONFIGURATION
    // =====================================================

    @Column(
            name = "interview_level",
            nullable = false,
            length = 50
    )
    private String interviewLevel;

    @Column(
            name = "developer_level",
            nullable = false,
            length = 50
    )
    private String developerLevel;


    // =====================================================
    // SESSION STATE
    // =====================================================

    @Column(nullable = false)
    private Boolean active;

    @Column(
            name = "current_question_number",
            nullable = false
    )
    private Integer currentQuestionNumber;

    @Column(
            name = "total_questions",
            nullable = false
    )
    private Integer totalQuestions;


    // =====================================================
    // FINAL RESULT
    // =====================================================

    @Column(
            name = "final_score"
    )
    private Integer finalScore;

    @Column(
            name = "final_feedback",
            columnDefinition = "TEXT"
    )
    private String finalFeedback;


    // =====================================================
    // TIMESTAMPS
    // =====================================================

    @Column(
            name = "started_at",
            nullable = false
    )
    private LocalDateTime startedAt;

    @Column(
            name = "completed_at"
    )
    private LocalDateTime completedAt;
}