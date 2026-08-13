package com.codementor.backend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateAdminSettingsRequest {

    private boolean compactDashboard;

    private boolean adminNotifications;

    private boolean platformAlerts;

    private int defaultPageSize;

    private boolean autoRefreshDashboard;

    private int autoRefreshInterval;

    private boolean confirmBeforeDelete;
}