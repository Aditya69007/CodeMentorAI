package com.codementor.backend.dto.leetcode.calculator;

import com.codementor.backend.dto.leetcode.response.ContestInfo;
import org.springframework.stereotype.Component;

@Component
public class ContestCalculator {

    public double calculate(ContestInfo contest) {

        if (contest == null
                || contest.getRating() == null) {

            return 0;
        }

        double rating = contest.getRating();

        return Math.min(100.0, rating / 25.0);
    }
}