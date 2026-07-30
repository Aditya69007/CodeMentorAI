package com.codementor.backend.dto.leetcode.analytics;

import com.codementor.backend.dto.leetcode.calculator.AcceptanceCalculator;
import com.codementor.backend.dto.leetcode.calculator.ConsistencyCalculator;
import com.codementor.backend.dto.leetcode.calculator.ContestCalculator;
import com.codementor.backend.dto.leetcode.calculator.DeveloperScoreCalculator;
import com.codementor.backend.dto.leetcode.calculator.DifficultyCalculator;
import com.codementor.backend.dto.leetcode.calculator.SkillCalculator;
import com.codementor.backend.dto.leetcode.response.AnalyticsInfo;
import com.codementor.backend.dto.leetcode.response.CalendarInfo;
import com.codementor.backend.dto.leetcode.response.ContestInfo;
import com.codementor.backend.dto.leetcode.response.ProblemStats;
import com.codementor.backend.dto.leetcode.response.SkillInfo;
import com.codementor.backend.dto.leetcode.response.SkillStats;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LeetCodeAnalyticsServiceImpl
        implements LeetCodeAnalyticsService {

    private final AcceptanceCalculator acceptanceCalculator;

    private final ContestCalculator contestCalculator;

    private final ConsistencyCalculator consistencyCalculator;

    private final DifficultyCalculator difficultyCalculator;

    private final SkillCalculator skillCalculator;

    private final DeveloperScoreCalculator developerScoreCalculator;
    
    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
    
    @Override
    public AnalyticsInfo generateAnalytics(

            ContestInfo contest,

            CalendarInfo calendar,

            ProblemStats problems,

            SkillStats skills

    ) {

        double acceptance =
                acceptanceCalculator.calculate(problems);

        double contestScore =
                contestCalculator.calculate(contest);

        double consistency =
                consistencyCalculator.calculate(calendar);

        double difficulty =
                difficultyCalculator.calculate(problems);

        double skillScore =
                skillCalculator.calculate(skills);

        double developerScore =
                developerScoreCalculator.calculate(
                        acceptance,
                        contestScore,
                        consistency,
                        difficulty,
                        skillScore
                );

        List<SkillInfo> allSkills = List.of(
                skills.getFundamental(),
                skills.getIntermediate(),
                skills.getAdvanced()
        ).stream()
                .flatMap(List::stream)
                .toList();

        List<String> strongest =
                allSkills.stream()
                        .sorted(
                                Comparator.comparing(
                                        SkillInfo::getProblemsSolved
                                ).reversed()
                        )
                        .limit(5)
                        .map(SkillInfo::getTagName)
                        .toList();

        List<String> weakest =
                allSkills.stream()
                        .sorted(
                                Comparator.comparing(
                                        SkillInfo::getProblemsSolved
                                )
                        )
                        .limit(5)
                        .map(SkillInfo::getTagName)
                        .toList();

    return AnalyticsInfo.builder()
            .acceptanceRate(round(acceptance))
            .contestScore(round(contestScore))
            .consistencyScore(round(consistency))
            .difficultyScore(round(difficulty))
            .skillScore(round(skillScore))
            .developerScore(round(developerScore))
            .strongestSkills(strongest)
            .weakestSkills(weakest)
            .build();
        }
}