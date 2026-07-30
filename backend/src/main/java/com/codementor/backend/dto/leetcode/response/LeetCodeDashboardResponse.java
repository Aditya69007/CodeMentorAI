package com.codementor.backend.dto.leetcode.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class LeetCodeDashboardResponse {

    private String username;

    private ContestInfo contest;

    private List<BadgeInfo> badges;

    private CalendarInfo calendar;

    private ProblemStats problems;

    private SkillStats skills;

    private List<RecentSubmission> recentSubmissions;

    private AnalyticsInfo analytics;

}