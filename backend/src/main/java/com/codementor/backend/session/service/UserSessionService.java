package com.codementor.backend.session.service;

import com.codementor.backend.session.dto.SessionResponse;

import java.util.List;

public interface UserSessionService {

    List<SessionResponse> getMySessions();

    void logoutSession(Long sessionId);

    void logoutAllOtherSessions(Long currentSessionId);

}