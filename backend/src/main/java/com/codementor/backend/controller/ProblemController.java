package com.codementor.backend.controller;

import com.codementor.backend.dto.ProblemProgressResponse;
import com.codementor.backend.dto.ProblemRequest;
import com.codementor.backend.dto.ProblemResponse;
import com.codementor.backend.dto.ProblemStatusFilter;
import com.codementor.backend.entity.Difficulty;
import com.codementor.backend.service.ProblemService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/problems")
@RequiredArgsConstructor
public class ProblemController {

    private final ProblemService problemService;


    // ==================================================
    // CREATE PROBLEM - ADMIN ONLY
    // ==================================================

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ProblemResponse> createProblem(
            @Valid @RequestBody ProblemRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        problemService.createProblem(request)
                );
    }


    // ==================================================
    // GET ALL PROBLEMS
    // ==================================================

    @GetMapping
    public ResponseEntity<List<ProblemResponse>> getAllProblems() {

        return ResponseEntity.ok(
                problemService.getAllProblems()
        );
    }


    // ==================================================
    // GET PROBLEM BY ID
    // ==================================================

    @GetMapping("/{id}")
    public ResponseEntity<ProblemResponse> getProblemById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                problemService.getProblemById(id)
        );
    }


    // ==================================================
    // GET PROBLEMS BY DIFFICULTY
    // ==================================================

    @GetMapping("/difficulty/{difficulty}")
    public ResponseEntity<List<ProblemResponse>>
    getProblemsByDifficulty(
            @PathVariable Difficulty difficulty
    ) {

        return ResponseEntity.ok(
                problemService.getProblemsByDifficulty(difficulty)
        );
    }


    // ==================================================
    // SEARCH PROBLEMS
    // ==================================================

    @GetMapping("/search")
    public ResponseEntity<Page<ProblemResponse>> searchProblems(

            @RequestParam(defaultValue = "")
            String title,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size
    ) {

        return ResponseEntity.ok(

                problemService.searchProblems(
                        title,
                        page,
                        size
                )
        );
    }


    // ==================================================
    // FILTER ALL PROBLEMS
    // ==================================================

    @GetMapping("/filter")
    public ResponseEntity<Page<ProblemResponse>> filterProblems(

            @RequestParam(defaultValue = "")
            String title,

            @RequestParam(required = false)
            Difficulty difficulty,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size
    ) {

        return ResponseEntity.ok(

                problemService.filterProblems(
                        title,
                        difficulty,
                        page,
                        size
                )
        );
    }


    // ==================================================
    // FILTER PROBLEMS FOR LOGGED-IN USER
    //
    // Supports:
    //
    // ALL
    // SOLVED
    // UNSOLVED
    //
    // ==================================================

    @GetMapping("/filter/me")
    public ResponseEntity<Page<ProblemResponse>>
    filterMyProblems(

            @RequestParam(defaultValue = "")
            String title,

            @RequestParam(required = false)
            Difficulty difficulty,

            @RequestParam(defaultValue = "ALL")
            ProblemStatusFilter status,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            Authentication authentication
    ) {

        Pageable pageable = PageRequest.of(
                page,
                size
        );

        return ResponseEntity.ok(

                problemService.filterProblemsForUser(

                        authentication.getName(),

                        title,

                        difficulty,

                        status,

                        pageable
                )
        );
    }


    // ==================================================
    // GET LOGGED-IN USER PROBLEM PROGRESS
    // ==================================================

    @GetMapping("/progress/me")
    public ResponseEntity<ProblemProgressResponse>
    getMyProblemProgress(
            Authentication authentication
    ) {

        return ResponseEntity.ok(

                problemService.getMyProblemProgress(
                        authentication.getName()
                )
        );
    }


    // ==================================================
    // GET LOGGED-IN USER SOLVED PROBLEM IDS
    // ==================================================

    @GetMapping("/solved/me")
    public ResponseEntity<List<Long>>
    getMySolvedProblemIds(
            Authentication authentication
    ) {

        return ResponseEntity.ok(

                problemService.getMySolvedProblemIds(
                        authentication.getName()
                )
        );
    }


    // ==================================================
    // UPDATE PROBLEM - ADMIN ONLY
    // ==================================================

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ProblemResponse> updateProblem(

            @PathVariable Long id,

            @Valid
            @RequestBody ProblemRequest request
    ) {

        return ResponseEntity.ok(

                problemService.updateProblem(
                        id,
                        request
                )
        );
    }


    // ==================================================
    // DELETE PROBLEM - ADMIN ONLY
    // ==================================================

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProblem(
            @PathVariable Long id
    ) {

        problemService.deleteProblem(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}