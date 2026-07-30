package com.codementor.backend.export.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GithubExport {

    private String username;

    private String name;

    private String avatarUrl;

    private String bio;

    private String profileUrl;

    private Integer publicRepositories;

    private Integer publicGists;

    private String createdAt;

    private String company;

    private String location;

    private String blog;

    private Integer followers;

    private Integer following;

}