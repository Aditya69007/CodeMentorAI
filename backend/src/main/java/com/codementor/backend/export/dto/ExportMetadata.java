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
public class ExportMetadata {

    private String applicationName;

    private String version;

    private LocalDateTime exportedAt;

    private String generatedBy;

}