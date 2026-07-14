package com.codementor.backend.service;

import com.codementor.backend.dto.InterviewSessionResponse;
import java.util.List;


public interface PersonalizedInterviewService {

    InterviewSessionResponse startInterview(
            String userEmail
    );

    InterviewSessionResponse submitAnswer(
            Long sessionId,
            Long questionId,
            String answer,
            String userEmail
    );

    InterviewSessionResponse getInterviewDetails(
            Long sessionId,
            String userEmail
    );

    InterviewSessionResponse getActiveInterview(
            String userEmail
    );

    List<InterviewSessionResponse> getInterviewHistory(
            String userEmail
    );
}