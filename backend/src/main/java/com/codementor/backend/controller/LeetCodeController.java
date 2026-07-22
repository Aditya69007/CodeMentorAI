package com.codementor.backend.controller;

import com.codementor.backend.client.LeetCodeClient;
import com.codementor.backend.dto.LeetCodeProfileResponse;
import com.codementor.backend.service.LeetCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/leetcode")
@RequiredArgsConstructor
public class LeetCodeController {

    private final LeetCodeService leetCodeService;

    @GetMapping("/profile/{username}")
    public LeetCodeProfileResponse profile(
            @PathVariable String username
    ) {
        return leetCodeService.getProfile(username);
    }
}