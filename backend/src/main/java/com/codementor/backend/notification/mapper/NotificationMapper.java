package com.codementor.backend.notification.mapper;

import com.codementor.backend.notification.dto.NotificationResponse;
import com.codementor.backend.notification.entity.Notification;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {

    public NotificationResponse toResponse(Notification notification) {

        if (notification == null) {
            return null;
        }

        return NotificationResponse.builder()
                .id(notification.getId())
                .title(notification.getTitle())
                .message(notification.getMessage())
                .type(notification.getType())
                .icon(notification.getIcon())
                .actionUrl(notification.getActionUrl())
                .isRead(notification.getIsRead())
                .createdAt(notification.getCreatedAt())
                .build();

    }

}