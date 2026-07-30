package com.codementor.backend.session.entity;

import com.codementor.backend.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user_sessions")
public class UserSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String deviceName;

    private String browser;

    private String operatingSystem;

    private String ipAddress;

    private String location;

    private String refreshTokenId;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime lastSeen;

    private LocalDateTime expiresAt;

    @Builder.Default
    private Boolean isActive = true;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
        lastSeen = LocalDateTime.now();
    }
}