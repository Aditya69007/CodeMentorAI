package com.codementor.backend.dto.portfolio;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AiDeveloperSummaryResponse {

    private String summary;

    private List<String> strengths;

    private List<String> growthAreas;

    private Integer recruiterMatch;

    private String recommendedRole;

}