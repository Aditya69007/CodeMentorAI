package com.codementor.backend.notification.dto;

import com.codementor.backend.notification.enums.NotificationType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateNotificationRequest {

    private Long userId;

    private String title;

    private String message;

    private NotificationType type;

    private String icon;

    private String actionUrl;

}