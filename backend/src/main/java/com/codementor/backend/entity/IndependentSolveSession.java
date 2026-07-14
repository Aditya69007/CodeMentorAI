package com.codementor.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "independent_solve_sessions")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IndependentSolveSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // ==================================================
    // USER
    // ==================================================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    private User user;


    // ==================================================
    // PROBLEM
    // ==================================================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "problem_id",
            nullable = false
    )
    private Problem problem;


    // ==================================================
    // SESSION STATE
    // ==================================================

    @Column(nullable = false)
    private Boolean active;


    @Column(
            name = "started_at",
            nullable = false
    )
    private LocalDateTime startedAt;


    @Column(name = "ended_at")
    private LocalDateTime endedAt;


    // ==================================================
    // RESULT
    // ==================================================

    @Column(name = "duration_seconds")
    private Long durationSeconds;


    @Column(
            name = "submissions_during_session",
            nullable = false
    )
    private Long submissionsDuringSession;


    @Column(
            name = "solved_independently",
            nullable = false
    )
    private Boolean solvedIndependently;


    // ==================================================
    // CREATED AT
    // ==================================================

    @Column(
            name = "created_at",
            nullable = false,
            updatable = false
    )
    private LocalDateTime createdAt;


    @PrePersist
    public void prePersist() {

        if (active == null) {
            active = true;
        }

        if (submissionsDuringSession == null) {
            submissionsDuringSession = 0L;
        }

        if (solvedIndependently == null) {
            solvedIndependently = false;
        }

        if (startedAt == null) {
            startedAt = LocalDateTime.now();
        }

        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}