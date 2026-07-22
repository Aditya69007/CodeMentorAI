package com.codementor.backend.dto.leetcode.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CalendarInfo {

    private Integer streak;

    private Integer totalActiveDays;

    private String submissionCalendar;

}