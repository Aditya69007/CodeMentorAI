package com.codementor.backend.controller;

import com.codementor.backend.dto.AdminDashboardAnalyticsResponse;
import com.codementor.backend.dto.AdminDashboardStatsResponse;
import com.codementor.backend.dto.AdminPlatformAnalyticsResponse;
import com.codementor.backend.dto.AdminUserDetailResponse;
import com.codementor.backend.dto.AdminUserSummaryResponse;
import com.codementor.backend.dto.ProblemResponse;

import com.codementor.backend.entity.Difficulty;

import com.codementor.backend.service.AdminDashboardService;
import com.codementor.backend.service.AdminUserService;
import com.codementor.backend.service.ProblemService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {


    private final AdminDashboardService adminDashboardService;

    private final AdminUserService adminUserService;

    private final ProblemService problemService;



    // ==================================================
    // DASHBOARD STATS
    // ==================================================

    @GetMapping("/dashboard/stats")
    public ResponseEntity<AdminDashboardStatsResponse>
    getDashboardStats() {

        return ResponseEntity.ok(
                adminDashboardService.getDashboardStats()
        );
    }



    // ==================================================
    // DASHBOARD ANALYTICS
    // ==================================================

    @GetMapping("/dashboard/analytics")
    public ResponseEntity<AdminDashboardAnalyticsResponse>
    getDashboardAnalytics() {

        return ResponseEntity.ok(
                adminDashboardService.getDashboardAnalytics()
        );
    }



    // ==================================================
    // PLATFORM ANALYTICS
    // ==================================================

    @GetMapping("/platform-analytics")
    public ResponseEntity<AdminPlatformAnalyticsResponse>
    getPlatformAnalytics() {

        return ResponseEntity.ok(
                adminDashboardService.getPlatformAnalytics()
        );
    }



    // ==================================================
    // GET ALL USERS
    // ==================================================

    @GetMapping("/users")
    public ResponseEntity<List<AdminUserSummaryResponse>>
    getAllUsers() {

        return ResponseEntity.ok(
                adminUserService.getAllUsers()
        );
    }



    // ==================================================
    // GET USER DETAIL
    // ==================================================

    @GetMapping("/users/{userId}")
    public ResponseEntity<AdminUserDetailResponse>
    getUserDetail(
            @PathVariable Long userId
    ) {

        return ResponseEntity.ok(
                adminUserService.getUserDetail(userId)
        );
    }



    // ==================================================
    // DELETE USER
    // ==================================================

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void>
    deleteUser(
            @PathVariable Long userId
    ) {

        adminUserService.deleteUser(userId);

        return ResponseEntity
                .noContent()
                .build();
    }



    // ==================================================
    // GET ADMIN PROBLEMS
    // ==================================================

    @GetMapping("/problems")
    public ResponseEntity<Page<ProblemResponse>>
    getAdminProblems(

            @RequestParam(defaultValue = "")
            String title,

            @RequestParam(required = false)
            Difficulty difficulty,

            @RequestParam(required = false)
            Long topicId,

            @RequestParam(required = false)
            Boolean active,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size

    ) {

        return ResponseEntity.ok(

                problemService.getAdminProblems(

                        title,

                        difficulty,

                        topicId,

                        active,

                        page,

                        size
                )
        );
    }
}