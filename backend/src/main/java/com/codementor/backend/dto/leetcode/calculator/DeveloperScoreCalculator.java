package com.codementor.backend.dto.leetcode.calculator;

import org.springframework.stereotype.Component;

@Component
public class DeveloperScoreCalculator {

    public double calculate(

            double acceptance,

            double contest,

            double consistency,

            double difficulty,

            double skills

    ) {

        return

                acceptance * 0.25

                        + contest * 0.20

                        + consistency * 0.20

                        + difficulty * 0.15

                        + skills * 0.20;
    }
}