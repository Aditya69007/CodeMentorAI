package com.codementor.backend.controller;

import com.codementor.backend.dto.InterviewSessionResponse;
import com.codementor.backend.dto.InterviewAnswerRequest;
import com.codementor.backend.service.PersonalizedInterviewService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;


import java.util.List;


@RestController
@RequestMapping("/api/v1/interviews")
@RequiredArgsConstructor
public class PersonalizedInterviewController {

    private final PersonalizedInterviewService
            personalizedInterviewService;


    // =====================================================
    // START PERSONALIZED INTERVIEW
    // =====================================================

    @PostMapping("/start")
    public ResponseEntity<InterviewSessionResponse>
    startInterview(
            Authentication authentication) {

        return ResponseEntity.ok(

                personalizedInterviewService
                        .startInterview(
                                authentication.getName()
                        )
        );
    }

    // =====================================================
    // SUBMIT INTERVIEW ANSWER
    // =====================================================

    @PostMapping(
            "/{sessionId}/questions/{questionId}/answer"
    )
    public ResponseEntity<InterviewSessionResponse>
    submitAnswer(
            @PathVariable Long sessionId,
            @PathVariable Long questionId,
            @RequestBody InterviewAnswerRequest request,
            Authentication authentication) {

        return ResponseEntity.ok(

                personalizedInterviewService
                        .submitAnswer(
                                sessionId,
                                questionId,
                                request.getAnswer(),
                                authentication.getName()
                        )
        );
    }

    // =====================================================
    // GET INTERVIEW DETAILS
    // =====================================================

    @GetMapping("/{sessionId}")
    public ResponseEntity<InterviewSessionResponse>
    getInterviewDetails(
            @PathVariable Long sessionId,
            Authentication authentication) {

        return ResponseEntity.ok(

                personalizedInterviewService
                        .getInterviewDetails(
                                sessionId,
                                authentication.getName()
                        )
        );
    }

// =====================================================
// GET ACTIVE INTERVIEW
// =====================================================

@GetMapping("/active/me")
public ResponseEntity<InterviewSessionResponse>
getActiveInterview(
        Authentication authentication) {

    InterviewSessionResponse response =
            personalizedInterviewService
                    .getActiveInterview(
                            authentication.getName()
                    );


    if (response == null) {

        return ResponseEntity
                .noContent()
                .build();
    }


    return ResponseEntity.ok(
            response
    );
}


    // =====================================================
    // GET INTERVIEW HISTORY
    // =====================================================

    @GetMapping("/history/me")
    public ResponseEntity<List<InterviewSessionResponse>>
    getInterviewHistory(
            Authentication authentication) {

        return ResponseEntity.ok(

                personalizedInterviewService
                        .getInterviewHistory(
                                authentication.getName()
                        )
        );
    }
}