package com.codementor.backend.controller;

import com.codementor.backend.dto.FeaturedProjectResponse;
import com.codementor.backend.dto.FeaturedProjectsRequest;
import com.codementor.backend.service.FeaturedProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/portfolio/featured-projects")
@RequiredArgsConstructor
public class FeaturedProjectController {

    private final FeaturedProjectService featuredProjectService;

    @GetMapping
    public List<FeaturedProjectResponse> getFeaturedProjects(
            Authentication authentication
    ) {

        return featuredProjectService.getFeaturedProjects(
                authentication.getName()
        );
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateFeaturedProjects(
            Authentication authentication,
            @Valid @RequestBody FeaturedProjectsRequest request
    ) {

        featuredProjectService.updateFeaturedProjects(
                authentication.getName(),
                request
        );
    }

}