package com.codementor.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ai_mistakes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AiMistake {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "submission_id",
            nullable = false
    )
    private Submission submission;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    private User user;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "problem_id",
            nullable = false
    )
    private Problem problem;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MistakeType mistakeType;


    @Column(nullable = false)
    private String concept;


    @Column(
            columnDefinition = "TEXT",
            nullable = false
    )
    private String description;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MistakeSeverity severity;


    @Column(
            nullable = false,
            updatable = false
    )
    private LocalDateTime createdAt;


    @PrePersist
    protected void onCreate() {

        createdAt = LocalDateTime.now();

    }
}