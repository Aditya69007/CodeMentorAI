package com.codementor.backend.controller;
import com.codementor.backend.service.GitHubService;
import com.codementor.backend.dto.GitHubProfileResponse;
import com.codementor.backend.dto.GitHubRepositoryResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/github")
@RequiredArgsConstructor
public class GitHubController {

    private final GitHubService gitHubService;

    @GetMapping("/profile/{username}")
    public GitHubProfileResponse profile(
            @PathVariable String username
    ) {

        return gitHubService.getProfile(username);

    }

    @GetMapping("/repos/{username}")
    public List<GitHubRepositoryResponse> repositories(
            @PathVariable String username
    ) {

        return gitHubService.getRepositories(username);

    }
    

}