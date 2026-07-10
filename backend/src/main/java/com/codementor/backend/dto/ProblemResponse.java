package com.codementor.backend.dto;

import com.codementor.backend.entity.Difficulty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProblemResponse {

    private Long id;

    private String title;

    private String description;

    private Difficulty difficulty;

    private String constraints;

    private String inputFormat;

    private String outputFormat;

    private String sampleInput;

    private String sampleOutput;

    private List<String> tags;

    private List<ProblemExampleResponse> examples;

    private Boolean active;
}