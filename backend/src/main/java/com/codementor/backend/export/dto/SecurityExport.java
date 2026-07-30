package com.codementor.backend.export.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SecurityExport {

    private Integer activeSessions;

    private Boolean emailNotifications;

    private Boolean aiLearningTips;

    private Boolean contestReminders;

    private Boolean weeklyGrowthReport;

    private Boolean interviewAlerts;

}