package com.codementor.backend.notification.builder;

import com.codementor.backend.entity.Problem;
import com.codementor.backend.entity.Role;
import com.codementor.backend.entity.User;
import com.codementor.backend.notification.enums.NotificationType;
import com.codementor.backend.notification.service.NotificationService;
import com.codementor.backend.repository.UserRepository;
import com.codementor.backend.entity.AdminSettings;
import com.codementor.backend.repository.AdminSettingsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationBuilder {

    private final NotificationService notificationService;
    private final UserRepository userRepository;
    private final AdminSettingsRepository adminSettingsRepository;


    // ==================================================
    // USER NOTIFICATIONS
    // ==================================================

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


    // ==================================================
    // ADMIN NOTIFICATIONS
    // ==================================================

        public void userDeleted(
                User admin,
                User deletedUser
        ) {

        if (!isAdminNotificationsEnabled(admin)) {
                return;
        }

        notificationService.createNotification(
                admin,
                "User Deleted",
                deletedUser.getFirstName()
                        + " "
                        + deletedUser.getLastName()
                        + " was deleted from the platform.",
                NotificationType.ACCOUNT,
                "trash",
                "/admin/users"
        );
        }

        public void userRegistered(User user) {

        notifyAdmins(
                "New User Registered",
                user.getFirstName()
                        + " "
                        + user.getLastName()
                        + " has created a new CodeMentorAI account.",
                "user-plus"
        );
        }

    // ==================================================
    // PROBLEM CREATED
    // ==================================================

    public void problemCreated(Problem problem) {

        notifyAdmins(
                "Problem Created",
                "A new problem \""
                        + problem.getTitle()
                        + "\" was created.",
                "plus-circle"
        );
    }


    // ==================================================
    // PROBLEM UPDATED
    // ==================================================

    public void problemUpdated(Problem problem) {

        notifyAdmins(
                "Problem Updated",
                "The problem \""
                        + problem.getTitle()
                        + "\" was updated.",
                "edit"
        );
    }


    // ==================================================
    // PROBLEM DELETED
    // ==================================================

    public void problemDeleted(Problem problem) {

        notifyAdmins(
                "Problem Deleted",
                "The problem \""
                        + problem.getTitle()
                        + "\" was deleted.",
                "trash"
        );
    }


    // ==================================================
    // SEND NOTIFICATION TO ALL ADMINS
    // ==================================================

        private void notifyAdmins(
                String title,
                String message,
                String icon
        ) {

        userRepository
                .findByRoleOrderByCreatedAtDesc(Role.ADMIN)
                .forEach(admin -> {

                        if (!isAdminNotificationsEnabled(admin)) {
                        return;
                        }

                        notificationService.createNotification(
                                admin,
                                title,
                                message,
                                NotificationType.ACCOUNT,
                                icon,
                                "/admin/problems"
                        );
                });

        userRepository
                .findByRoleOrderByCreatedAtDesc(Role.SUPER_ADMIN)
                .forEach(admin -> {

                        if (!isAdminNotificationsEnabled(admin)) {
                        return;
                        }

                        notificationService.createNotification(
                                admin,
                                title,
                                message,
                                NotificationType.ACCOUNT,
                                icon,
                                "/admin/problems"
                        );
                });
        }

        private boolean isAdminNotificationsEnabled(User admin) {

        return adminSettingsRepository
                .findByAdminEmail(admin.getEmail())
                .map(AdminSettings::isAdminNotifications)
                .orElse(true);
        }

}