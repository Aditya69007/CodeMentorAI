package com.codementor.backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConceptGrowthResponse {

    private String concept;

    private Long totalMistakes;

    private Long acceptedSubmissions;

    private String growthStatus;

    private String message;
}