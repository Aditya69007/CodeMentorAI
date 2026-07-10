package com.codementor.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "ai_progressive_hints",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {
                                "submission_id",
                                "level"
                        }
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AiProgressiveHint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "submission_id",
            nullable = false
    )
    private Submission submission;

    @Column(nullable = false)
    private Integer level;

    @Column(
            columnDefinition = "TEXT",
            nullable = false
    )
    private String response;

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