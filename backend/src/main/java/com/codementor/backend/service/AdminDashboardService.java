package com.codementor.backend.service;

import com.codementor.backend.dto.AdminDashboardAnalyticsResponse;
import com.codementor.backend.dto.AdminDashboardStatsResponse;
import com.codementor.backend.dto.AdminPlatformAnalyticsResponse;

public interface AdminDashboardService {

    AdminDashboardStatsResponse getDashboardStats();

    AdminDashboardAnalyticsResponse getDashboardAnalytics();

    AdminPlatformAnalyticsResponse getPlatformAnalytics();
}