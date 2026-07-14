package com.codementor.backend.repository;

import com.codementor.backend.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TopicRepository
        extends JpaRepository<Topic, Long> {

    Optional<Topic> findBySlug(String slug);

    Optional<Topic> findByNameIgnoreCase(String name);

    boolean existsBySlug(String slug);

    boolean existsByNameIgnoreCase(String name);

    List<Topic> findByActiveTrueOrderByNameAsc();

    List<Topic> findAllByOrderByCreatedAtDesc();
}