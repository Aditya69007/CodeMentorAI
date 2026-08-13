package com.codementor.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "admin_settings",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "admin_email")
    }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "admin_email", nullable = false, unique = true)
    private String adminEmail;

    @Column(nullable = false)
    private boolean compactDashboard;

    @Column(nullable = false)
    private boolean adminNotifications;

    @Column(nullable = false)
    private boolean platformAlerts;

    @Column(nullable = false)
    private int defaultPageSize;

    @Column(nullable = false)
    private boolean autoRefreshDashboard;

    @Column(nullable = false)
    private int autoRefreshInterval;

    @Column(nullable = false)
    private boolean confirmBeforeDelete;
}