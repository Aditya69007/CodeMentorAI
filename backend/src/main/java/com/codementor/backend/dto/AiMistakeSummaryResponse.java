package com.codementor.backend.dto;

import com.codementor.backend.entity.MistakeType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AiMistakeSummaryResponse {

    private MistakeType mistakeType;

    private Long count;
}