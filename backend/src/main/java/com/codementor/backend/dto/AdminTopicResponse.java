package com.codementor.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminTopicResponse {

    private Long id;

    private String name;

    private String slug;

    private String description;

    private Boolean active;

    private Long problemCount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}