package com.codementor.backend.dto;

import com.codementor.backend.entity.MistakeType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecurringMistakeResponse {

    private MistakeType mistakeType;

    private Long occurrenceCount;

    private Long affectedProblems;

    private String message;
}