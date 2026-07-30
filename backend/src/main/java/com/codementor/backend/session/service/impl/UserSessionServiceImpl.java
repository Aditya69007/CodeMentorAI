package com.codementor.backend.session.service.impl;

import com.codementor.backend.entity.User;
import com.codementor.backend.repository.UserRepository;
import com.codementor.backend.session.dto.SessionResponse;
import com.codementor.backend.session.entity.UserSession;
import com.codementor.backend.session.mapper.UserSessionMapper;
import com.codementor.backend.session.repository.UserSessionRepository;
import com.codementor.backend.session.service.UserSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserSessionServiceImpl implements UserSessionService {

    private final UserRepository userRepository;
    private final UserSessionRepository sessionRepository;
    private final UserSessionMapper mapper;

    private User getCurrentUser() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public List<SessionResponse> getMySessions() {

        return sessionRepository.findByUserAndIsActiveTrue(getCurrentUser())
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public void logoutSession(Long sessionId) {

        UserSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        session.setIsActive(false);

        sessionRepository.save(session);
    }

    @Override
    public void logoutAllOtherSessions(Long currentSessionId) {

        User currentUser = getCurrentUser();

        List<UserSession> sessions =
                sessionRepository.findByUserAndIsActiveTrueAndIdNot(currentUser, currentSessionId);

        for (UserSession session : sessions) {
            session.setIsActive(false);
        }

        sessionRepository.saveAll(sessions);
    }
    
}