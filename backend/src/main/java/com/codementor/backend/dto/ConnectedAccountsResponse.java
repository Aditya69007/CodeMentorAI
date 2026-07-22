package com.codementor.backend.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ConnectedAccountsResponse {

    private String githubUsername;

    private String leetcodeUsername;

    private Boolean githubConnected;

    private Boolean leetcodeConnected;

    private LocalDateTime githubLastSyncedAt;

    private LocalDateTime leetcodeLastSyncedAt;

}