package com.codementor.backend.export.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConnectedAccountsExport {

    private String githubUsername;

    private String leetcodeUsername;

    private Boolean githubConnected;

    private Boolean leetcodeConnected;

    private LocalDateTime githubLastSyncedAt;

    private LocalDateTime leetcodeLastSyncedAt;

}