package com.codementor.backend.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AiMistakeDetectionResponse {

    private List<AiMistakeItemResponse> mistakes;
}