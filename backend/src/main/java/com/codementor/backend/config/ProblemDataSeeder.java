package com.codementor.backend.config;

import com.codementor.backend.entity.Problem;
import com.codementor.backend.entity.ProblemExample;
import com.codementor.backend.entity.TestCase;
import com.codementor.backend.entity.Topic;

import com.codementor.backend.repository.ProblemExampleRepository;
import com.codementor.backend.repository.ProblemRepository;
import com.codementor.backend.repository.TestCaseRepository;
import com.codementor.backend.repository.TopicRepository;

import com.codementor.backend.seed.ProblemExampleSeedData;
import com.codementor.backend.seed.ProblemSeedData;
import com.codementor.backend.seed.TestCaseSeedData;
import com.codementor.backend.seed.problems.ProblemSeedRegistry;

import lombok.RequiredArgsConstructor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Component
@RequiredArgsConstructor
@Order(2)
public class ProblemDataSeeder
        implements CommandLineRunner {


    private final ProblemRepository problemRepository;

    private final TopicRepository topicRepository;

    private final TestCaseRepository testCaseRepository;

    private final ProblemExampleRepository
            problemExampleRepository;


    // ==================================================
    // START PROBLEM SEEDING
    // ==================================================

    @Override
    @Transactional
    public void run(String... args) {

        List<ProblemSeedData> problems =
                ProblemSeedRegistry.getAllProblems();


        for (ProblemSeedData seed : problems) {

            seedProblem(seed);

        }

    }


    // ==================================================
    // CREATE OR UPDATE PROBLEM
    // ==================================================

    private void seedProblem(
            ProblemSeedData seed
    ) {


        Topic topic =
                topicRepository
                        .findBySlug(
                                seed.topicSlug()
                        )
                        .orElseThrow(() ->

                                new IllegalStateException(

                                        "Topic not found: "
                                                + seed.topicSlug()

                                )

                        );


        Problem problem =
                problemRepository
                        .findByTitleIgnoreCase(
                                seed.title()
                        )
                        .orElse(null);


        // ==================================================
        // CREATE NEW PROBLEM
        // ==================================================

        if (problem == null) {


            problem =
                    Problem
                            .builder()

                            .title(
                                    seed.title()
                            )

                            .description(
                                    seed.description()
                            )

                            .difficulty(
                                    seed.difficulty()
                            )

                            .topic(
                                    topic
                            )

                            .constraints(
                                    seed.constraints()
                            )

                            .inputFormat(
                                    seed.inputFormat()
                            )

                            .outputFormat(
                                    seed.outputFormat()
                            )

                            .sampleInput(
                                    seed.sampleInput()
                            )

                            .sampleOutput(
                                    seed.sampleOutput()
                            )

                            .tags(
                                    new ArrayList<>(
                                            seed.tags()
                                    )
                            )

                            .active(true)

                            .build();


        }


        // ==================================================
        // UPDATE EXISTING PROBLEM
        // ==================================================

        else {


            problem.setTitle(
                    seed.title()
            );


            problem.setDescription(
                    seed.description()
            );


            problem.setDifficulty(
                    seed.difficulty()
            );


            problem.setTopic(
                    topic
            );


            problem.setConstraints(
                    seed.constraints()
            );


            problem.setInputFormat(
                    seed.inputFormat()
            );


            problem.setOutputFormat(
                    seed.outputFormat()
            );


            problem.setSampleInput(
                    seed.sampleInput()
            );


            problem.setSampleOutput(
                    seed.sampleOutput()
            );


            problem.setTags(

                    new ArrayList<>(
                            seed.tags()
                    )

            );


            problem.setActive(true);

        }


        // ==================================================
        // SAVE PROBLEM
        // ==================================================

        problem =
                problemRepository.save(
                        problem
                );


        // ==================================================
        // SYNCHRONIZE EXAMPLES
        // ==================================================

        synchronizeExamples(

                problem,

                seed.examples()

        );


        // ==================================================
        // SYNCHRONIZE TEST CASES
        // ==================================================

        synchronizeTestCases(

                problem,

                seed.testCases()

        );

    }


    // ==================================================
    // SYNCHRONIZE PROBLEM EXAMPLES
    // ==================================================

    private void synchronizeExamples(

            Problem problem,

            List<ProblemExampleSeedData> examples

    ) {


        // Delete existing examples first

        problemExampleRepository
                .deleteByProblemId(
                        problem.getId()
                );


        // No new examples

        if (
                examples == null
                        ||
                examples.isEmpty()
        ) {

            return;

        }


        // Create latest examples

        List<ProblemExample> newExamples =
                new ArrayList<>();


        for (
                ProblemExampleSeedData exampleSeed
                : examples
        ) {


            ProblemExample example =
                    ProblemExample
                            .builder()

                            .input(
                                    exampleSeed.input()
                            )

                            .output(
                                    exampleSeed.output()
                            )

                            .explanation(
                                    exampleSeed.explanation()
                            )

                            .orderIndex(
                                    exampleSeed.orderIndex()
                            )

                            .problem(
                                    problem
                            )

                            .build();


            newExamples.add(
                    example
            );

        }


        problemExampleRepository
                .saveAll(
                        newExamples
                );

    }


    // ==================================================
    // SYNCHRONIZE TEST CASES
    // ==================================================

    private void synchronizeTestCases(

            Problem problem,

            List<TestCaseSeedData> testCases

    ) {


        // Delete existing test cases first

        testCaseRepository
                .deleteByProblemId(
                        problem.getId()
                );


        // No new test cases

        if (
                testCases == null
                        ||
                testCases.isEmpty()
        ) {

            return;

        }


        // Create latest test cases

        List<TestCase> newTestCases =
                new ArrayList<>();


        for (
                TestCaseSeedData testCaseSeed
                : testCases
        ) {


            TestCase testCase =
                    TestCase
                            .builder()

                            .input(
                                    testCaseSeed.input()
                            )

                            .expectedOutput(
                                    testCaseSeed.expectedOutput()
                            )

                            .hidden(
                                    testCaseSeed.hidden()
                            )

                            .problem(
                                    problem
                            )

                            .build();


            newTestCases.add(
                    testCase
            );

        }


        testCaseRepository
                .saveAll(
                        newTestCases
                );

    }

}