package com.codementor.backend.dto.leetcode.calculator;

import com.codementor.backend.dto.leetcode.response.SkillStats;
import org.springframework.stereotype.Component;

@Component
public class SkillCalculator {

    public double calculate(SkillStats skills) {

        if (skills == null) {

            return 0;
        }

        int total =

                skills.getFundamental().size()

                        + skills.getIntermediate().size()

                        + skills.getAdvanced().size();

        return Math.min(total, 100);
    }
}