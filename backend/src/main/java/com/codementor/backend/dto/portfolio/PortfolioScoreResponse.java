package com.codementor.backend.dto.portfolio;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioScoreResponse {

    private Integer overallScore;

    private Integer githubScore;

    private Integer leetcodeScore;

    private Integer resumeReadiness;

    private Integer openSourceScore;

    private Integer productionReadiness;

}