package com.codementor.backend.seed;

import com.codementor.backend.entity.Difficulty;

import java.util.List;

public record ProblemSeedData(

        String title,

        String description,

        Difficulty difficulty,

        String topicSlug,

        String constraints,

        String inputFormat,

        String outputFormat,

        String sampleInput,

        String sampleOutput,

        List<String> tags,

        List<ProblemExampleSeedData> examples,

        List<TestCaseSeedData> testCases

) {
}