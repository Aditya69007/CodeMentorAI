package com.codementor.backend.controller;

import com.codementor.backend.dto.SubmissionRequest;
import com.codementor.backend.dto.SubmissionResponse;
import com.codementor.backend.service.SubmissionService;
import com.codementor.backend.dto.AdminSubmissionDetailsResponse;
import com.codementor.backend.dto.AdminSubmissionResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import com.codementor.backend.entity.Language;
import com.codementor.backend.entity.SubmissionStatus;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/submissions")
@RequiredArgsConstructor
public class SubmissionController {

    private final SubmissionService submissionService;


    // ==================================================
    // CREATE SUBMISSION
    // ==================================================

    @PostMapping
    public ResponseEntity<SubmissionResponse> createSubmission(

            @Valid
            @RequestBody
            SubmissionRequest request,

            Authentication authentication) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        submissionService.createSubmission(
                                request,
                                authentication.getName()
                        )
                );
    }


    // ==================================================
    // GET ALL LOGGED-IN USER SUBMISSIONS
    // ==================================================

    @GetMapping("/me")
    public ResponseEntity<Page<SubmissionResponse>>
    getMySubmissions(

            Authentication authentication,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size) {

        return ResponseEntity.ok(

                submissionService.getMySubmissions(
                        authentication.getName(),
                        page,
                        size
                )

        );
    }


    // ==================================================
    // GET SUBMISSIONS FOR ONE PROBLEM
    // ==================================================

    @GetMapping("/problem/{problemId}")
    public ResponseEntity<Page<SubmissionResponse>>
    getMyProblemSubmissions(

            @PathVariable
            Long problemId,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            Authentication authentication) {

        return ResponseEntity.ok(

                submissionService
                        .getMyProblemSubmissions(
                                problemId,
                                authentication.getName(),
                                page,
                                size
                        )

        );
    }

        // ==================================================
        // GET ALL SUBMISSIONS - ADMIN ONLY
        // ==================================================
        // ==================================================
        // FILTER SUBMISSIONS - ADMIN ONLY
        // ==================================================

        @PreAuthorize("hasRole('ADMIN')")
        @GetMapping("/admin")
        public ResponseEntity<Page<AdminSubmissionResponse>>
        filterSubmissionsForAdmin(

                @RequestParam(defaultValue = "")
                String search,

                @RequestParam(required = false)
                SubmissionStatus status,

                @RequestParam(required = false)
                Language language,

                @RequestParam(defaultValue = "0")
                int page,

                @RequestParam(defaultValue = "10")
                int size
        ) {

        return ResponseEntity.ok(

                submissionService
                        .filterSubmissionsForAdmin(

                                search,

                                status,

                                language,

                                page,

                                size
                        )
        );
        }


    // ==================================================
    // GET SUBMISSION BY ID
    // ==================================================

    @GetMapping("/{id}")
    public ResponseEntity<SubmissionResponse>
    getSubmissionById(

            @PathVariable
            Long id,

            Authentication authentication) {

        return ResponseEntity.ok(

                submissionService.getSubmissionById(
                        id,
                        authentication.getName()
                )

        );
    }

        @GetMapping("/admin/{id}")
        public ResponseEntity<AdminSubmissionDetailsResponse>
        getSubmissionDetailsForAdmin(
                @PathVariable Long id
        ) {

        return ResponseEntity.ok(
                submissionService
                        .getSubmissionDetailsForAdmin(id)
        );
        }
}