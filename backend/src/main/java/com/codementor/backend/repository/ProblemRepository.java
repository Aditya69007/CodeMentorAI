package com.codementor.backend.repository;

import com.codementor.backend.entity.Difficulty;
import com.codementor.backend.entity.Problem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.codementor.backend.entity.Difficulty;

import java.util.Optional;
import java.util.List;

public interface ProblemRepository
        extends JpaRepository<Problem, Long> {

    boolean existsByTitle(String title);

    long countByTopicId(Long topicId);

    long countByTopicIdAndActiveTrue(Long topicId);

    Optional<Problem> findByTitleIgnoreCase(String title);

    List<Problem> findByDifficulty(
            Difficulty difficulty
    );

    Page<Problem> findByTitleContainingIgnoreCase(
            String title,
            Pageable pageable
    );

    Page<Problem> findByTitleContainingIgnoreCaseAndDifficulty(
            String title,
            Difficulty difficulty,
            Pageable pageable
    );


    /*
     * TOPIC LIBRARY
     */

    List<Problem> findByTopicIdAndActiveTrueOrderByDifficultyAscTitleAsc(
            Long topicId
    );


    List<Problem> findByTopicSlugAndActiveTrueOrderByDifficultyAscTitleAsc(
            String slug
    );

        @Query("""
                SELECT p
                FROM Problem p
                WHERE p.active = true

                AND (
                LOWER(p.title)
                LIKE LOWER(CONCAT('%', :title, '%'))
                )

                AND (
                :difficulty IS NULL
                OR p.difficulty = :difficulty
                )

                AND EXISTS (
                SELECT s.id
                FROM Submission s
                WHERE s.problem.id = p.id
                AND s.user.id = :userId
                AND s.status =
                        com.codementor.backend.entity.SubmissionStatus.ACCEPTED
                )
                """)
        Page<Problem> findSolvedProblems(

                @Param("userId")
                Long userId,

                @Param("title")
                String title,

                @Param("difficulty")
                Difficulty difficulty,

                Pageable pageable
        );


        @Query("""
                SELECT p
                FROM Problem p
                WHERE p.active = true

                AND (
                LOWER(p.title)
                LIKE LOWER(CONCAT('%', :title, '%'))
                )

                AND (
                :difficulty IS NULL
                OR p.difficulty = :difficulty
                )

                AND NOT EXISTS (
                SELECT s.id
                FROM Submission s
                WHERE s.problem.id = p.id
                AND s.user.id = :userId
                AND s.status =
                        com.codementor.backend.entity.SubmissionStatus.ACCEPTED
                )
                """)
        Page<Problem> findUnsolvedProblems(

                @Param("userId")
                Long userId,

                @Param("title")
                String title,

                @Param("difficulty")
                Difficulty difficulty,

            Pageable pageable
        );

        List<Problem> findByTopicNameIgnoreCaseAndActiveTrue(
                String topicName
        );
        
        long countByDifficulty(Difficulty difficulty);

        @Query("""
                SELECT p
                FROM Problem p
                WHERE
                (
                :title = ''
                OR LOWER(p.title)
                LIKE LOWER(CONCAT('%', :title, '%'))
                )

                AND
                (
                :difficulty IS NULL
                OR p.difficulty = :difficulty
                )

                AND
                (
                :topicId IS NULL
                OR p.topic.id = :topicId
                )

                AND
                (
                :active IS NULL
                OR p.active = :active
                )
                """)
        Page<Problem> findAdminProblems(

                @Param("title")
                String title,

                @Param("difficulty")
                Difficulty difficulty,

                @Param("topicId")
                Long topicId,

                @Param("active")
                Boolean active,

                Pageable pageable
        );

}