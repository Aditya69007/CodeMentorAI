package com.codementor.backend.notification.repository;

import com.codementor.backend.entity.User;
import com.codementor.backend.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByUserOrderByCreatedAtDesc(User user);

    List<Notification> findByUserAndIsReadFalseOrderByCreatedAtDesc(User user);

    long countByUserAndIsReadFalse(User user);

    long countByUser(User user);

    @Modifying
    @Transactional
    void deleteByUser(User user);


}