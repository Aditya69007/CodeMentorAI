package com.codementor.backend.session.controller;

import com.codementor.backend.session.dto.SessionResponse;
import com.codementor.backend.session.service.UserSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sessions")
@RequiredArgsConstructor
public class UserSessionController {

    private final UserSessionService sessionService;

    @GetMapping
    public List<SessionResponse> getMySessions() {
        return sessionService.getMySessions();
    }

    @DeleteMapping("/{id}")
    public void logoutSession(@PathVariable Long id) {
        sessionService.logoutSession(id);
    }

    @DeleteMapping("/logout-all/{sessionId}")
    public void logoutAllOtherSessions(@PathVariable Long sessionId) {
        sessionService.logoutAllOtherSessions(sessionId);
    }
}