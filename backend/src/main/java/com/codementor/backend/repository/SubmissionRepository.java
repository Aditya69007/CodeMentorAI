package com.codementor.backend.repository;

import com.codementor.backend.entity.Submission;
import com.codementor.backend.entity.SubmissionStatus;
import com.codementor.backend.entity.Language;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface SubmissionRepository
        extends JpaRepository<Submission, Long> {

        void deleteByUserId(Long userId);

        Page<Submission> findAllByOrderByCreatedAtDesc(
                Pageable pageable
        );


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

        long countByStatus(SubmissionStatus status);

        List<Submission> findByCreatedAtGreaterThanEqualOrderByCreatedAtAsc(
                LocalDateTime startDate
        );

        long countByCreatedAtGreaterThanEqual(
                LocalDateTime startDate
        );

        long countByUserId(Long userId);


        long countByUserIdAndStatus(
                Long userId,
                SubmissionStatus status
        );

        List<Submission> findTop10ByUserIdOrderByCreatedAtDesc(
                Long userId
        );


        List<Submission> findByUserIdAndCreatedAtGreaterThanEqualOrderByCreatedAtAsc(
                Long userId,
                LocalDateTime startDate
        );


        @Query("""
                SELECT s.status, COUNT(s)
                FROM Submission s
                WHERE s.user.id = :userId
                GROUP BY s.status
                """)
        List<Object[]> findStatusDistributionByUserId(
                @Param("userId") Long userId
        );


        @Query("""
                SELECT
                s.problem.topic.id,
                s.problem.topic.name,
                COUNT(s),
                SUM(
                        CASE
                        WHEN s.status =
                        com.codementor.backend.entity.SubmissionStatus.ACCEPTED
                        THEN 1
                        ELSE 0
                        END
                )
                FROM Submission s
                WHERE s.user.id = :userId
                AND s.problem.topic IS NOT NULL
                GROUP BY
                s.problem.topic.id,
                s.problem.topic.name
                ORDER BY COUNT(s) DESC
                """)
        List<Object[]> findTopicPerformanceByUserId(
                @Param("userId") Long userId
        );

        @Query("""
                SELECT s
                FROM Submission s
                WHERE
                (
                :search = ''
                OR LOWER(s.user.firstName)
                        LIKE LOWER(CONCAT('%', :search, '%'))
                OR LOWER(s.user.lastName)
                        LIKE LOWER(CONCAT('%', :search, '%'))
                OR LOWER(s.user.email)
                        LIKE LOWER(CONCAT('%', :search, '%'))
                OR LOWER(s.problem.title)
                        LIKE LOWER(CONCAT('%', :search, '%'))
                )
                AND
                (
                :status IS NULL
                OR s.status = :status
                )
                AND
                (
                :language IS NULL
                OR s.language = :language
                )
                """)
        Page<Submission> filterAdminSubmissions(

                @Param("search")
                String search,

                @Param("status")
                SubmissionStatus status,

                @Param("language")
                Language language,

                Pageable pageable
        );

        @Query("""
                SELECT s.language, COUNT(s)
                FROM Submission s
                GROUP BY s.language
                ORDER BY COUNT(s) DESC
                """)
        List<Object[]> findLanguageDistribution();

        @Query("""
                SELECT
                s.problem.topic.id,
                s.problem.topic.name,
                COUNT(s),
                SUM(
                        CASE
                        WHEN s.status =
                        com.codementor.backend.entity.SubmissionStatus.ACCEPTED
                        THEN 1
                        ELSE 0
                        END
                )
                FROM Submission s
                WHERE s.user.id = :userId
                AND s.problem.topic IS NOT NULL
                GROUP BY
                s.problem.topic.id,
                s.problem.topic.name
                ORDER BY s.problem.topic.name ASC
                """)
        List<Object[]> findDeveloperSkillStatsByUserId(
                @Param("userId") Long userId
        );

        List<Submission> findByUserIdAndProblemIdOrderByCreatedAtAsc(
                Long userId,
                Long problemId
        );

        // ==================================================
        // COUNT UNIQUE PROBLEMS ATTEMPTED BY USER
        // ==================================================

        @Query("""
                SELECT COUNT(DISTINCT s.problem.id)
                FROM Submission s
                WHERE s.user.id = :userId
                """)
        long countDistinctProblemsAttemptedByUserId(
                @Param("userId") Long userId
        );

        List<Submission> findByUserIdOrderByCreatedAtDesc(
                Long userId
        );

        List<Submission>
        findByUserIdAndCreatedAtBetweenOrderByCreatedAtAsc(
                Long userId,
                LocalDateTime startDate,
                LocalDateTime endDate
        );
}