package com.codementor.backend.service;

import com.codementor.backend.dto.ConnectedAccountsResponse;
import com.codementor.backend.dto.NotificationSettingsResponse;
import com.codementor.backend.dto.RegisterRequest;
import com.codementor.backend.dto.UpdateConnectedAccountsRequest;
import com.codementor.backend.dto.UpdateNotificationSettingsRequest;
import com.codementor.backend.dto.UpdateProfileRequest;
import com.codementor.backend.dto.UserProfileResponse;
import com.codementor.backend.dto.DeleteAccountRequest;
import com.codementor.backend.dto.ChangePasswordRequest;
import org.springframework.web.multipart.MultipartFile;
public interface UserService {

    void registerUser(RegisterRequest request);
    void removeProfilePicture(String email);
    void deleteAccount(
            String email,
            DeleteAccountRequest request
    );

    UserProfileResponse getCurrentUser(String email);

    UserProfileResponse updateCurrentUser(
            String email,
            UpdateProfileRequest request
        );

        String updateProfilePicture(
                String email,
                MultipartFile file
        );
        
        void changePassword(
                String email,
                ChangePasswordRequest request
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
