package com.codementor.backend.repository;

import com.codementor.backend.entity.Submission;
import com.codementor.backend.entity.SubmissionStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface SubmissionRepository
        extends JpaRepository<Submission, Long> {


    List<Submission> findByUserId(Long userId);


    List<Submission> findByProblemId(Long problemId);


    Page<Submission> findByUserId(
            Long userId,
            Pageable pageable
    );


    Page<Submission> findByUserIdAndProblemId(
            Long userId,
            Long problemId,
            Pageable pageable
    );


    @Query("""
            SELECT COUNT(s)
            FROM Submission s
            WHERE s.user.id = :userId
            AND s.problem.id = :problemId
            AND s.status = :status
            AND s.createdAt > :afterDate
            """)
    long countByUserAndProblemAndStatusAfterDate(

            @Param("userId")
            Long userId,

            @Param("problemId")
            Long problemId,

            @Param("status")
            SubmissionStatus status,

            @Param("afterDate")
            LocalDateTime afterDate
    );


        boolean existsByUserIdAndProblemIdAndStatus(
                Long userId,
                Long problemId,
                SubmissionStatus status
        );

        @Query("""
                SELECT COUNT(DISTINCT s.problem.id)
                FROM Submission s
                WHERE s.user.id = :userId
                AND s.status = com.codementor.backend.entity.SubmissionStatus.ACCEPTED
                """)
        long countDistinctSolvedProblemsByUserId(
                @Param("userId") Long userId
        );

        @Query("""
                SELECT DISTINCT s.problem.id
                FROM Submission s
                WHERE s.user.id = :userId
                AND s.status = com.codementor.backend.entity.SubmissionStatus.ACCEPTED
                """)
        List<Long> findSolvedProblemIdsByUserId(
                @Param("userId") Long userId
        );
}