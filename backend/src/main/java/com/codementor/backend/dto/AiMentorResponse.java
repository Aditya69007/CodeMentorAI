package com.codementor.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AiMentorResponse {

    private Long submissionId;

    private String explanation;

    private String hint;

    private String conceptToStudy;
}