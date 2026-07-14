package com.codementor.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProblemExampleRequest {

    @NotBlank(message = "Example input is required")
    private String input;

    @NotBlank(message = "Example output is required")
    private String output;

    private String explanation;

    @NotNull(message = "Example order index is required")
    private Integer orderIndex;
}