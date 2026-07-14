package com.codementor.backend.service;

import com.codementor.backend.dto.AdminUserDetailResponse;
import com.codementor.backend.dto.AdminUserSummaryResponse;

import java.util.List;

public interface AdminUserService {
    
    void deleteUser(Long userId);

    List<AdminUserSummaryResponse> getAllUsers();

    AdminUserDetailResponse getUserDetail(Long userId);
}