package com.codementor.backend.dto.github;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GitHubTopRepositoryResponse {

    private String name;

    private String description;

    private String language;

    private int stars;

    private int forks;

    private String repositoryUrl;

}