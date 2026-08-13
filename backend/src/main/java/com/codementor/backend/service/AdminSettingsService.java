package com.codementor.backend.service;

import com.codementor.backend.dto.AdminSettingsResponse;
import com.codementor.backend.dto.UpdateAdminSettingsRequest;

public interface AdminSettingsService {

    AdminSettingsResponse getSettings();

    AdminSettingsResponse updateSettings(
            UpdateAdminSettingsRequest request
    );
}