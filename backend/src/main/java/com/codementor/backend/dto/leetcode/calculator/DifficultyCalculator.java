package com.codementor.backend.dto.leetcode.calculator;

import com.codementor.backend.dto.leetcode.response.ProblemStats;
import org.springframework.stereotype.Component;

@Component
public class DifficultyCalculator {

    public double calculate(ProblemStats stats) {

        if (stats == null
                || stats.getTotalSolved() == null
                || stats.getTotalSolved() == 0) {

            return 0;
        }

        double hardWeight =
                stats.getHardSolved() * 3;

        double mediumWeight =
                stats.getMediumSolved() * 2;

        double easyWeight =
                stats.getEasySolved();

        double score =
                (hardWeight + mediumWeight + easyWeight)
                        / stats.getTotalSolved();

        return Math.min(score * 30, 100);
    }
}