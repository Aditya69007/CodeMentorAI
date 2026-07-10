package com.codementor.backend.service;

import com.codementor.backend.dto.ProblemProgressResponse;
import com.codementor.backend.dto.ProblemRequest;
import com.codementor.backend.dto.ProblemResponse;
import com.codementor.backend.dto.ProblemStatusFilter;
import com.codementor.backend.entity.Difficulty;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProblemService {

    ProblemResponse createProblem(ProblemRequest request);

    List<ProblemResponse> getAllProblems();

    ProblemResponse getProblemById(Long id);

    Page<ProblemResponse> searchProblems(
            String title,
            int page,
            int size
    );

    Page<ProblemResponse> filterProblems(
            String title,
            Difficulty difficulty,
            int page,
            int size
    );

    void deleteProblem(Long id);

    ProblemResponse updateProblem(
            Long id,
            ProblemRequest request
    );

    List<ProblemResponse> getProblemsByDifficulty(
            Difficulty difficulty
    );

        ProblemProgressResponse getMyProblemProgress(
                String userEmail
        );

        List<Long> getMySolvedProblemIds(
                String userEmail
        );

        Page<ProblemResponse> filterProblemsForUser(

                String userEmail,

                String title,

                Difficulty difficulty,

                ProblemStatusFilter status,

                Pageable pageable
        );
}