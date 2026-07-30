package com.codementor.backend.session.dto;

import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SessionResponse {

    Long id;
    String deviceName;
    String browser;
    String operatingSystem;
    String ipAddress;
    String location;
    LocalDateTime createdAt;
    LocalDateTime lastSeen;
    LocalDateTime expiresAt;
    Boolean isActive;

}
