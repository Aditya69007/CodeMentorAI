package com.codementor.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateConnectedAccountsRequest {

    private String githubUsername;

    private String leetcodeUsername;

}