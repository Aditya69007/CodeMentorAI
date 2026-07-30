package com.codementor.backend.notification.controller;

import com.codementor.backend.entity.User;
import com.codementor.backend.notification.dto.NotificationResponse;
import com.codementor.backend.notification.dto.NotificationSummaryResponse;
import com.codementor.backend.notification.service.NotificationService;
import com.codementor.backend.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    private final SecurityUtils securityUtils;

    @GetMapping
    public ResponseEntity<List<NotificationResponse>> getNotifications() {

        User user = securityUtils.getCurrentUser();

        return ResponseEntity.ok(
                notificationService.getNotifications(user)
        );

    }

    @GetMapping("/summary")
    public ResponseEntity<NotificationSummaryResponse> getSummary() {

        User user = securityUtils.getCurrentUser();

        return ResponseEntity.ok(
                notificationService.getNotificationSummary(user)
        );

    }

    @PutMapping("/{notificationId}/read")
    public ResponseEntity<Void> markAsRead(
            @PathVariable Long notificationId
    ) {

        User user = securityUtils.getCurrentUser();

        notificationService.markAsRead(notificationId, user);

        return ResponseEntity.ok().build();

    }

    @PutMapping("/read-all")
    public ResponseEntity<Void> markAllAsRead() {

        User user = securityUtils.getCurrentUser();

        notificationService.markAllAsRead(user);

        return ResponseEntity.ok().build();

    }

    @DeleteMapping("/{notificationId}")
    public ResponseEntity<Void> deleteNotification(
            @PathVariable Long notificationId
    ) {

        User user = securityUtils.getCurrentUser();

        notificationService.deleteNotification(notificationId, user);

        return ResponseEntity.noContent().build();

    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllNotifications() {

        User user = securityUtils.getCurrentUser();

        notificationService.deleteAllNotifications(user);

        return ResponseEntity.noContent().build();

    }

    @PostMapping("/test")
    public ResponseEntity<Void> createTestNotifications() {

        User user = securityUtils.getCurrentUser();

        notificationService.createNotification(
                user,
                "New Login Detected",
                "Your account was accessed successfully.",
                com.codementor.backend.notification.enums.NotificationType.SECURITY,
                "shield",
                "/account/sessions"
        );

        notificationService.createNotification(
                user,
                "Profile Updated",
                "Your profile information has been updated.",
                com.codementor.backend.notification.enums.NotificationType.ACCOUNT,
                "user",
                "/account/profile"
        );

        notificationService.createNotification(
                user,
                "AI Mentor Analysis Ready",
                "Your latest submission has been analyzed.",
                com.codementor.backend.notification.enums.NotificationType.AI,
                "cpu",
                "/ai"
        );

        notificationService.createNotification(
                user,
                "Interview Recommendations Ready",
                "Your interview preparation has been updated.",
                com.codementor.backend.notification.enums.NotificationType.INTERVIEW,
                "briefcase",
                "/interview"
        );

        notificationService.createNotification(
                user,
                "Learning Plan Updated",
                "Your personalized learning plan is ready.",
                com.codementor.backend.notification.enums.NotificationType.LEARNING,
                "book",
                "/learning-plan"
        );

        notificationService.createNotification(
                user,
                "Contest Reminder",
                "A contest starts in one hour.",
                com.codementor.backend.notification.enums.NotificationType.CONTEST,
                "award",
                "/problems"
        );

        notificationService.createNotification(
                user,
                "Revision Reminder",
                "Time to revise your weak topics.",
                com.codementor.backend.notification.enums.NotificationType.REVISION,
                "refresh",
                "/revision-plan"
        );

        notificationService.createNotification(
                user,
                "Growth Report Generated",
                "Your weekly growth report is ready.",
                com.codementor.backend.notification.enums.NotificationType.GROWTH,
                "trending-up",
                "/growth-report"
        );

        notificationService.createNotification(
                user,
                "Welcome to CodeMentorAI",
                "Your account is ready to use.",
                com.codementor.backend.notification.enums.NotificationType.SYSTEM,
                "settings",
                "/dashboard"
        );

        return ResponseEntity.ok().build();
    }

}