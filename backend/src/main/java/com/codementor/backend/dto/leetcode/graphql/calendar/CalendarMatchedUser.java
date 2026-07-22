package com.codementor.backend.dto.leetcode.graphql.calendar;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CalendarMatchedUser {

    @JsonProperty("userCalendar")
    private UserCalendar userCalendar;

}