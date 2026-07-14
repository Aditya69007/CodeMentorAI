package com.codementor.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserTopicPerformanceResponse {

    private Long topicId;

    private String topicName;

    private long totalSubmissions;

    private long acceptedSubmissions;

    private long mistakes;

    private double acceptanceRate;
}