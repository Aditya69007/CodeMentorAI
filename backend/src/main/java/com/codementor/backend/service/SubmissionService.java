package com.codementor.backend.service;

import com.codementor.backend.dto.SubmissionRequest;
import com.codementor.backend.dto.SubmissionResponse;
import org.springframework.data.domain.Page;

public interface SubmissionService {

    SubmissionResponse createSubmission(
            SubmissionRequest request,
            String userEmail
    );

    SubmissionResponse getSubmissionById(
            Long id,
            String userEmail
    );

    Page<SubmissionResponse> getMySubmissions(
            String userEmail,
            int page,
            int size
    );

    Page<SubmissionResponse> getMyProblemSubmissions(
            Long problemId,
            String userEmail,
            int page,
            int size
    );
}