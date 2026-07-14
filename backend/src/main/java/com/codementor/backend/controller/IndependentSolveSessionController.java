package com.codementor.backend.controller;

import com.codementor.backend.dto.IndependentSolveSessionResponse;
import com.codementor.backend.exception.ResourceNotFoundException;
import com.codementor.backend.service.IndependentSolveSessionService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/independent-solve")
@RequiredArgsConstructor
public class IndependentSolveSessionController {

    private final IndependentSolveSessionService
            independentSolveSessionService;


    // ==================================================
    // START SESSION
    // ==================================================

    @PostMapping("/start/{problemId}")
    public ResponseEntity<IndependentSolveSessionResponse>
    startSession(
            @PathVariable Long problemId,
            Authentication authentication) {

        return ResponseEntity.ok(

                independentSolveSessionService
                        .startSession(
                                problemId,
                                authentication.getName()
                        )
        );
    }


    // ==================================================
    // GET ACTIVE SESSION
    // ==================================================

        @GetMapping("/active/{problemId}")
        public ResponseEntity<IndependentSolveSessionResponse>
        getActiveSession(
                @PathVariable Long problemId,
                Authentication authentication) {

        try {

                IndependentSolveSessionResponse response =
                        independentSolveSessionService
                                .getActiveSession(
                                        problemId,
                                        authentication.getName()
                                );

                return ResponseEntity.ok(response);

        } catch (ResourceNotFoundException exception) {

                return ResponseEntity.noContent().build();
        }
        }


    // ==================================================
    // FINISH SESSION
    // ==================================================

    @PostMapping("/finish/{problemId}")
    public ResponseEntity<IndependentSolveSessionResponse>
    finishSession(
            @PathVariable Long problemId,
            Authentication authentication) {

        return ResponseEntity.ok(

                independentSolveSessionService
                        .finishSession(
                                problemId,
                                authentication.getName()
                        )
        );
    }

        // ==================================================
        // GET SESSION HISTORY
        // ==================================================

        @GetMapping("/history/{problemId}")
        public ResponseEntity<List<IndependentSolveSessionResponse>>
        getSessionHistory(
                @PathVariable Long problemId,
                Authentication authentication) {

        return ResponseEntity.ok(

                independentSolveSessionService
                        .getSessionHistory(
                                problemId,
                                authentication.getName()
                        )
        );
        }
}