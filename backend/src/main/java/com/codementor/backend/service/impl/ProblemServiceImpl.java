package com.codementor.backend.service.impl;

import com.codementor.backend.dto.*;
import com.codementor.backend.entity.Difficulty;
import com.codementor.backend.entity.Problem;
import com.codementor.backend.entity.User;
import com.codementor.backend.exception.ResourceAlreadyExistsException;
import com.codementor.backend.exception.ResourceNotFoundException;
import com.codementor.backend.repository.ProblemRepository;
import com.codementor.backend.repository.SubmissionRepository;
import com.codementor.backend.repository.UserRepository;
import com.codementor.backend.service.ProblemService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProblemServiceImpl implements ProblemService {

    private final ProblemRepository problemRepository;

    private final SubmissionRepository submissionRepository;

    private final UserRepository userRepository;


    // ==================================================
    // FILTER PROBLEMS FOR CURRENT USER
    // ==================================================

    @Override
    @Transactional(readOnly = true)
    public Page<ProblemResponse> filterProblemsForUser(

            String userEmail,

            String title,

            Difficulty difficulty,

            ProblemStatusFilter status,

            Pageable pageable
    ) {

        User user =
                userRepository
                        .findByEmail(userEmail)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found."
                                )
                        );


        String searchTitle =
                title == null
                        ? ""
                        : title.trim();


        ProblemStatusFilter selectedStatus =
                status == null
                        ? ProblemStatusFilter.ALL
                        : status;


        Page<Problem> problems;


        if (
                selectedStatus
                        == ProblemStatusFilter.SOLVED
        ) {

            problems =
                    problemRepository
                            .findSolvedProblems(

                                    user.getId(),

                                    searchTitle,

                                    difficulty,

                                    pageable
                            );

        } else if (
                selectedStatus
                        == ProblemStatusFilter.UNSOLVED
        ) {

            problems =
                    problemRepository
                            .findUnsolvedProblems(

                                    user.getId(),

                                    searchTitle,

                                    difficulty,

                                    pageable
                            );

        } else {

            if (difficulty == null) {

                problems =
                        problemRepository
                                .findByTitleContainingIgnoreCase(

                                        searchTitle,

                                        pageable
                                );

            } else {

                problems =
                        problemRepository
                                .findByTitleContainingIgnoreCaseAndDifficulty(

                                        searchTitle,

                                        difficulty,

                                        pageable
                                );
            }
        }


        return problems.map(
                this::mapToResponse
        );
    }


    // ==================================================
    // GET CURRENT USER PROGRESS
    // ==================================================

    @Override
    @Transactional(readOnly = true)
    public ProblemProgressResponse getMyProblemProgress(
            String userEmail
    ) {

        User user =
                userRepository
                        .findByEmail(userEmail)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found."
                                )
                        );


        long totalProblems =
                problemRepository.count();


        long solvedProblems =
                submissionRepository
                        .countDistinctSolvedProblemsByUserId(
                                user.getId()
                        );


        long unsolvedProblems =
                totalProblems - solvedProblems;


        double solvedPercentage =
                totalProblems == 0
                        ? 0.0
                        : (
                                (double) solvedProblems
                                        / totalProblems
                        ) * 100;


        return ProblemProgressResponse
                .builder()

                .totalProblems(
                        totalProblems
                )

                .solvedProblems(
                        solvedProblems
                )

                .unsolvedProblems(
                        unsolvedProblems
                )

                .solvedPercentage(
                        Math.round(
                                solvedPercentage * 100.0
                        ) / 100.0
                )

                .build();
    }


    // ==================================================
    // GET CURRENT USER SOLVED PROBLEM IDS
    // ==================================================

    @Override
    @Transactional(readOnly = true)
    public List<Long> getMySolvedProblemIds(
            String userEmail
    ) {

        User user =
                userRepository
                        .findByEmail(userEmail)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found."
                                )
                        );


        return submissionRepository
                .findSolvedProblemIdsByUserId(
                        user.getId()
                );
    }


    // ==================================================
    // CREATE PROBLEM
    // ==================================================

    @Override
    @Transactional
    public ProblemResponse createProblem(
            ProblemRequest request
    ) {

        if (
                problemRepository.existsByTitle(
                        request.getTitle()
                )
        ) {

            throw new ResourceAlreadyExistsException(
                    "Problem with this title already exists."
            );
        }


        Problem problem =
                Problem
                        .builder()

                        .title(
                                request.getTitle()
                        )

                        .description(
                                request.getDescription()
                        )

                        .difficulty(
                                request.getDifficulty()
                        )

                        .constraints(
                                request.getConstraints()
                        )

                        .inputFormat(
                                request.getInputFormat()
                        )

                        .outputFormat(
                                request.getOutputFormat()
                        )

                        .sampleInput(
                                request.getSampleInput()
                        )

                        .sampleOutput(
                                request.getSampleOutput()
                        )

                        .tags(
                                request.getTags() != null

                                        ? new ArrayList<>(
                                                request.getTags()
                                        )

                                        : new ArrayList<>()
                        )

                        .active(true)

                        .build();


        problem =
                problemRepository.save(problem);


        return mapToResponse(problem);
    }


    // ==================================================
    // GET ALL PROBLEMS
    // ==================================================

    @Override
    @Transactional(readOnly = true)
    public List<ProblemResponse> getAllProblems() {

        return problemRepository
                .findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


    // ==================================================
    // GET PROBLEM BY ID
    // ==================================================

    @Override
    @Transactional(readOnly = true)
    public ProblemResponse getProblemById(
            Long id
    ) {

        Problem problem =
                findProblemById(id);


        return mapToResponse(problem);
    }


    // ==================================================
    // SEARCH PROBLEMS
    // ==================================================

    @Override
    @Transactional(readOnly = true)
    public Page<ProblemResponse> searchProblems(

            String title,

            int page,

            int size
    ) {

        Pageable pageable =
                PageRequest.of(
                        page,
                        size
                );


        return problemRepository
                .findByTitleContainingIgnoreCase(

                        title,

                        pageable
                )

                .map(
                        this::mapToResponse
                );
    }


    // ==================================================
    // FILTER PROBLEMS
    // ==================================================

    @Override
    @Transactional(readOnly = true)
    public Page<ProblemResponse> filterProblems(

            String title,

            Difficulty difficulty,

            int page,

            int size
    ) {

        Pageable pageable =
                PageRequest.of(
                        page,
                        size
                );


        if (difficulty == null) {

            return problemRepository
                    .findByTitleContainingIgnoreCase(

                            title,

                            pageable
                    )

                    .map(
                            this::mapToResponse
                    );
        }


        return problemRepository
                .findByTitleContainingIgnoreCaseAndDifficulty(

                        title,

                        difficulty,

                        pageable
                )

                .map(
                        this::mapToResponse
                );
    }


    // ==================================================
    // UPDATE PROBLEM
    // ==================================================

    @Override
    @Transactional
    public ProblemResponse updateProblem(

            Long id,

            ProblemRequest request
    ) {

        Problem problem =
                findProblemById(id);


        problem.setTitle(
                request.getTitle()
        );


        problem.setDescription(
                request.getDescription()
        );


        problem.setDifficulty(
                request.getDifficulty()
        );


        problem.setConstraints(
                request.getConstraints()
        );


        problem.setInputFormat(
                request.getInputFormat()
        );


        problem.setOutputFormat(
                request.getOutputFormat()
        );


        problem.setSampleInput(
                request.getSampleInput()
        );


        problem.setSampleOutput(
                request.getSampleOutput()
        );


        if (request.getTags() != null) {

            problem.setTags(
                    new ArrayList<>(
                            request.getTags()
                    )
            );
        }


        problem =
                problemRepository.save(problem);


        return mapToResponse(problem);
    }


    // ==================================================
    // DELETE PROBLEM
    // ==================================================

    @Override
    @Transactional
    public void deleteProblem(
            Long id
    ) {

        Problem problem =
                findProblemById(id);


        problemRepository.delete(
                problem
        );
    }


    // ==================================================
    // GET PROBLEMS BY DIFFICULTY
    // ==================================================

    @Override
    @Transactional(readOnly = true)
    public List<ProblemResponse>
    getProblemsByDifficulty(
            Difficulty difficulty
    ) {

        return problemRepository
                .findByDifficulty(
                        difficulty
                )

                .stream()

                .map(
                        this::mapToResponse
                )

                .toList();
    }


    // ==================================================
    // INTERNAL FIND PROBLEM METHOD
    // ==================================================

    private Problem findProblemById(
            Long id
    ) {

        return problemRepository
                .findById(id)

                .orElseThrow(() ->
                        new ResourceNotFoundException(

                                "Problem not found with id: "
                                        + id
                        )
                );
    }


    // ==================================================
    // CONVERT PROBLEM ENTITY TO RESPONSE DTO
    // ==================================================

    private ProblemResponse mapToResponse(
            Problem problem
    ) {

        List<ProblemExampleResponse> examples =

                problem
                        .getExamples()

                        .stream()

                        .map(example ->

                                ProblemExampleResponse
                                        .builder()

                                        .id(
                                                example.getId()
                                        )

                                        .input(
                                                example.getInput()
                                        )

                                        .output(
                                                example.getOutput()
                                        )

                                        .explanation(
                                                example.getExplanation()
                                        )

                                        .orderIndex(
                                                example.getOrderIndex()
                                        )

                                        .build()
                        )

                        .toList();


        return ProblemResponse
                .builder()

                .id(
                        problem.getId()
                )

                .title(
                        problem.getTitle()
                )

                .description(
                        problem.getDescription()
                )

                .difficulty(
                        problem.getDifficulty()
                )

                .constraints(
                        problem.getConstraints()
                )

                .inputFormat(
                        problem.getInputFormat()
                )

                .outputFormat(
                        problem.getOutputFormat()
                )

                .sampleInput(
                        problem.getSampleInput()
                )

                .sampleOutput(
                        problem.getSampleOutput()
                )

                .tags(
                        problem.getTags()
                )

                .examples(
                        examples
                )

                .active(
                        problem.getActive()
                )

                .build();
    }
}