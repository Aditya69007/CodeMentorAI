package com.codementor.backend.service.impl;

import com.codementor.backend.client.GitHubClient;
import com.codementor.backend.dto.RegisterRequest;
import com.codementor.backend.entity.User;
import com.codementor.backend.exception.ResourceAlreadyExistsException;
import com.codementor.backend.repository.UserRepository;
import com.codementor.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.codementor.backend.entity.Role;
import com.codementor.backend.entity.AuthProvider;
import com.codementor.backend.dto.UpdateProfileRequest;
import com.codementor.backend.dto.UserProfileResponse;
import com.codementor.backend.dto.ConnectedAccountsResponse;
import com.codementor.backend.dto.UpdateConnectedAccountsRequest;
import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final GitHubClient gitHubClient;

    @Override
    public void registerUser(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResourceAlreadyExistsException("Email already registered.");
        }

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .provider(AuthProvider.LOCAL)
                .enabled(true)
                .build();

        userRepository.save(user);
    }

    @Override
    public UserProfileResponse getCurrentUser(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return mapToUserProfile(user);

    }

    @Override
    public UserProfileResponse updateCurrentUser(
            String email,
            UpdateProfileRequest request
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        userRepository.save(user);

        return mapToUserProfile(user);

    }

    @Override
    public ConnectedAccountsResponse getConnectedAccounts(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return mapToConnectedAccounts(user);

    }

    @Override
    public ConnectedAccountsResponse updateConnectedAccounts(
            String email,
            UpdateConnectedAccountsRequest request
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // GitHub

        String githubUsername = request.getGithubUsername();

        if (githubUsername != null && !githubUsername.isBlank()) {

            // Validate GitHub username
            gitHubClient.getUserProfile(githubUsername);

            user.setGithubUsername(githubUsername);
            user.setGithubConnected(true);
            user.setGithubLastSyncedAt(LocalDateTime.now());

        } else {

            user.setGithubUsername(null);
            user.setGithubConnected(false);
            user.setGithubLastSyncedAt(null);

        }

        // LeetCode
        user.setLeetcodeUsername(request.getLeetcodeUsername());

        boolean leetcodeConnected =
                request.getLeetcodeUsername() != null &&
                !request.getLeetcodeUsername().trim().isEmpty();

        user.setLeetcodeConnected(leetcodeConnected);

        if (leetcodeConnected) {
            user.setLeetcodeLastSyncedAt(LocalDateTime.now());
        } else {
            user.setLeetcodeLastSyncedAt(null);
        }

        userRepository.save(user);

        return mapToConnectedAccounts(user);

    }

    private UserProfileResponse mapToUserProfile(User user) {

        return UserProfileResponse.builder()
                .userId(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .provider(user.getProvider())
                .profilePicture(user.getProfilePicture())
                .build();

    }

    private ConnectedAccountsResponse mapToConnectedAccounts(User user) {

        return ConnectedAccountsResponse.builder()
                .githubUsername(user.getGithubUsername())
                .leetcodeUsername(user.getLeetcodeUsername())
                .githubConnected(Boolean.TRUE.equals(user.getGithubConnected()))
                .leetcodeConnected(Boolean.TRUE.equals(user.getLeetcodeConnected()))
                .githubLastSyncedAt(user.getGithubLastSyncedAt())
                .leetcodeLastSyncedAt(user.getLeetcodeLastSyncedAt())
                .build();

    }
}