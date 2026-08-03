package com.codementor.backend.service;

import com.codementor.backend.dto.FeaturedProjectResponse;
import com.codementor.backend.dto.FeaturedProjectsRequest;

import java.util.List;

public interface FeaturedProjectService {

    /**
     * Returns the user's featured projects.
     */
    List<FeaturedProjectResponse> getFeaturedProjects(
            String email
    );

    /**
     * Replaces the user's featured projects.
     */
    void updateFeaturedProjects(
            String email,
            FeaturedProjectsRequest request
    );

}