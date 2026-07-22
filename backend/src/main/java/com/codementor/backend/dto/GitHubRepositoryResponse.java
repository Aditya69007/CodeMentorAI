package com.codementor.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GitHubRepositoryResponse {

    private String name;

    private String description;

    private String language;

    @JsonProperty("stargazers_count")
    private Integer stars;

    private Integer forks;

    @JsonProperty("html_url")
    private String repositoryUrl;

}