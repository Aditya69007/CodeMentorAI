package com.codementor.backend.controller;

import com.codementor.backend.dto.AdminUserSummaryResponse;
import com.codementor.backend.dto.CreateAdminRequest;
import com.codementor.backend.dto.DeleteAdminRequest;
import com.codementor.backend.service.SuperAdminService;
import org.springframework.security.access.prepost.PreAuthorize;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/super-admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('SUPER_ADMIN')")
public class SuperAdminController {

    private final SuperAdminService superAdminService;

    // ==================================================
    // GET ALL ADMINS
    // ==================================================

    @GetMapping("/admins")
    public ResponseEntity<List<AdminUserSummaryResponse>> getAllAdmins() {

        return ResponseEntity.ok(
                superAdminService.getAllAdmins()
        );
    }

    // ==================================================
    // CREATE NEW ADMIN
    // ==================================================

    @PostMapping("/admins")
    public ResponseEntity<AdminUserSummaryResponse> createAdmin(
            @Valid @RequestBody CreateAdminRequest request
    ) {

        return ResponseEntity.ok(
                superAdminService.createAdmin(request)
        );
    }

    // ==================================================
    // DELETE / DISABLE USER
    // ==================================================

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long userId
    ) {

        superAdminService.deleteUser(userId);

        return ResponseEntity.noContent().build();
    }

    // ==================================================
    // DELETE / DISABLE ADMIN
    // ==================================================

    @DeleteMapping("/admins/{adminId}")
    public ResponseEntity<String> deleteAdmin(
            @PathVariable Long adminId,
            @Valid @RequestBody DeleteAdminRequest request
    ) {

        superAdminService.deleteAdmin(
                adminId,
                request.getPassword()
        );

        return ResponseEntity.ok(
                "Administrator deleted successfully"
        );
    }
}