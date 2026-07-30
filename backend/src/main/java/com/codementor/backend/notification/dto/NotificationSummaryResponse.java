package com.codementor.backend.notification.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationSummaryResponse {

    private Long totalNotifications;

    private Long unreadNotifications;

}