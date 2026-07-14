package com.codementor.backend.repository;

import com.codementor.backend.entity.IndependentSolveSession;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IndependentSolveSessionRepository
        extends JpaRepository<IndependentSolveSession, Long> {


    // ==================================================
    // FIND ACTIVE SESSION FOR USER + PROBLEM
    // ==================================================

    Optional<IndependentSolveSession>
    findByUserIdAndProblemIdAndActiveTrue(
            Long userId,
            Long problemId
    );


    // ==================================================
    // CHECK ACTIVE SESSION EXISTS
    // ==================================================

    boolean existsByUserIdAndProblemIdAndActiveTrue(
            Long userId,
            Long problemId
    );


    // ==================================================
    // GET USER'S INDEPENDENT SOLVE HISTORY
    // ==================================================

    List<IndependentSolveSession>
    findByUserIdOrderByStartedAtDesc(
            Long userId
    );


    // ==================================================
    // COUNT ALL COMPLETED INDEPENDENT SESSIONS
    // ==================================================

    long countByUserIdAndActiveFalse(
            Long userId
    );


    // ==================================================
    // COUNT SUCCESSFULLY SOLVED INDEPENDENT SESSIONS
    // ==================================================

    long countByUserIdAndActiveFalseAndSolvedIndependentlyTrue(
            Long userId
    );

    List<IndependentSolveSession> findByUserIdAndProblemIdOrderByStartedAtDesc(
                Long userId,
                Long problemId
        );

}