package com.codementor.backend.service;

import com.codementor.backend.dto.ConnectedAccountsResponse;
import com.codementor.backend.dto.NotificationSettingsResponse;
import com.codementor.backend.dto.RegisterRequest;
import com.codementor.backend.dto.UpdateConnectedAccountsRequest;
import com.codementor.backend.dto.UpdateNotificationSettingsRequest;
import com.codementor.backend.dto.UpdateProfileRequest;
import com.codementor.backend.dto.UserProfileResponse;
import com.codementor.backend.dto.DeleteAccountRequest;


public interface UserService {

    void registerUser(RegisterRequest request);
    void deleteAccount(
            String email,
            DeleteAccountRequest request
    );

    UserProfileResponse getCurrentUser(String email);

    UserProfileResponse updateCurrentUser(
            String email,
            UpdateProfileRequest request
    );

    ConnectedAccountsResponse getConnectedAccounts(String email);

    ConnectedAccountsResponse updateConnectedAccounts(
        String email,
        UpdateConnectedAccountsRequest request
    );

    NotificationSettingsResponse getNotificationSettings(String email);

    NotificationSettingsResponse updateNotificationSettings(
            String email,
            UpdateNotificationSettingsRequest request
    );


}
