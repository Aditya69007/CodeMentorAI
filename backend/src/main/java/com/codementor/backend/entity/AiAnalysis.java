package com.codementor.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ai_analyses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AiAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "submission_id",
            nullable = false,
            unique = true
    )
    private Submission submission;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String explanation;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String hint;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String conceptToStudy;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}