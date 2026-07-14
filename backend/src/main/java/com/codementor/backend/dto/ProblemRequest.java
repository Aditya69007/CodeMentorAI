package com.codementor.backend.dto;

import com.codementor.backend.entity.Difficulty;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProblemRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Difficulty is required")
    private Difficulty difficulty;

    private String constraints;

    private String inputFormat;

    private String outputFormat;

    private String sampleInput;

    private String sampleOutput;

    private List<String> tags;

    @Valid
    private List<ProblemExampleRequest> examples;
}