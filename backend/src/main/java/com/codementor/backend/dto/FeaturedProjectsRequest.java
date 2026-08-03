package com.codementor.backend.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeaturedProjectsRequest {

    /**
     * Selected GitHub repositories.
     * Maximum 3 featured projects.
     */
    @NotEmpty(message = "At least one repository must be selected.")
    @Size(max = 3, message = "You can feature a maximum of 3 repositories.")
    private List<String> repositoryNames;

}