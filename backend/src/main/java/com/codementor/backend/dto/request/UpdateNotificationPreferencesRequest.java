package com.codementor.backend.dto.request;

import lombok.Data;

@Data
public class UpdateNotificationPreferencesRequest {

    private Boolean emailNotifications;

    private Boolean aiLearningTips;

    private Boolean contestReminders;

    private Boolean weeklyGrowthReport;

    private Boolean interviewAlerts;
}