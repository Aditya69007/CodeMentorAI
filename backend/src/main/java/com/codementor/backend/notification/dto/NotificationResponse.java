package com.codementor.backend.notification.dto;

import com.codementor.backend.notification.enums.NotificationType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationResponse {

    private Long id;

    private String title;

    private String message;

    private NotificationType type;

    private String icon;

    private String actionUrl;

    private Boolean isRead;

    private LocalDateTime createdAt;

}