package com.codementor.backend.repository;

import com.codementor.backend.entity.AiAnalysis;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AiAnalysisRepository
        extends JpaRepository<AiAnalysis, Long> {

        void deleteBySubmissionUserId(Long userId);

    Optional<AiAnalysis> findBySubmissionId(
            Long submissionId
    );

    long countBySubmissionUserId(
            Long userId
    );
}