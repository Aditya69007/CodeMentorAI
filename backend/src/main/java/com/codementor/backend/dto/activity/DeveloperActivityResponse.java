package com.codementor.backend.dto.activity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeveloperActivityResponse {

    private int currentStreak;

    private int longestStreak;

    private long totalActiveDays;

    private long totalSubmissions;

    private List<DailyActivityResponse> activity;
}