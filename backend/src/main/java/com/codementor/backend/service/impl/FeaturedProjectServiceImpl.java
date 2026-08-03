package com.codementor.backend.service.impl;

import com.codementor.backend.dto.FeaturedProjectResponse;
import com.codementor.backend.dto.FeaturedProjectsRequest;
import com.codementor.backend.entity.FeaturedProject;
import com.codementor.backend.entity.User;
import com.codementor.backend.repository.FeaturedProjectRepository;
import com.codementor.backend.repository.UserRepository;
import com.codementor.backend.service.FeaturedProjectService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FeaturedProjectServiceImpl
        implements FeaturedProjectService {

    private final FeaturedProjectRepository featuredProjectRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<FeaturedProjectResponse> getFeaturedProjects(
            String email
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new EntityNotFoundException("User not found"));

        return featuredProjectRepository
                .findByUserOrderByDisplayOrderAsc(user)
                .stream()
                .map(project ->
                        FeaturedProjectResponse.builder()
                                .repositoryName(project.getRepositoryName())
                                .displayOrder(project.getDisplayOrder())
                                .build())
                .toList();
    }

    @Override
    public void updateFeaturedProjects(
            String email,
            FeaturedProjectsRequest request
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new EntityNotFoundException("User not found"));

        // Replace previous selection
        featuredProjectRepository.deleteByUser(user);

        int order = 1;

        for (String repositoryName : request.getRepositoryNames()) {

            FeaturedProject project = FeaturedProject.builder()
                    .user(user)
                    .repositoryName(repositoryName.trim())
                    .displayOrder(order++)
                    .build();

            featuredProjectRepository.save(project);
        }
    }
}