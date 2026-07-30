package com.codementor.backend.session.repository;

import com.codementor.backend.entity.User;
import com.codementor.backend.session.entity.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserSessionRepository extends JpaRepository<UserSession, Long> {

    List<UserSession> findByUserAndIsActiveTrue(User user);

    List<UserSession> findByUser(User user);

    List<UserSession> findByUserAndIsActiveTrueAndIdNot(User user, Long id);

    void deleteByUser(User user);
    
}