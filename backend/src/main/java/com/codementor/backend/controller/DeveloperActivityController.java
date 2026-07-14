package com.codementor.backend.controller;

import com.codementor.backend.dto.activity.DeveloperActivityResponse;
import com.codementor.backend.service.DeveloperActivityService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/developer-activity")
@RequiredArgsConstructor
public class DeveloperActivityController {

    private final DeveloperActivityService developerActivityService;


    // ==================================================
    // GET LOGGED-IN USER ACTIVITY
    // ==================================================

    @GetMapping("/me")
    public ResponseEntity<DeveloperActivityResponse> getMyActivity(
            Authentication authentication
    ) {

        DeveloperActivityResponse response =
                developerActivityService.getMyActivity(
                        authentication.getName()
                );

        return ResponseEntity.ok(response);
    }
}