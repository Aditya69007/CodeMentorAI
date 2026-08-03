package com.codementor.backend.repository;

import com.codementor.backend.entity.FeaturedProject;
import com.codementor.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeaturedProjectRepository
        extends JpaRepository<FeaturedProject, Long> {

    /**
     * Returns featured projects ordered
     * by display order.
     */
    List<FeaturedProject> findByUserOrderByDisplayOrderAsc(
            User user
    );

    /**
     * Delete all featured projects
     * before saving new selection.
     */
    void deleteByUser(
            User user
    );

    /**
     * Prevent duplicate repositories
     * for the same user.
     */
    boolean existsByUserAndRepositoryName(
            User user,
            String repositoryName
    );

}