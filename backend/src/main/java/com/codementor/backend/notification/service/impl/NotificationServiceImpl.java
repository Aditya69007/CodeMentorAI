package com.codementor.backend.notification.service.impl;

import com.codementor.backend.entity.User;
import com.codementor.backend.notification.dto.NotificationResponse;
import com.codementor.backend.notification.dto.NotificationSummaryResponse;
import com.codementor.backend.notification.entity.Notification;
import com.codementor.backend.notification.enums.NotificationType;
import com.codementor.backend.notification.mapper.NotificationMapper;
import com.codementor.backend.notification.repository.NotificationRepository;
import com.codementor.backend.notification.service.NotificationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    private final NotificationMapper notificationMapper;

    @Override
    public Notification createNotification(
            User user,
            String title,
            String message,
            NotificationType type,
            String icon,
            String actionUrl
    ) {

        Notification notification = Notification.builder()
                .user(user)
                .title(title)
                .message(message)
                .type(type)
                .icon(icon)
                .actionUrl(actionUrl)
                .build();

        return notificationRepository.save(notification);

    }

    @Override
    public List<NotificationResponse> getNotifications(User user) {

        return notificationRepository
                .findByUserOrderByCreatedAtDesc(user)
                .stream()
                .map(notificationMapper::toResponse)
                .toList();

    }

    @Override
    public NotificationSummaryResponse getNotificationSummary(User user) {

        long unread =
                notificationRepository.countByUserAndIsReadFalse(user);

        long total =
                notificationRepository.countByUser(user);

        return NotificationSummaryResponse.builder()
                .totalNotifications(total)
                .unreadNotifications(unread)
                .build();

    }

    @Override
    public void markAsRead(Long notificationId, User user) {

        Notification notification =
                notificationRepository.findById(notificationId)
                        .orElseThrow(() ->
                                new EntityNotFoundException("Notification not found."));

        if (!notification.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("You cannot access this notification.");
        }

        notification.setIsRead(true);

        notificationRepository.save(notification);

    }

    @Override
    public void markAllAsRead(User user) {

        List<Notification> notifications =
                notificationRepository.findByUserAndIsReadFalseOrderByCreatedAtDesc(user);

        notifications.forEach(notification ->
                notification.setIsRead(true));

        notificationRepository.saveAll(notifications);

    }

    @Override
    public void deleteNotification(Long notificationId, User user) {

        Notification notification =
                notificationRepository.findById(notificationId)
                        .orElseThrow(() ->
                                new EntityNotFoundException("Notification not found."));

        if (!notification.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("You cannot delete this notification.");
        }

        notificationRepository.delete(notification);

    }

        @Override
        public void deleteAllNotifications(User user) {

        notificationRepository.deleteByUser(user);

        }

}