package com.codementor.backend.service.impl;

import com.codementor.backend.dto.AuthResponse;
import com.codementor.backend.dto.LoginRequest;
import com.codementor.backend.entity.ThemePreference;
import com.codementor.backend.entity.User;
import com.codementor.backend.repository.UserRepository;
import com.codementor.backend.security.JwtService;
import com.codementor.backend.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.codementor.backend.exception.BadRequestException;
import com.codementor.backend.notification.builder.NotificationBuilder;
import com.codementor.backend.dto.auth.ChangePasswordRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.codementor.backend.session.entity.UserSession;
import com.codementor.backend.session.repository.UserSessionRepository;

import com.codementor.backend.dto.auth.ForgotPasswordRequest;
import com.codementor.backend.dto.auth.ResetPasswordRequest;
import com.codementor.backend.dto.request.DeleteAccountRequest;
import com.codementor.backend.dto.request.UpdateNotificationPreferencesRequest;
import com.codementor.backend.dto.request.UpdateThemeRequest;
import com.codementor.backend.dto.response.NotificationPreferencesResponse;
import com.codementor.backend.service.EmailService;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final EmailService emailService;
    private final NotificationBuilder notificationBuilder;
    private final UserSessionRepository userSessionRepository;


    @Override
    public AuthResponse login(LoginRequest request) {

        System.out.println("Login method called");

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("Invalid email or password")
                );

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        )) {

            throw new RuntimeException(
                    "Invalid email or password"
            );
        }

        if (!Boolean.TRUE.equals(user.getEnabled())) {

            throw new RuntimeException(
                    "Your account is disabled"
            );
        }

        String token =
                jwtService.generateToken(user.getEmail());

        UserSession session = UserSession.builder()
                .user(user)
                .deviceName("Unknown Device")
                .browser("Unknown Browser")
                .operatingSystem("Unknown OS")
                .ipAddress("Unknown IP")
                .location("Unknown Location")
                .refreshTokenId(token)
                .expiresAt(LocalDateTime.now().plusDays(7))
                .build();

        session = userSessionRepository.save(session);

        return AuthResponse.builder()
                .token(token)
                .message("Login successful")
                .userId(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .profilePicture(user.getProfilePicture())
                .sessionId(session.getId())
                .build();
    }

        @Override
        public void changePassword(ChangePasswordRequest request) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new BadRequestException("User not found"));

        if (!passwordEncoder.matches(
                request.getCurrentPassword(),
                user.getPassword())) {

                throw new BadRequestException("Current password is incorrect");
        }

        if (!request.getNewPassword()
                .equals(request.getConfirmPassword())) {

                throw new BadRequestException("New password and confirm password do not match");
        }

        if (passwordEncoder.matches(
                request.getNewPassword(),
                user.getPassword())) {

                throw new BadRequestException("New password cannot be the same as the current password");
        }

        String newPassword = request.getNewPassword();

        if (!newPassword.matches(
                "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$")) {

        throw new BadRequestException(
                "Password must contain at least 8 characters, one uppercase letter, one lowercase letter, one number, and one special character.");
        }

        user.setPassword(
                passwordEncoder.encode(newPassword));

        userRepository.save(user);
        notificationBuilder.passwordChanged(user);
        }

        @Override
        public void forgotPassword(ForgotPasswordRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElse(null);

        // Don't reveal whether the email exists
        if (user == null) {
                return;
        }

        String token = UUID.randomUUID().toString();

        user.setPasswordResetToken(token);

        user.setPasswordResetTokenExpiry(
                LocalDateTime.now().plusMinutes(30)
        );

        userRepository.save(user);

        String resetLink =
                "http://localhost:5173/reset-password?token=" + token;

        String html = """
                <html>
                        <body style="font-family:Arial,sans-serif;">
                        <h2>Reset Your Password</h2>

                        <p>Hello %s,</p>

                        <p>
                                We received a request to reset your password.
                        </p>

                        <p>
                                <a href="%s"
                                style="
                                background:#2563eb;
                                color:white;
                                padding:12px 20px;
                                text-decoration:none;
                                border-radius:6px;">
                                Reset Password
                                </a>
                        </p>

                        <p>This link expires in <b>30 minutes</b>.</p>

                        <hr>

                        <p>If you didn't request this, you can safely ignore this email.</p>

                        </body>
                </html>
                """.formatted(
                user.getFirstName(),
                resetLink
        );

        emailService.sendEmail(
                user.getEmail(),
                "Reset your CodeMentorAI Password",
                html
        );

        }

        @Override
        public void resetPassword(ResetPasswordRequest request) {

        User user = userRepository
                .findByPasswordResetToken(request.getToken())
                .orElseThrow(() ->
                        new BadRequestException("Invalid reset token."));

        if (user.getPasswordResetTokenExpiry()
                .isBefore(LocalDateTime.now())) {

                throw new BadRequestException(
                        "Reset link has expired.");
        }

        if (!request.getNewPassword()
                .equals(request.getConfirmPassword())) {

                throw new BadRequestException(
                        "Passwords do not match.");
        }

        String newPassword = request.getNewPassword();

        if (!newPassword.matches(
                "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$")) {

                throw new BadRequestException(
                        "Password must contain at least 8 characters, one uppercase letter, one lowercase letter, one number, and one special character.");
        }

        user.setPassword(
                passwordEncoder.encode(newPassword));

        user.setPasswordResetToken(null);
        user.setPasswordResetTokenExpiry(null);

        userRepository.save(user);
        notificationBuilder.passwordReset(user);

        }

        @Override
        public ThemePreference getThemePreference() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new BadRequestException("User not found"));

        return user.getThemePreference();
        }
                
        @Override
        public void updateThemePreference(UpdateThemeRequest request) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new BadRequestException("User not found"));

        user.setThemePreference(request.getThemePreference());

        userRepository.save(user);
        notificationBuilder.themeChanged(user);
        }

        @Override
        public NotificationPreferencesResponse getNotificationPreferences() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new BadRequestException("User not found"));

        return NotificationPreferencesResponse.builder()
                .emailNotifications(user.getEmailNotifications())
                .aiLearningTips(user.getAiLearningTips())
                .contestReminders(user.getContestReminders())
                .weeklyGrowthReport(user.getWeeklyGrowthReport())
                .interviewAlerts(user.getInterviewAlerts())
                .build();
        }

        @Override
        public void updateNotificationPreferences(
                UpdateNotificationPreferencesRequest request
        ) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new BadRequestException("User not found"));

        user.setEmailNotifications(request.getEmailNotifications());
        user.setAiLearningTips(request.getAiLearningTips());
        user.setContestReminders(request.getContestReminders());
        user.setWeeklyGrowthReport(request.getWeeklyGrowthReport());
        user.setInterviewAlerts(request.getInterviewAlerts());

        userRepository.save(user);
        notificationBuilder.notificationPreferencesUpdated(user);
        }

        @Override
        public void deleteAccount(DeleteAccountRequest request) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new BadRequestException("User not found"));

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

                throw new BadRequestException("Incorrect password.");
        }

        userRepository.delete(user);

        SecurityContextHolder.clearContext();
        }

}