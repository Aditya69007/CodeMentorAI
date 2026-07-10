package com.codementor.backend.repository;

import com.codementor.backend.entity.AiProgressiveHint;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AiProgressiveHintRepository
        extends JpaRepository<AiProgressiveHint, Long> {

    Optional<AiProgressiveHint>
    findBySubmissionIdAndLevel(
            Long submissionId,
            Integer level
    );

    List<AiProgressiveHint>
    findBySubmissionIdOrderByLevelAsc(
            Long submissionId
    );
}