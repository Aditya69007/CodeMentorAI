package com.codementor.backend.controller;

import com.codementor.backend.dto.AdminAiAnalyticsResponse;

import com.codementor.backend.service.AdminAiAnalyticsService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ai-analytics/admin")
@RequiredArgsConstructor
public class AdminAiAnalyticsController {

    private final AdminAiAnalyticsService adminAiAnalyticsService;


    @GetMapping
    public ResponseEntity<AdminAiAnalyticsResponse>
    getAnalytics() {

        return ResponseEntity.ok(
                adminAiAnalyticsService.getAnalytics()
        );
    }
}