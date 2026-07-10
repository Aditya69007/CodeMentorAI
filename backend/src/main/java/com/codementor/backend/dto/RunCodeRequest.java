package com.codementor.backend.dto;

import com.codementor.backend.entity.Language;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RunCodeRequest {

    @NotNull(message = "Problem id is required")
    private Long problemId;

    @NotNull(message = "Example id is required")
    private Long exampleId;

    @NotBlank(message = "Source code is required")
    private String sourceCode;

    @NotNull(message = "Language is required")
    private Language language;
}