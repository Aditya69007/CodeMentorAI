package com.codementor.backend.service;

import com.codementor.backend.dto.IndependentSolveSessionResponse;
import com.codementor.backend.entity.Submission;

import java.util.List;

public interface IndependentSolveSessionService {

    IndependentSolveSessionResponse startSession(
            Long problemId,
            String userEmail
    );

    IndependentSolveSessionResponse getActiveSession(
            Long problemId,
            String userEmail
    );

    IndependentSolveSessionResponse finishSession(
            Long problemId,
            String userEmail
    );

    boolean hasActiveSession(
            Long problemId,
            String userEmail
    );

    List<IndependentSolveSessionResponse> getSessionHistory(
            Long problemId,
            String userEmail
    );
    
        void recordSubmission(
                Submission submission,
                String userEmail
        );
}