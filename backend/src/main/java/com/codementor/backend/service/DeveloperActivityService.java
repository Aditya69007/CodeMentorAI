package com.codementor.backend.service;

import com.codementor.backend.dto.activity.DeveloperActivityResponse;

public interface DeveloperActivityService {

    DeveloperActivityResponse getMyActivity(
            String userEmail
    );
}