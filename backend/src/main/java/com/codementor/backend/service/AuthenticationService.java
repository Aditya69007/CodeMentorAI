package com.codementor.backend.service;

import com.codementor.backend.dto.AuthResponse;
import com.codementor.backend.dto.LoginRequest;
import com.codementor.backend.dto.auth.ChangePasswordRequest;
import com.codementor.backend.dto.auth.ForgotPasswordRequest;
import com.codementor.backend.dto.auth.ResetPasswordRequest;
import com.codementor.backend.dto.request.DeleteAccountRequest;
import com.codementor.backend.dto.request.UpdateNotificationPreferencesRequest;
import com.codementor.backend.dto.request.UpdateThemeRequest;
import com.codementor.backend.dto.response.NotificationPreferencesResponse;
import com.codementor.backend.entity.ThemePreference;

public interface AuthenticationService {

    AuthResponse login(LoginRequest request);

    void changePassword(ChangePasswordRequest request);

    void forgotPassword(ForgotPasswordRequest request);

    void resetPassword(ResetPasswordRequest request);

    ThemePreference getThemePreference();

    void updateThemePreference(UpdateThemeRequest request);

    NotificationPreferencesResponse getNotificationPreferences();

    void updateNotificationPreferences(
            UpdateNotificationPreferencesRequest request
    );

    void deleteAccount(DeleteAccountRequest request);

}