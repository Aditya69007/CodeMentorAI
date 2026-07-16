package com.codementor.backend.dto;

import com.codementor.backend.entity.Difficulty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopicProblemResponse {

    private Long id;

    private String title;

    private Difficulty difficulty;

    private List<String> tags;

    private boolean solved;

    private boolean attempted;

    private long attempts;

    private String latestStatus;

    private double acceptanceRate;

}