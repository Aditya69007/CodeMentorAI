package com.codementor.backend.service;

import com.codementor.backend.dto.ConnectedAccountsResponse;
import com.codementor.backend.dto.RegisterRequest;
import com.codementor.backend.dto.UpdateConnectedAccountsRequest;
import com.codementor.backend.dto.UpdateProfileRequest;
import com.codementor.backend.dto.UserProfileResponse;


public interface UserService {

    void registerUser(RegisterRequest request);

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
}