package com.codementor.backend.dto.leetcode.calculator;

import com.codementor.backend.dto.leetcode.response.ProblemStats;
import org.springframework.stereotype.Component;

@Component
public class AcceptanceCalculator {

    public double calculate(ProblemStats stats) {

        if (stats == null
                || stats.getTotalSubmissions() == null
                || stats.getTotalSubmissions() == 0) {

            return 0.0;
        }

        return ((double) stats.getTotalSolved()
                / stats.getTotalSubmissions()) * 100.0;
    }
}