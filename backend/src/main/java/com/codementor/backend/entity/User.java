package com.codementor.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnore;

import com.codementor.backend.entity.AuthProvider;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @JsonIgnore
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private AuthProvider provider = AuthProvider.LOCAL;

    @Column(unique = true)
    private String leetcodeUsername;

    private String profilePicture;

    @Column(unique = true)
    private String providerId;

    @Builder.Default
    private Boolean emailVerified = false;

    private String passwordResetToken;

    private LocalDateTime passwordResetTokenExpiry;

    @Column(nullable = false)
    @Builder.Default
    private Boolean enabled = true;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    private String githubUsername;

    @Builder.Default
    private Boolean githubConnected = false;

    @Builder.Default
    private Boolean leetcodeConnected = false;

    private LocalDateTime githubLastSyncedAt;

    private LocalDateTime leetcodeLastSyncedAt;

}