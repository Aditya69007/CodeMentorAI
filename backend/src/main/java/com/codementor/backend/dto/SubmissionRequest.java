package com.codementor.backend.dto;

import com.codementor.backend.entity.Language;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubmissionRequest {

    @NotNull(message = "Problem ID is required")
    private Long problemId;

    @NotNull(message = "Language is required")
    private Language language;

    @NotBlank(message = "Source code is required")
    private String sourceCode;
}