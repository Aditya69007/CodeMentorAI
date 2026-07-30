package com.codementor.backend.dto.leetcode.calculator;

import com.codementor.backend.dto.leetcode.response.CalendarInfo;
import org.springframework.stereotype.Component;

@Component
public class ConsistencyCalculator {

    public double calculate(CalendarInfo calendar) {

        if (calendar == null
                || calendar.getCurrentStreak() == null) {

            return 0;

        }

        return Math.min(
                calendar.getCurrentStreak(),
                100
        );

    }

}