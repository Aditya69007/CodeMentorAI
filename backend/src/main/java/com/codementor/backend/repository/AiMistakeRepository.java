package com.codementor.backend.repository;

import com.codementor.backend.entity.AiMistake;
import com.codementor.backend.entity.MistakeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AiMistakeRepository
        extends JpaRepository<AiMistake, Long> {

        void deleteByUserId(Long userId);

        @Query("""
                SELECT
                m.mistakeType,
                COUNT(m),
                COUNT(DISTINCT m.problem.id)
                FROM AiMistake m
                WHERE m.user.id = :userId
                GROUP BY m.mistakeType
                HAVING COUNT(m) >= 2
                ORDER BY COUNT(m) DESC
                """)
        List<Object[]> findRecurringMistakesByUserId(
                @Param("userId") Long userId
        );

        @Query("""
                SELECT m
                FROM AiMistake m
                WHERE m.user.id = :userId
                AND m.mistakeType = :mistakeType
                AND m.submission.id <> :currentSubmissionId
                ORDER BY m.createdAt DESC
                """)
        List<AiMistake> findPreviousSimilarMistakes(
                @Param("userId") Long userId,
                @Param("mistakeType") MistakeType mistakeType,
                @Param("currentSubmissionId") Long currentSubmissionId
        );

    // CHECK IF MISTAKES ALREADY EXIST FOR SUBMISSION

    boolean existsBySubmissionId(
            Long submissionId
    );


    // GET ALL MISTAKES FOR ONE SUBMISSION

    List<AiMistake> findBySubmissionId(
            Long submissionId
    );


    // GET USER-WIDE MISTAKE SUMMARY

    @Query("""
            SELECT m.mistakeType, COUNT(m)
            FROM AiMistake m
            WHERE m.user.id = :userId
            GROUP BY m.mistakeType
            ORDER BY COUNT(m) DESC
            """)
    List<Object[]> findMistakeSummaryByUserId(
            @Param("userId") Long userId
    );

    List<AiMistake> findByUserIdOrderByCreatedAtDesc(
        Long userId
    );

    List<AiMistake> findBySubmissionIdOrderByCreatedAtAsc(
        Long submissionId
    );

    long countByUserId(Long userId);

        @Query("""
                SELECT
                m.problem.topic.id,
                COUNT(m)
                FROM AiMistake m
                WHERE m.user.id = :userId
                AND m.problem.topic IS NOT NULL
                GROUP BY m.problem.topic.id
                """)
        List<Object[]> findMistakeCountByTopicForUser(
                @Param("userId") Long userId
        );

        // =========================================================
        // ADMIN AI ANALYTICS
        // =========================================================

        @Query("""
                SELECT m.mistakeType, COUNT(m)
                FROM AiMistake m
                GROUP BY m.mistakeType
                ORDER BY COUNT(m) DESC
                """)
        List<Object[]> findGlobalMistakeTypeDistribution();


        @Query("""
                SELECT m.severity, COUNT(m)
                FROM AiMistake m
                GROUP BY m.severity
                ORDER BY COUNT(m) DESC
                """)
        List<Object[]> findGlobalSeverityDistribution();


        @Query("""
                SELECT m.concept, COUNT(m)
                FROM AiMistake m
                GROUP BY m.concept
                ORDER BY COUNT(m) DESC
                """)
        List<Object[]> findGlobalConceptDistribution();


        @Query("""
                SELECT COUNT(DISTINCT m.user.id)
                FROM AiMistake m
                """)
        long countDistinctUsersWithMistakes();

        @Query("""
                SELECT
                m.problem.topic.id,
                COUNT(m)
                FROM AiMistake m
                WHERE m.user.id = :userId
                AND m.problem.topic IS NOT NULL
                GROUP BY m.problem.topic.id
                """)
        List<Object[]> findDeveloperSkillMistakeStatsByUserId(
                @Param("userId") Long userId
        );

        @Query("""
        SELECT COUNT(m)
        FROM AiMistake m
        WHERE m.user.id = :userId
        AND m.problem.topic.id = :topicId
        """)
        long countByUserAndTopic(
                @Param("userId") Long userId,
                @Param("topicId") Long topicId
        );

        @Query("""
                SELECT
                m.concept,
                COUNT(m)
                FROM AiMistake m
                WHERE m.user.id = :userId
                AND m.problem.topic.id = :topicId
                AND m.concept IS NOT NULL
                GROUP BY m.concept
                ORDER BY COUNT(m) DESC
                """)
        List<Object[]> findConceptMistakeStatsByTopic(

                @Param("userId")
                Long userId,

                @Param("topicId")
                Long topicId

        );
}