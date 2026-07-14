package com.codementor.backend.service;

import com.codementor.backend.dto.AdminSubmissionResponse;
import com.codementor.backend.dto.SubmissionRequest;
import com.codementor.backend.dto.SubmissionResponse;

import com.codementor.backend.entity.Language;
import com.codementor.backend.entity.SubmissionStatus;
import com.codementor.backend.dto.AdminSubmissionDetailsResponse;

import org.springframework.data.domain.Page;

public interface SubmissionService {

    // ==================================================
    // CREATE SUBMISSION
    // ==================================================

    SubmissionResponse createSubmission(
            SubmissionRequest request,
            String userEmail
    );


    // ==================================================
    // GET SUBMISSION BY ID
    // ==================================================

    SubmissionResponse getSubmissionById(
            Long id,
            String userEmail
    );


    // ==================================================
    // GET LOGGED-IN USER SUBMISSIONS
    // ==================================================

    Page<SubmissionResponse> getMySubmissions(
            String userEmail,
            int page,
            int size
    );


    // ==================================================
    // GET LOGGED-IN USER SUBMISSIONS FOR ONE PROBLEM
    // ==================================================

    Page<SubmissionResponse> getMyProblemSubmissions(
            Long problemId,
            String userEmail,
            int page,
            int size
    );


    // ==================================================
    // FILTER SUBMISSIONS - ADMIN
    // ==================================================

    Page<AdminSubmissionResponse> filterSubmissionsForAdmin(

            String search,

            SubmissionStatus status,

            Language language,

            int page,

            int size
    );
    AdminSubmissionDetailsResponse getSubmissionDetailsForAdmin(
                Long id
        );
}