package com.codementor.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminSettingsResponse {

    private boolean compactDashboard;

    private boolean adminNotifications;

    private boolean platformAlerts;

    private int defaultPageSize;

    private boolean autoRefreshDashboard;

    private int autoRefreshInterval;

    private boolean confirmBeforeDelete;
}