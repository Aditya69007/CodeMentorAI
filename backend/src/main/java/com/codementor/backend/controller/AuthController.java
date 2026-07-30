package com.codementor.backend.controller;

import com.codementor.backend.dto.RegisterRequest;
import com.codementor.backend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.codementor.backend.dto.AuthResponse;
import com.codementor.backend.dto.LoginRequest;
import com.codementor.backend.service.AuthenticationService;
import com.codementor.backend.dto.auth.ChangePasswordRequest;
import com.codementor.backend.dto.auth.ForgotPasswordRequest;
import com.codementor.backend.dto.auth.ResetPasswordRequest;
import com.codementor.backend.dto.request.DeleteAccountRequest;
import com.codementor.backend.dto.request.UpdateNotificationPreferencesRequest;
import com.codementor.backend.dto.request.UpdateThemeRequest;
import com.codementor.backend.dto.response.NotificationPreferencesResponse;
import com.codementor.backend.entity.ThemePreference;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(
            @Valid @RequestBody RegisterRequest request) {

        userService.registerUser(request);

        return ResponseEntity.ok("User registered successfully.");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
        @Valid @RequestBody LoginRequest request) {
                System.out.println("AuthController Login Called");
        return ResponseEntity.ok(authenticationService.login(request));
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(
            @Valid @RequestBody ChangePasswordRequest request) {

        authenticationService.changePassword(request);

        return ResponseEntity.ok("Password changed successfully");
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(
            @Valid @RequestBody ForgotPasswordRequest request) {

        authenticationService.forgotPassword(request);

        return ResponseEntity.ok(
                "If an account with that email exists, a password reset link has been sent."
        );
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(
            @Valid @RequestBody ResetPasswordRequest request) {

        authenticationService.resetPassword(request);

        return ResponseEntity.ok(
                "Password reset successfully."
        );
    }

    @GetMapping("/theme")
    public ResponseEntity<ThemePreference> getThemePreference() {

        return ResponseEntity.ok(
                authenticationService.getThemePreference()
        );
    }

    @PutMapping("/theme")
    public ResponseEntity<String> updateThemePreference(
            @Valid @RequestBody UpdateThemeRequest request
    ) {

        authenticationService.updateThemePreference(request);

        return ResponseEntity.ok(
                "Theme preference updated successfully."
        );
    }

    @GetMapping("/notifications")
    public ResponseEntity<NotificationPreferencesResponse> getNotificationPreferences() {

        return ResponseEntity.ok(
                authenticationService.getNotificationPreferences()
        );
    }

    @PutMapping("/notifications")
    public ResponseEntity<String> updateNotificationPreferences(
            @Valid @RequestBody UpdateNotificationPreferencesRequest request
    ) {

        authenticationService.updateNotificationPreferences(request);

        return ResponseEntity.ok(
                "Notification preferences updated successfully."
        );
    }

    @PostMapping("/delete-account")
    public ResponseEntity<String> deleteAccount(
            @Valid @RequestBody DeleteAccountRequest request
    ) {

        authenticationService.deleteAccount(request);

        return ResponseEntity.ok(
                "Account deleted successfully."
        );
    }


}