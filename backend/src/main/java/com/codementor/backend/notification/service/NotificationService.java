package com.codementor.backend.notification.service;

import com.codementor.backend.entity.User;
import com.codementor.backend.notification.dto.NotificationResponse;
import com.codementor.backend.notification.dto.NotificationSummaryResponse;
import com.codementor.backend.notification.entity.Notification;
import com.codementor.backend.notification.enums.NotificationType;

import java.util.List;

public interface NotificationService {

    List<NotificationResponse> getNotifications(User user);

    NotificationSummaryResponse getNotificationSummary(User user);

    void markAsRead(Long notificationId, User user);

    void markAllAsRead(User user);

    void deleteNotification(Long notificationId, User user);

    void deleteAllNotifications(User user);

    Notification createNotification(
            User user,
            String title,
            String message,
            NotificationType type,
            String icon,
            String actionUrl
    );

}