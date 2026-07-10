package com.codementor.backend.dto;

import com.codementor.backend.entity.MistakeType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PastMistakeRecallResponse {

    private boolean repeatedMistake;

    private MistakeType mistakeType;

    private String concept;

    private Long occurrenceCount;

    private Long previousSubmissionId;

    private String previousProblemTitle;

    private String message;

    private String memoryAdvice;
}