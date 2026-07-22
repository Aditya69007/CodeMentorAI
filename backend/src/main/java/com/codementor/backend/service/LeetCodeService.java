package com.codementor.backend.service;

import com.codementor.backend.dto.LeetCodeProfileResponse;

public interface LeetCodeService {

    LeetCodeProfileResponse getProfile(String username);

}