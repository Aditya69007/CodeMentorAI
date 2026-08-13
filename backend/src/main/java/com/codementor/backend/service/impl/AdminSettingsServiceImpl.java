package com.codementor.backend.service.impl;

import com.codementor.backend.dto.AdminSettingsResponse;
import com.codementor.backend.dto.UpdateAdminSettingsRequest;
import com.codementor.backend.entity.AdminSettings;
import com.codementor.backend.repository.AdminSettingsRepository;
import com.codementor.backend.service.AdminSettingsService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminSettingsServiceImpl
        implements AdminSettingsService {

    private final AdminSettingsRepository adminSettingsRepository;

    @Override
    @Transactional
    public AdminSettingsResponse getSettings() {

        String adminEmail = getCurrentAdminEmail();

        AdminSettings settings =
                adminSettingsRepository
                        .findByAdminEmail(adminEmail)
                        .orElseGet(() -> createDefaultSettings(adminEmail));

        return mapToResponse(settings);
    }

    @Override
    @Transactional
    public AdminSettingsResponse updateSettings(
            UpdateAdminSettingsRequest request
    ) {

        String adminEmail = getCurrentAdminEmail();

        AdminSettings settings =
                adminSettingsRepository
                        .findByAdminEmail(adminEmail)
                        .orElseGet(() -> createDefaultSettings(adminEmail));

        settings.setCompactDashboard(
                request.isCompactDashboard()
        );

        settings.setAdminNotifications(
                request.isAdminNotifications()
        );

        settings.setPlatformAlerts(
                request.isPlatformAlerts()
        );

        settings.setDefaultPageSize(
                request.getDefaultPageSize()
        );

        settings.setAutoRefreshDashboard(
                request.isAutoRefreshDashboard()
        );

        settings.setAutoRefreshInterval(
                request.getAutoRefreshInterval()
        );

        settings.setConfirmBeforeDelete(
                request.isConfirmBeforeDelete()
        );

        AdminSettings saved =
                adminSettingsRepository.save(settings);

        return mapToResponse(saved);
    }

    private AdminSettings createDefaultSettings(
            String adminEmail
    ) {

        AdminSettings settings =
                AdminSettings.builder()
                        .adminEmail(adminEmail)
                        .compactDashboard(false)
                        .adminNotifications(true)
                        .platformAlerts(true)
                        .defaultPageSize(10)
                        .autoRefreshDashboard(false)
                        .autoRefreshInterval(60)
                        .confirmBeforeDelete(true)
                        .build();

        return adminSettingsRepository.save(settings);
    }

    private String getCurrentAdminEmail() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated()) {

            throw new IllegalStateException(
                    "Authenticated administrator required"
            );
        }

        String email = authentication.getName();

        if (email == null || email.isBlank()) {

            throw new IllegalStateException(
                    "Administrator email could not be determined"
            );
        }

        return email;
    }

    private AdminSettingsResponse mapToResponse(
            AdminSettings settings
    ) {

        return AdminSettingsResponse.builder()
                .compactDashboard(settings.isCompactDashboard())
                .adminNotifications(settings.isAdminNotifications())
                .platformAlerts(settings.isPlatformAlerts())
                .defaultPageSize(settings.getDefaultPageSize())
                .autoRefreshDashboard(settings.isAutoRefreshDashboard())
                .autoRefreshInterval(settings.getAutoRefreshInterval())
                .confirmBeforeDelete(settings.isConfirmBeforeDelete())
                .build();
    }
}