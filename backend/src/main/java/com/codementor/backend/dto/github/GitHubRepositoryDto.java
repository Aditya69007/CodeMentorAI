package com.codementor.backend.dto.github;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GitHubRepositoryDto {

    private String name;

    private String description;

    private String language;

    private Integer stars;

    private Integer forks;

    private String repositoryUrl;

    private Integer watchers;

    private Integer openIssues;

    private String updatedAt;

    private Boolean isPrivate;

    private List<String> topics;

}