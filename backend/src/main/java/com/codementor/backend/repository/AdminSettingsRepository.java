package com.codementor.backend.repository;

import com.codementor.backend.entity.AdminSettings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminSettingsRepository
        extends JpaRepository<AdminSettings, Long> {

    Optional<AdminSettings> findByAdminEmail(String adminEmail);
}