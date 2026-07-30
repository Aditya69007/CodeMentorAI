package com.codementor.backend.session.mapper;

import com.codementor.backend.session.dto.SessionResponse;
import com.codementor.backend.session.entity.UserSession;
import org.springframework.stereotype.Component;

@Component
public class UserSessionMapper {

    public SessionResponse toResponse(UserSession session) {

        return SessionResponse.builder()
                .id(session.getId())
                .deviceName(session.getDeviceName())
                .browser(session.getBrowser())
                .operatingSystem(session.getOperatingSystem())
                .ipAddress(session.getIpAddress())
                .location(session.getLocation())
                .createdAt(session.getCreatedAt())
                .lastSeen(session.getLastSeen())
                .expiresAt(session.getExpiresAt())
                .isActive(session.getIsActive())
                .build();
    }
}