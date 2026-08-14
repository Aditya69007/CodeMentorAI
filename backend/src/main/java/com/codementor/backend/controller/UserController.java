package com.codementor.backend.controller;

import com.codementor.backend.dto.UpdateProfileRequest;
import com.codementor.backend.dto.UserProfileResponse;
import com.codementor.backend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.codementor.backend.dto.ConnectedAccountsResponse;
import com.codementor.backend.dto.NotificationSettingsResponse;
import com.codementor.backend.dto.UpdateConnectedAccountsRequest;
import com.codementor.backend.dto.UpdateNotificationSettingsRequest;
import com.codementor.backend.dto.DeleteAccountRequest;
import com.codementor.backend.dto.ChangePasswordRequest;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getCurrentUser(
            Authentication authentication
    ) {

        return ResponseEntity.ok(
                userService.getCurrentUser(
                        authentication.getName()
                )
        );

    }

    @PutMapping("/me")
    public ResponseEntity<UserProfileResponse> updateCurrentUser(
            Authentication authentication,
            @Valid @RequestBody UpdateProfileRequest request
    ) {

        return ResponseEntity.ok(
                userService.updateCurrentUser(
                        authentication.getName(),
                        request
                )
        );

    }

        @PostMapping("/change-password")
        public ResponseEntity<Void> changePassword(
                Authentication authentication,
                @Valid @RequestBody ChangePasswordRequest request
        ) {

        userService.changePassword(
                authentication.getName(),
                request
        );

        return ResponseEntity.noContent().build();
        }

        @GetMapping("/connected-accounts")
        public ResponseEntity<ConnectedAccountsResponse> getConnectedAccounts(
                Authentication authentication
        ) {

        return ResponseEntity.ok(
                userService.getConnectedAccounts(
                        authentication.getName()
                )
        );

        }

        @PutMapping("/connected-accounts")
        public ResponseEntity<ConnectedAccountsResponse> updateConnectedAccounts(
                Authentication authentication,
                @RequestBody UpdateConnectedAccountsRequest request
        ) {

        return ResponseEntity.ok(
                userService.updateConnectedAccounts(
                        authentication.getName(),
                        request
                )
        );

        }

        @GetMapping("/notification-settings")
        public ResponseEntity<NotificationSettingsResponse> getNotificationSettings(
                Authentication authentication
        ) {

        return ResponseEntity.ok(
                userService.getNotificationSettings(
                        authentication.getName()
                )
        );

        }

        @PutMapping("/notification-settings")
        public ResponseEntity<NotificationSettingsResponse> updateNotificationSettings(
                Authentication authentication,
                @RequestBody UpdateNotificationSettingsRequest request
        ) {

        return ResponseEntity.ok(
                userService.updateNotificationSettings(
                        authentication.getName(),
                        request
                )
        );

        }

        @DeleteMapping("/delete-account")
        public ResponseEntity<Void> deleteAccount(
                Authentication authentication,
                @RequestBody DeleteAccountRequest request
        ) {

        userService.deleteAccount(
                authentication.getName(),
                request
        );

        return ResponseEntity.noContent().build();

        }

        @PostMapping(
                value = "/me/profile-picture",
                consumes = "multipart/form-data"
        )
        public ResponseEntity<String> updateProfilePicture(
                Authentication authentication,
                @RequestParam("file") MultipartFile file
        ) {

        return ResponseEntity.ok(
                userService.updateProfilePicture(
                        authentication.getName(),
                        file
                )
        );
        }

@DeleteMapping("/me/profile-picture")
public ResponseEntity<Void> removeProfilePicture(
        Authentication authentication
) {

    userService.removeProfilePicture(
            authentication.getName()
    );

    return ResponseEntity.noContent().build();
}

}