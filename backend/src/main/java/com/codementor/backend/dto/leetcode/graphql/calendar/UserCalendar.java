package com.codementor.backend.dto.leetcode.graphql.calendar;

import lombok.Data;

@Data
public class UserCalendar {

    private Integer streak;

    private Integer totalActiveDays;

    private String submissionCalendar;

}