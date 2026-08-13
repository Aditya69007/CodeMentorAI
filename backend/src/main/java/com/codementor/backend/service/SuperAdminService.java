package com.codementor.backend.service;

import com.codementor.backend.dto.AdminUserSummaryResponse;
import com.codementor.backend.dto.CreateAdminRequest;

import java.util.List;

public interface SuperAdminService {

    List<AdminUserSummaryResponse> getAllAdmins();

    AdminUserSummaryResponse createAdmin(
            CreateAdminRequest request
    );

    void deleteUser(Long userId);

    void deleteAdmin(Long adminId, String password);
}