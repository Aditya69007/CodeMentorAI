package com.codementor.backend.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GitHubProfileResponse {

    @JsonAlias("login")
    private String username;

    private String name;

    @JsonAlias("avatar_url")
    private String avatarUrl;

    private String bio;

    @JsonAlias("html_url")
    private String profileUrl;

    @JsonAlias("public_repos")
    private Integer publicRepositories;

    private Integer followers;

    private Integer following;
}