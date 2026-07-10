package com.codementor.backend.dto;

import com.codementor.backend.entity.MistakeSeverity;
import com.codementor.backend.entity.MistakeType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AiMistakeItemResponse {

    private MistakeType mistakeType;

    private String concept;

    private String description;

    private MistakeSeverity severity;
}