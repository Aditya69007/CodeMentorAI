package com.codementor.backend.service.impl;

import com.codementor.backend.client.GitHubClient;
import com.codementor.backend.dto.RegisterRequest;
import com.codementor.backend.entity.User;
import com.codementor.backend.exception.ResourceAlreadyExistsException;
import com.codementor.backend.exception.ResourceNotFoundException;
import com.codementor.backend.notification.repository.NotificationRepository;
import com.codementor.backend.repository.UserRepository;
import com.codementor.backend.service.UserService;
import com.codementor.backend.session.repository.UserSessionRepository;
import com.codementor.backend.notification.builder.NotificationBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.codementor.backend.entity.Role;
import com.codementor.backend.entity.AuthProvider;
import com.codementor.backend.dto.UpdateProfileRequest;
import com.codementor.backend.dto.UserProfileResponse;
import com.codementor.backend.dto.ConnectedAccountsResponse;
import com.codementor.backend.dto.NotificationSettingsResponse;
import com.codementor.backend.dto.UpdateConnectedAccountsRequest;
import com.codementor.backend.dto.UpdateNotificationSettingsRequest;
import com.codementor.backend.dto.DeleteAccountRequest;
import com.codementor.backend.dto.ChangePasswordRequest;
import com.codementor.backend.export.dto.ProfileExport;
import com.codementor.backend.notification.enums.NotificationType;
import com.codementor.backend.notification.service.NotificationService;
import org.springframework.web.multipart.MultipartFile;
import com.codementor.backend.repository.SubmissionRepository;
import java.time.LocalDateTime;
import jakarta.transaction.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final GitHubClient gitHubClient;

    private final NotificationRepository notificationRepository;
    
    private final NotificationService notificationService;

    private final NotificationBuilder notificationBuilder;
    
    private final UserSessionRepository userSessionRepository;

    private final SubmissionRepository submissionRepository;

    private String generateUniqueUsername(
            String firstName,
            String lastName
    ) {

        String baseUsername =
                (firstName + lastName)
                        .toLowerCase()
                        .replaceAll("[^a-z0-9]", "");

        String username = baseUsername;

        int count = 1;

        while (userRepository.existsByUsername(username)) {

            username = baseUsername + count;

            count++;

        }

        return username;

    }
        

    @Override
    public void registerUser(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResourceAlreadyExistsException("Email already registered.");
        }

        String username =
                generateUniqueUsername(
                        request.getFirstName(),
                        request.getLastName()
                );

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(username)
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .provider(AuthProvider.LOCAL)
                .enabled(true)
                .build();

                userRepository.save(user);

                notificationBuilder.userRegistered(user);
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

        if (request.getUsername() != null &&
                !request.getUsername().trim().equalsIgnoreCase(user.getUsername())) {

                String username = request.getUsername().trim().toLowerCase();

                if (userRepository.existsByUsername(username)) {
                throw new ResourceAlreadyExistsException(
                        "Username already exists."
                );
                }

                user.setUsername(username);
        }

        userRepository.save(user);

        return mapToUserProfile(user);

        }

        @Override
        @Transactional
        public String updateProfilePicture(
        String email,
        MultipartFile file
) {

    if (file == null || file.isEmpty()) {
        throw new IllegalArgumentException(
                "Please select an image."
        );
    }

    String contentType = file.getContentType();

    if (!"image/jpeg".equals(contentType)
            && !"image/png".equals(contentType)
            && !"image/webp".equals(contentType)) {

        throw new IllegalArgumentException(
                "Only JPG, PNG, and WebP images are allowed."
        );
    }

    if (file.getSize() > 5 * 1024 * 1024) {
        throw new IllegalArgumentException(
                "Image must be smaller than 5 MB."
        );
    }

    try {

        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found."
                                )
                        );

        byte[] imageBytes = file.getBytes();

        String base64Image =
                java.util.Base64
                        .getEncoder()
                        .encodeToString(imageBytes);

        String profilePicture =
                "data:"
                        + contentType
                        + ";base64,"
                        + base64Image;

        user.setProfilePicture(profilePicture);

        userRepository.save(user);

        return profilePicture;

    } catch (Exception exception) {

        throw new RuntimeException(
                "Failed to upload profile picture.",
                exception
        );
    }
}

        @Override
        @Transactional
        public void changePassword(
                String email,
                ChangePasswordRequest request
        ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        // Admin password changes are only supported for LOCAL accounts
        if (user.getProvider() != AuthProvider.LOCAL) {
                throw new RuntimeException(
                        "Password cannot be changed for accounts using external authentication."
                );
        }

        // Verify current password
        if (request.getCurrentPassword() == null ||
                !passwordEncoder.matches(
                        request.getCurrentPassword(),
                        user.getPassword()
                )) {

                throw new RuntimeException(
                        "Current password is incorrect."
                );
        }

        // Confirm new password
        if (!request.getNewPassword().equals(
                request.getConfirmPassword()
        )) {

                throw new RuntimeException(
                        "New passwords do not match."
                );
        }

        // Prevent reusing the same password
        if (passwordEncoder.matches(
                request.getNewPassword(),
                user.getPassword()
        )) {

                throw new RuntimeException(
                        "New password must be different from your current password."
                );
        }

        // Save encoded password
        user.setPassword(
                passwordEncoder.encode(
                        request.getNewPassword()
                )
        );

        userRepository.save(user);

        notificationService.createNotification(
                user,
                "Password Changed",
                "Your password was successfully changed.",
                NotificationType.SECURITY,
                "shield",
                "/account/profile"
        );

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

        if (request.getGithubUsername() != null) {

            String githubUsername = request.getGithubUsername().trim();

            if (!githubUsername.isEmpty()) {

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

        }

        // LeetCode

        if (request.getLeetcodeUsername() != null) {

            String leetcodeUsername =
                    request.getLeetcodeUsername().trim();

            user.setLeetcodeUsername(leetcodeUsername);

            boolean connected = !leetcodeUsername.isEmpty();

            user.setLeetcodeConnected(connected);

            if (connected) {

                user.setLeetcodeLastSyncedAt(LocalDateTime.now());

            } else {

                user.setLeetcodeLastSyncedAt(null);

            }

        }

        userRepository.save(user);

        return mapToConnectedAccounts(user);

    }

        private UserProfileResponse mapToUserProfile(User user) {

        Long problemsSolved =
                submissionRepository
                        .countDistinctSolvedProblemsByUserId(
                                user.getId()
                        );

        return UserProfileResponse.builder()
                .userId(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .createdAt(user.getCreatedAt())
                .email(user.getEmail())
                .role(user.getRole())
                .provider(user.getProvider())
                .profilePicture(user.getProfilePicture())
                .githubUsername(user.getGithubUsername())
                .leetcodeUsername(user.getLeetcodeUsername())
                .problemsSolved(problemsSolved)
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

    @Override
    public NotificationSettingsResponse getNotificationSettings(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return NotificationSettingsResponse.builder()
                .emailNotifications(user.getEmailNotifications())
                .aiLearningTips(user.getAiLearningTips())
                .contestReminders(user.getContestReminders())
                .weeklyGrowthReport(user.getWeeklyGrowthReport())
                .interviewAlerts(user.getInterviewAlerts())
                .build();

    }

    @Override
    public NotificationSettingsResponse updateNotificationSettings(
            String email,
            UpdateNotificationSettingsRequest request
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setEmailNotifications(request.getEmailNotifications());
        user.setAiLearningTips(request.getAiLearningTips());
        user.setContestReminders(request.getContestReminders());
        user.setWeeklyGrowthReport(request.getWeeklyGrowthReport());
        user.setInterviewAlerts(request.getInterviewAlerts());

        userRepository.save(user);

        return NotificationSettingsResponse.builder()
                .emailNotifications(user.getEmailNotifications())
                .aiLearningTips(user.getAiLearningTips())
                .contestReminders(user.getContestReminders())
                .weeklyGrowthReport(user.getWeeklyGrowthReport())
                .interviewAlerts(user.getInterviewAlerts())
                .build();

    }

    @Override
    @Transactional
    public void deleteAccount(
            String email,
            DeleteAccountRequest request
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Verify password for LOCAL accounts
        if (user.getProvider() == AuthProvider.LOCAL) {

            if (request.getPassword() == null ||
                    !passwordEncoder.matches(
                            request.getPassword(),
                            user.getPassword()
                    )) {

                throw new RuntimeException("Invalid password.");

            }

        }

        // Delete notifications
        notificationRepository.deleteByUser(user);

        // Delete all sessions
        userSessionRepository.deleteByUser(user);

        // Finally delete user
        userRepository.delete(user);

    }

        @Override
        @Transactional
        public void removeProfilePicture(String email) {

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        user.setProfilePicture(null);

        userRepository.save(user);
        }

}