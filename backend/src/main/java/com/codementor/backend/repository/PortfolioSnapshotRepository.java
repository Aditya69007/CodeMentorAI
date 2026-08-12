package com.codementor.backend.repository;

import com.codementor.backend.entity.PortfolioSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PortfolioSnapshotRepository
        extends JpaRepository<PortfolioSnapshot, Long> {

    Optional<PortfolioSnapshot> findByUserId(Long userId);

    boolean existsByUserId(Long userId);
}