package com.codementor.backend.dto.activity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyActivityResponse {

    private LocalDate date;

    private long submissionCount;

    private long acceptedSubmissionCount;
}