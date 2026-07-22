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
import com.codementor.backend.dto.UpdateConnectedAccountsRequest;

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

}