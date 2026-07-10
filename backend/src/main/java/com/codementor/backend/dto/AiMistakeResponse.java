package com.codementor.backend.dto;

import com.codementor.backend.entity.MistakeSeverity;
import com.codementor.backend.entity.MistakeType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AiMistakeResponse {

    private Long id;
    private Long submissionId;
    private Long problemId;
    private String problemTitle;
    private MistakeType mistakeType;
    private String concept;
    private String description;
    private MistakeSeverity severity;
    private LocalDateTime createdAt;
}