package com.codementor.backend.notification.builder;

import com.codementor.backend.entity.User;
import com.codementor.backend.notification.enums.NotificationType;
import com.codementor.backend.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationBuilder {

    private final NotificationService notificationService;

    public void passwordChanged(User user) {
        notificationService.createNotification(
                user,
                "Password Changed",
                "Your account password was changed successfully.",
                NotificationType.SECURITY,
                "lock",
                "/settings/security"
        );
    }

    public void passwordReset(User user) {
        notificationService.createNotification(
                user,
                "Password Reset",
                "Your password has been reset successfully.",
                NotificationType.SECURITY,
                "lock-reset",
                "/login"
        );
    }

    public void profileUpdated(User user) {
        notificationService.createNotification(
                user,
                "Profile Updated",
                "Your profile information has been updated.",
                NotificationType.ACCOUNT,
                "user",
                "/profile"
        );
    }

    public void themeChanged(User user) {
        notificationService.createNotification(
                user,
                "Theme Updated",
                "Your appearance preferences were updated.",
                NotificationType.ACCOUNT,
                "palette",
                "/settings/appearance"
        );
    }

    public void notificationPreferencesUpdated(User user) {
        notificationService.createNotification(
                user,
                "Notification Preferences Updated",
                "Your notification settings have been updated.",
                NotificationType.ACCOUNT,
                "bell",
                "/settings/notifications"
        );
    }

    public void accountDeleted(User user) {
        notificationService.createNotification(
                user,
                "Account Deleted",
                "Your CodeMentorAI account has been deleted.",
                NotificationType.ACCOUNT,
                "trash",
                "/"
        );
    }
}