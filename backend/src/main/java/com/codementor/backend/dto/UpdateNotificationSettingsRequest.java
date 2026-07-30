package com.codementor.backend.dto;

import lombok.Data;

@Data
public class UpdateNotificationSettingsRequest {

    private Boolean emailNotifications;

    private Boolean aiLearningTips;

    private Boolean contestReminders;

    private Boolean weeklyGrowthReport;

    private Boolean interviewAlerts;

}