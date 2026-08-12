package com.codementor.backend.entity;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import lombok.*;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "portfolio_snapshots",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_portfolio_snapshot_user",
                        columnNames = "user_id"
                )
        }
)
public class PortfolioSnapshot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ==================================================
    // USER
    // ==================================================

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            unique = true
    )
    private User user;

    // ==================================================
    // GITHUB SNAPSHOT
    // ==================================================

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private JsonNode githubData;

    @Column(length = 64)
    private String githubDataHash;

    private LocalDateTime githubLastSyncedAt;

    // ==================================================
    // LEETCODE SNAPSHOT
    // ==================================================

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private JsonNode leetcodeData;

    @Column(length = 64)
    private String leetcodeDataHash;

    private LocalDateTime leetcodeLastSyncedAt;

    // ==================================================
    // GROWTH REPORT
    // ==================================================

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private JsonNode growthReportData;

    private LocalDateTime growthReportUpdatedAt;

    // ==================================================
    // AI DEVELOPER SUMMARY
    // ==================================================

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private JsonNode developerSummaryData;

    private LocalDateTime developerSummaryUpdatedAt;

    // ==================================================
    // AI SKILLS SUMMARY
    // ==================================================

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private JsonNode skillsSummaryData;

    private LocalDateTime skillsSummaryUpdatedAt;

    // ==================================================
    // PORTFOLIO SCORE
    // ==================================================

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private JsonNode portfolioScoreData;

    private LocalDateTime portfolioScoreUpdatedAt;

    // ==================================================
    // OVERALL SNAPSHOT
    // ==================================================

    @Column(length = 64)
    private String sourceDataHash;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // ==================================================
    // JPA LIFECYCLE
    // ==================================================

    @PrePersist
    public void onCreate() {

        LocalDateTime now = LocalDateTime.now();

        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    public void onUpdate() {

        updatedAt = LocalDateTime.now();
    }
}