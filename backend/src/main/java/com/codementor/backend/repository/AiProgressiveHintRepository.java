package com.codementor.backend.repository;

import com.codementor.backend.entity.AiProgressiveHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AiProgressiveHintRepository
        extends JpaRepository<AiProgressiveHint, Long> {


    // ==================================================
    // FIND SAVED HINT FOR SUBMISSION + LEVEL
    // ==================================================

    Optional<AiProgressiveHint>
    findBySubmissionIdAndLevel(
            Long submissionId,
            Integer level
    );


    // ==================================================
    // GET ALL HINTS FOR ONE SUBMISSION
    // ==================================================

    List<AiProgressiveHint>
    findBySubmissionIdOrderByLevelAsc(
            Long submissionId
    );


    // ==================================================
    // TOTAL HINTS USED BY USER
    // ==================================================

    long countBySubmissionUserId(
            Long userId
    );


    // ==================================================
    // TOTAL UNIQUE PROBLEMS WHERE USER USED HINTS
    // ==================================================

    @Query("""
            SELECT COUNT(DISTINCT h.submission.problem.id)
            FROM AiProgressiveHint h
            WHERE h.submission.user.id = :userId
            """)
    long countDistinctProblemsWithHintsByUserId(
            @Param("userId") Long userId
    );


    // ==================================================
    // HINT USAGE GROUPED BY LEVEL
    // ==================================================

    @Query("""
            SELECT h.level, COUNT(h)
            FROM AiProgressiveHint h
            WHERE h.submission.user.id = :userId
            GROUP BY h.level
            ORDER BY h.level ASC
            """)
    List<Object[]> countHintsByLevelForUser(
            @Param("userId") Long userId
    );

}