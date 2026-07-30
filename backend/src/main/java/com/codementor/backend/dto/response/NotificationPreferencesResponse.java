package com.codementor.backend.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationPreferencesResponse {

    private Boolean emailNotifications;

    private Boolean aiLearningTips;

    private Boolean contestReminders;

    private Boolean weeklyGrowthReport;

    private Boolean interviewAlerts;
}