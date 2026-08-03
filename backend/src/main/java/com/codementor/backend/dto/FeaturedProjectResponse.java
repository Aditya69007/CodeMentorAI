package com.codementor.backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeaturedProjectResponse {

    /**
     * Repository name selected by the user.
     */
    private String repositoryName;

    /**
     * Display order in the portfolio.
     */
    private Integer displayOrder;

}