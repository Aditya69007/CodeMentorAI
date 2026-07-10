package com.codementor.backend.seed.problems;

import com.codementor.backend.entity.Difficulty;
import com.codementor.backend.seed.ProblemExampleSeedData;
import com.codementor.backend.seed.ProblemSeedData;
import com.codementor.backend.seed.TestCaseSeedData;

import java.util.List;

public final class TwoPointersProblemSeeds {

    private TwoPointersProblemSeeds() {
    }

    public static List<ProblemSeedData> getProblems() {

        return List.of(

                // ==================================================
                // MOVE ZEROES
                // ==================================================

                new ProblemSeedData(

                        "Move Zeroes",

                        """
                        Given an array containing n integers, move all zero values to the end of the array while preserving the relative order of all non-zero elements.

                        The non-zero elements must appear in the same order as they appeared in the original array.

                        The size of the array must remain unchanged.

                        For example, the array [0, 1, 0, 3, 12] becomes [1, 3, 12, 0, 0].

                        The values 1, 3, and 12 keep their original relative order, while both zero values are moved to the end.
                        """,

                        Difficulty.EASY,

                        "two-pointers",

                        """
                        1 <= n <= 100000

                        -1000000000 <= array[i] <= 1000000000
                        """,

                        """
                        The first line contains an integer n representing the number of elements in the array.

                        The second line contains n space-separated integers.
                        """,

                        """
                        Print n space-separated integers representing the array after moving all zero values to the end.

                        Preserve the relative order of all non-zero elements.
                        """,

                        """
                        5
                        0 1 0 3 12
                        """,

                        "1 3 12 0 0",

                        List.of(
                                "Two Pointers",
                                "Array"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        5
                                        0 1 0 3 12
                                        """,
                                        "1 3 12 0 0",
                                        """
                                        The non-zero elements are 1, 3, and 12.

                                        They keep their original relative order.

                                        The two zero values are moved to the final two positions.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        1
                                        0
                                        """,
                                        "0",
                                        """
                                        The array contains only one zero.

                                        It is already in its correct final position.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        4
                                        1 2 3 4
                                        """,
                                        "1 2 3 4",
                                        """
                                        The array contains no zero values.

                                        Therefore, the array remains unchanged.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        5
                                        0 0 0 0 0
                                        """,
                                        "0 0 0 0 0",
                                        """
                                        Every element is zero.

                                        Moving the zero values to the end does not change the array.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        6
                                        0 -3 0 5 7 0
                                        """,
                                        "-3 5 7 0 0 0",
                                        """
                                        The non-zero elements -3, 5, and 7 remain in their original order.

                                        The three zero values are placed after them.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "5\n0 1 0 3 12",
                                        "1 3 12 0 0",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "1\n0",
                                        "0",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "4\n1 2 3 4",
                                        "1 2 3 4",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "5\n0 0 0 0 0",
                                        "0 0 0 0 0",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "6\n0 -3 0 5 7 0",
                                        "-3 5 7 0 0 0",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "8\n1 0 2 0 0 3 0 4",
                                        "1 2 3 4 0 0 0 0",
                                        true
                                )
                        )
                ),

                // ==================================================
                // SQUARES OF A SORTED ARRAY
                // ==================================================

                new ProblemSeedData(

                        "Squares of a Sorted Array",

                        """
                        Given an integer array sorted in non-decreasing order, create a new array containing the square of every element.

                        Print the squared values in non-decreasing order.

                        Simply squaring the elements from left to right does not always produce a sorted result because negative values can have large absolute values.

                        For example, squaring [-4, -1, 0, 3, 10] produces [16, 1, 0, 9, 100] before sorting.

                        The required sorted result is [0, 1, 9, 16, 100].
                        """,

                        Difficulty.EASY,

                        "two-pointers",

                        """
                        1 <= n <= 100000

                        -100000 <= array[i] <= 100000

                        The input array is sorted in non-decreasing order.

                        Each squared value fits within a signed 64-bit integer.
                        """,

                        """
                        The first line contains an integer n representing the number of elements.

                        The second line contains n space-separated integers sorted in non-decreasing order.
                        """,

                        """
                        Print n space-separated integers containing the squares of all elements in non-decreasing order.
                        """,

                        """
                        5
                        -4 -1 0 3 10
                        """,

                        "0 1 9 16 100",

                        List.of(
                                "Two Pointers",
                                "Array",
                                "Sorting"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        5
                                        -4 -1 0 3 10
                                        """,
                                        "0 1 9 16 100",
                                        """
                                        Squaring the values gives 16, 1, 0, 9, and 100.

                                        Arranging these squared values in non-decreasing order produces 0 1 9 16 100.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        5
                                        -7 -3 2 3 11
                                        """,
                                        "4 9 9 49 121",
                                        """
                                        The squared values are 49, 9, 4, 9, and 121.

                                        In sorted order, they become 4 9 9 49 121.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        4
                                        1 2 3 4
                                        """,
                                        "1 4 9 16",
                                        """
                                        Every input value is positive.

                                        Their squares are already produced in increasing order.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        4
                                        -5 -4 -2 -1
                                        """,
                                        "1 4 16 25",
                                        """
                                        Every value is negative.

                                        Values with smaller absolute values produce smaller squares.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        1
                                        0
                                        """,
                                        "0",
                                        """
                                        The square of 0 is 0.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "5\n-4 -1 0 3 10",
                                        "0 1 9 16 100",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "5\n-7 -3 2 3 11",
                                        "4 9 9 49 121",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "4\n1 2 3 4",
                                        "1 4 9 16",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "4\n-5 -4 -2 -1",
                                        "1 4 16 25",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "1\n0",
                                        "0",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "7\n-10 -3 -1 0 1 4 8",
                                        "0 1 1 9 16 64 100",
                                        true
                                )
                        )
                ),

