package com.codementor.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "featured_projects",
    uniqueConstraints = {
        @UniqueConstraint(
            columnNames = {
                "user_id",
                "repository_name"
            }
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeaturedProject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Owner of these featured projects.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Exact GitHub repository name.
     * Example:
     * CodeMentorAI
     * Student-Dropout-Prediction
     */
    @Column(name = "repository_name", nullable = false, length = 150)
    private String repositoryName;

    /**
     * Controls display order in portfolio.
     * 1, 2, 3...
     */
    @Column(name = "display_order", nullable = false)
    private Integer displayOrder;
}