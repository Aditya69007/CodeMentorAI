package com.codementor.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationSettingsResponse {

    private Boolean emailNotifications;

    private Boolean aiLearningTips;

    private Boolean contestReminders;

    private Boolean weeklyGrowthReport;

    private Boolean interviewAlerts;

}