                // ==================================================
                // CONTAINER WITH MOST WATER
                // ==================================================

                new ProblemSeedData(

                        "Container With Most Water",

                        """
                        Given n non-negative integers representing the heights of vertical lines, choose two lines that together with the horizontal axis form a container.

                        The amount of water held by a pair of lines is determined by the shorter line multiplied by the horizontal distance between the two lines.

                        More precisely, if lines at indices i and j are selected, the container area is:

                        min(height[i], height[j]) × (j - i)

                        Find and print the maximum amount of water that can be contained.

                        The lines themselves cannot be tilted or rearranged.

                        For example, for heights [1, 8, 6, 2, 5, 4, 8, 3, 7], choosing the lines at indices 1 and 8 gives an area of min(8, 7) × 7 = 49.
                        """,

                        Difficulty.MEDIUM,

                        "two-pointers",

                        """
                        2 <= n <= 100000

                        0 <= height[i] <= 1000000000

                        The answer fits within a signed 64-bit integer.
                        """,

                        """
                        The first line contains an integer n representing the number of vertical lines.

                        The second line contains n space-separated integers representing their heights.
                        """,

                        """
                        Print a single integer representing the maximum amount of water that can be contained.
                        """,

                        """
                        9
                        1 8 6 2 5 4 8 3 7
                        """,

                        "49",

                        List.of(
                                "Two Pointers",
                                "Array",
                                "Greedy"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        9
                                        1 8 6 2 5 4 8 3 7
                                        """,
                                        "49",
                                        """
                                        Choose the lines at indices 1 and 8.

                                        Their heights are 8 and 7, and the distance between them is 7.

                                        The area is min(8, 7) × 7 = 49, which is the maximum possible area.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        2
                                        1 1
                                        """,
                                        "1",
                                        """
                                        Only one pair of lines can be selected.

                                        Their minimum height is 1 and their distance is 1, giving an area of 1.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        4
                                        1 2 3 4
                                        """,
                                        "4",
                                        """
                                        Choosing the lines with heights 2 and 4 gives a distance of 2.

                                        The resulting area is min(2, 4) × 2 = 4.

                                        No other pair produces a larger area.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        5
                                        5 5 5 5 5
                                        """,
                                        "20",
                                        """
                                        Choose the first and last lines.

                                        Their height is 5 and the distance between them is 4.

                                        Therefore, the maximum area is 5 × 4 = 20.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        3
                                        0 10 0
                                        """,
                                        "0",
                                        """
                                        Every possible pair contains at least one line with height 0.

                                        Therefore, every container has area 0.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "9\n1 8 6 2 5 4 8 3 7",
                                        "49",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "2\n1 1",
                                        "1",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "4\n1 2 3 4",
                                        "4",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "5\n5 5 5 5 5",
                                        "20",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "3\n0 10 0",
                                        "0",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "6\n2 3 10 5 7 8",
                                        "24",
                                        true
                                )
                        )
                ),

                // ==================================================
                // THREE SUM
                // ==================================================

                new ProblemSeedData(

                        "Three Sum",

                        """
                        Given an integer array containing n elements, find all unique triplets whose sum is equal to zero.

                        A triplet consists of three different array positions.

                        The same combination of values must appear only once in the result, even if duplicate values in the input allow the same triplet to be formed using different indices.

                        Inside each triplet, print the three values in non-decreasing order.

                        Print all unique triplets in lexicographical order.

                        First print the total number of unique triplets.

                        For example, the array [-1, 0, 1, 2, -1, -4] contains two unique triplets whose sum is zero: [-1, -1, 2] and [-1, 0, 1].
                        """,

                        Difficulty.MEDIUM,

                        "two-pointers",

                        """
                        3 <= n <= 3000

                        -100000 <= array[i] <= 100000
                        """,

                        """
                        The first line contains an integer n representing the number of elements.

                        The second line contains n space-separated integers.
                        """,

                        """
                        Print the number of unique triplets on the first line.

                        Then print each triplet on a separate line as three space-separated integers.

                        Values inside each triplet must be in non-decreasing order.

                        Triplets must be printed in lexicographical order.

                        If no valid triplet exists, print only 0.
                        """,

                        """
                        6
                        -1 0 1 2 -1 -4
                        """,

                        """
                        2
                        -1 -1 2
                        -1 0 1
                        """,

                        List.of(
                                "Two Pointers",
                                "Array",
                                "Sorting"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        6
                                        -1 0 1 2 -1 -4
                                        """,
                                        """
                                        2
                                        -1 -1 2
                                        -1 0 1
                                        """,
                                        """
                                        The values -1, -1, and 2 sum to 0.

                                        The values -1, 0, and 1 also sum to 0.

                                        No other unique triplet has a sum of zero, so the answer contains two triplets.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        3
                                        0 1 1
                                        """,
                                        "0",
                                        """
                                        The only possible triplet has sum 2.

                                        Therefore, no valid triplet exists.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        3
                                        0 0 0
                                        """,
                                        """
                                        1
                                        0 0 0
                                        """,
                                        """
                                        The three zero values form a triplet whose sum is 0.

                                        Although all values are equal, this is one valid unique triplet.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        7
                                        -2 0 1 1 2 -1 -4
                                        """,
                                        """
                                        3
                                        -2 0 2
                                        -2 1 1
                                        -1 0 1
                                        """,
                                        """
                                        Three unique combinations sum to zero.

                                        After sorting the values inside each triplet and ordering the triplets lexicographically, the required result is shown.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        6
                                        -2 -2 0 0 2 2
                                        """,
                                        """
                                        1
                                        -2 0 2
                                        """,
                                        """
                                        Duplicate input values can create the same value combination using different indices.

                                        The triplet -2 0 2 must still be printed only once.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "6\n-1 0 1 2 -1 -4",
                                        "2\n-1 -1 2\n-1 0 1",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "3\n0 1 1",
                                        "0",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "3\n0 0 0",
                                        "1\n0 0 0",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "7\n-2 0 1 1 2 -1 -4",
                                        "3\n-2 0 2\n-2 1 1\n-1 0 1",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "6\n-2 -2 0 0 2 2",
                                        "1\n-2 0 2",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "8\n-3 -2 -1 0 1 2 3 4",
                                        "5\n-3 -1 4\n-3 0 3\n-3 1 2\n-2 -1 3\n-2 0 2",
                                        true
                                )
                        )
                )
        );
    }
}