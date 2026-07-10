package com.codementor.backend.seed.problems;

import com.codementor.backend.seed.ProblemSeedData;

import java.util.ArrayList;
import java.util.List;

public final class ProblemSeedRegistry {

    private ProblemSeedRegistry() {
    }

    public static List<ProblemSeedData> getAllProblems() {

        List<ProblemSeedData> problems =
                new ArrayList<>();

        problems.addAll(StackProblemSeeds.getProblems());
        problems.addAll(ArrayProblemSeeds.getProblems());
        problems.addAll(StringProblemSeeds.getProblems());
        problems.addAll(BinarySearchProblemSeeds.getProblems());
        problems.addAll(HashingProblemSeeds.getProblems());
        problems.addAll(TwoPointersProblemSeeds.getProblems());
        problems.addAll(SlidingWindowProblemSeeds.getProblems());
        problems.addAll(SortingProblemSeeds.getProblems());
        problems.addAll(QueueProblemSeeds.getProblems());
        problems.addAll(LinkedListProblemSeeds.getProblems());
        problems.addAll(RecursionProblemSeeds.getProblems());
        problems.addAll(BacktrackingProblemSeeds.getProblems());
        problems.addAll(BinarySearchTreeProblemSeeds.getProblems());
        problems.addAll(BitManipulationProblemSeeds.getProblems());
        problems.addAll(BreadthFirstSearchProblemSeeds.getProblems());
        problems.addAll(DepthFirstSearchProblemSeeds.getProblems());
        problems.addAll(DisjointSetProblemSeeds.getProblems());
        problems.addAll(DynamicProgrammingProblemSeeds.getProblems());
        problems.addAll(GraphProblemSeeds.getProblems());
        problems.addAll(GreedyProblemSeeds.getProblems());
        problems.addAll(HeapProblemSeeds.getProblems());
        problems.addAll(MathProblemSeeds.getProblems());
        problems.addAll(MatrixProblemSeeds.getProblems());
        problems.addAll(TreeProblemSeeds.getProblems());
        problems.addAll(TrieProblemSeeds.getProblems());
        return problems;
    }
}