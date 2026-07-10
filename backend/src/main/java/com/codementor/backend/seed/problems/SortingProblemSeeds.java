package com.codementor.backend.seed.problems;

import com.codementor.backend.entity.Difficulty;
import com.codementor.backend.seed.ProblemExampleSeedData;
import com.codementor.backend.seed.ProblemSeedData;
import com.codementor.backend.seed.TestCaseSeedData;

import java.util.List;

public final class SortingProblemSeeds {

    private SortingProblemSeeds() {
    }

    public static List<ProblemSeedData> getProblems() {

        return List.of(

                // ==================================================
                // SORT AN ARRAY
                // ==================================================

                new ProblemSeedData(

                        "Sort an Array",

                        """
                        Given an array containing n integers, arrange all elements in non-decreasing order.

                        In non-decreasing order, every element must be greater than or equal to the element before it.

                        The array may contain positive values, negative values, zero, and duplicate elements.

                        For example, the array [5, 2, 3, 1] becomes [1, 2, 3, 5] after sorting.
                        """,

                        Difficulty.EASY,

                        "sorting",

                        """
                        1 <= n <= 100000

                        -1000000000 <= array[i] <= 1000000000
                        """,

                        """
                        The first line contains an integer n representing the number of elements.

                        The second line contains n space-separated integers.
                        """,

                        """
                        Print n space-separated integers representing the array in non-decreasing order.
                        """,

                        """
                        4
                        5 2 3 1
                        """,

                        "1 2 3 5",

                        List.of(
                                "Sorting",
                                "Array"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        4
                                        5 2 3 1
                                        """,
                                        "1 2 3 5",
                                        """
                                        Arranging the elements from smallest to largest produces 1, 2, 3, and 5.

                                        Therefore, the sorted array is 1 2 3 5.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        6
                                        5 1 1 2 0 0
                                        """,
                                        "0 0 1 1 2 5",
                                        """
                                        The two zero values appear first, followed by the two values equal to 1, then 2 and 5.

                                        Duplicate values are preserved in the sorted result.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        5
                                        -3 -1 -7 4 0
                                        """,
                                        "-7 -3 -1 0 4",
                                        """
                                        Negative values are smaller than zero and positive values.

                                        Arranging every value in non-decreasing order gives -7 -3 -1 0 4.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        4
                                        1 2 3 4
                                        """,
                                        "1 2 3 4",
                                        """
                                        The array is already sorted.

                                        Therefore, its order remains unchanged.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        1
                                        10
                                        """,
                                        "10",
                                        """
                                        An array containing one element is already sorted.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "4\n5 2 3 1",
                                        "1 2 3 5",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "6\n5 1 1 2 0 0",
                                        "0 0 1 1 2 5",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "5\n-3 -1 -7 4 0",
                                        "-7 -3 -1 0 4",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "4\n1 2 3 4",
                                        "1 2 3 4",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "1\n10",
                                        "10",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "7\n9 -2 5 5 0 -10 3",
                                        "-10 -2 0 3 5 5 9",
                                        true
                                )
                        )
                ),

                // ==================================================
                // SORT COLORS
                // ==================================================

                new ProblemSeedData(

                        "Sort Colors",

                        """
                        Given an array containing only the values 0, 1, and 2, arrange the elements in non-decreasing order.

                        All values equal to 0 must appear first, followed by all values equal to 1, and then all values equal to 2.

                        The values may be interpreted as three different colors.

                        For example, the array [2, 0, 2, 1, 1, 0] becomes [0, 0, 1, 1, 2, 2].

                        Solve the problem by correctly reorganizing every value in the array.
                        """,

                        Difficulty.MEDIUM,

                        "sorting",

                        """
                        1 <= n <= 100000

                        array[i] is 0, 1, or 2.
                        """,

                        """
                        The first line contains an integer n representing the number of elements.

                        The second line contains n space-separated integers, where every value is 0, 1, or 2.
                        """,

                        """
                        Print n space-separated integers in non-decreasing order.
                        """,

                        """
                        6
                        2 0 2 1 1 0
                        """,

                        "0 0 1 1 2 2",

                        List.of(
                                "Sorting",
                                "Array",
                                "Two Pointers"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        6
                                        2 0 2 1 1 0
                                        """,
                                        "0 0 1 1 2 2",
                                        """
                                        The input contains two zero values, two one values, and two two values.

                                        Placing the values in non-decreasing order produces 0 0 1 1 2 2.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        3
                                        2 0 1
                                        """,
                                        "0 1 2",
                                        """
                                        Each possible value appears exactly once.

                                        Their sorted order is 0 1 2.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        5
                                        0 0 0 0 0
                                        """,
                                        "0 0 0 0 0",
                                        """
                                        Every value is already 0.

                                        Therefore, the array remains unchanged.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        7
                                        2 2 1 1 0 0 1
                                        """,
                                        "0 0 1 1 1 2 2",
                                        """
                                        The two zero values come first, followed by three one values and two two values.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        1
                                        2
                                        """,
                                        "2",
                                        """
                                        A single-element array is already sorted.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "6\n2 0 2 1 1 0",
                                        "0 0 1 1 2 2",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "3\n2 0 1",
                                        "0 1 2",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "5\n0 0 0 0 0",
                                        "0 0 0 0 0",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "7\n2 2 1 1 0 0 1",
                                        "0 0 1 1 1 2 2",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "1\n2",
                                        "2",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "8\n1 2 0 2 1 0 2 0",
                                        "0 0 0 1 1 2 2 2",
                                        true
                                )
                        )
                ),

                // ==================================================
                // MERGE TWO SORTED ARRAYS
                // ==================================================

                new ProblemSeedData(

                        "Merge Two Sorted Arrays",

                        """
                        Given two integer arrays sorted in non-decreasing order, merge them into one sorted array.

                        The resulting array must contain every element from both input arrays, including duplicate values.

                        The relative ordering required by non-decreasing order must be preserved.

                        For example, merging [1, 3, 5] and [2, 4, 6] produces [1, 2, 3, 4, 5, 6].

                        The two input arrays may have different sizes.
                        """,

                        Difficulty.EASY,

                        "sorting",

                        """
                        1 <= n, m <= 100000

                        -1000000000 <= array1[i], array2[i] <= 1000000000

                        Both input arrays are sorted in non-decreasing order.

                        n + m <= 200000
                        """,

                        """
                        The first line contains an integer n representing the size of the first array.

                        The second line contains n space-separated integers.

                        The third line contains an integer m representing the size of the second array.

                        The fourth line contains m space-separated integers.
                        """,

                        """
                        Print n + m space-separated integers representing the merged array in non-decreasing order.
                        """,

                        """
                        3
                        1 3 5
                        3
                        2 4 6
                        """,

                        "1 2 3 4 5 6",

                        List.of(
                                "Sorting",
                                "Array",
                                "Two Pointers"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        3
                                        1 3 5
                                        3
                                        2 4 6
                                        """,
                                        "1 2 3 4 5 6",
                                        """
                                        Compare elements from both sorted arrays and repeatedly choose the smaller available value.

                                        The final merged order is 1 2 3 4 5 6.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        3
                                        1 2 3
                                        4
                                        4 5 6 7
                                        """,
                                        "1 2 3 4 5 6 7",
                                        """
                                        Every value in the first array is smaller than every value in the second array.

                                        Therefore, the second array is placed directly after the first.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        4
                                        1 2 2 5
                                        3
                                        2 3 5
                                        """,
                                        "1 2 2 2 3 5 5",
                                        """
                                        Duplicate values from both arrays must be preserved.

                                        The merged result therefore contains three occurrences of 2 and two occurrences of 5.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        2
                                        -5 -1
                                        3
                                        -4 0 10
                                        """,
                                        "-5 -4 -1 0 10",
                                        """
                                        Negative, zero, and positive values are merged according to their numerical order.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        1
                                        10
                                        1
                                        5
                                        """,
                                        "5 10",
                                        """
                                        Comparing the two values shows that 5 must appear before 10.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "3\n1 3 5\n3\n2 4 6",
                                        "1 2 3 4 5 6",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "3\n1 2 3\n4\n4 5 6 7",
                                        "1 2 3 4 5 6 7",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "4\n1 2 2 5\n3\n2 3 5",
                                        "1 2 2 2 3 5 5",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "2\n-5 -1\n3\n-4 0 10",
                                        "-5 -4 -1 0 10",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "1\n10\n1\n5",
                                        "5 10",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "5\n-10 0 4 4 20\n4\n-5 1 4 30",
                                        "-10 -5 0 1 4 4 4 20 30",
                                        true
                                )
                        )
                ),

                // ==================================================
                // COUNT INVERSIONS
                // ==================================================

                new ProblemSeedData(

                        "Count Inversions",

                        """
                        Given an integer array containing n elements, count the total number of inversions in the array.

                        An inversion is a pair of indices (i, j) such that i is smaller than j, but array[i] is greater than array[j].

                        In other words, an inversion represents a pair of elements that appear in the opposite order from how they would appear in a sorted array.

                        For example, in the array [2, 4, 1, 3, 5], the inversion pairs are (2, 1), (4, 1), and (4, 3).

                        Therefore, the total number of inversions is 3.

                        Equal values do not form an inversion.
                        """,

                        Difficulty.HARD,

                        "sorting",

                        """
                        1 <= n <= 200000

                        -1000000000 <= array[i] <= 1000000000

                        The answer fits within a signed 64-bit integer.
                        """,

                        """
                        The first line contains an integer n representing the number of elements.

                        The second line contains n space-separated integers.
                        """,

                        """
                        Print a single integer representing the total number of inversion pairs.
                        """,

                        """
                        5
                        2 4 1 3 5
                        """,

                        "3",

                        List.of(
                                "Sorting",
                                "Merge Sort",
                                "Divide and Conquer",
                                "Array"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        5
                                        2 4 1 3 5
                                        """,
                                        "3",
                                        """
                                        The inversion pairs are (2, 1), (4, 1), and (4, 3).

                                        Therefore, the total number of inversions is 3.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        5
                                        1 2 3 4 5
                                        """,
                                        "0",
                                        """
                                        Every smaller value already appears before every larger value.

                                        Therefore, the array contains no inversions.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        5
                                        5 4 3 2 1
                                        """,
                                        "10",
                                        """
                                        Every pair of indices forms an inversion because the array is in strictly decreasing order.

                                        An array of 5 elements contains 5 × 4 / 2 = 10 pairs.

                                        Therefore, the answer is 10.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        4
                                        1 1 1 1
                                        """,
                                        "0",
                                        """
                                        Equal values do not form inversions.

                                        Therefore, the answer is 0.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        3
                                        3 1 2
                                        """,
                                        "2",
                                        """
                                        The value 3 forms an inversion with 1 and with 2.

                                        The pair (1, 2) is already in the correct order.

                                        Therefore, the total number of inversions is 2.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "5\n2 4 1 3 5",
                                        "3",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "5\n1 2 3 4 5",
                                        "0",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "5\n5 4 3 2 1",
                                        "10",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "4\n1 1 1 1",
                                        "0",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "3\n3 1 2",
                                        "2",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "6\n8 4 2 1 3 5",
                                        "9",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "6\n-1 -3 -2 0 2 1",
                                        "3",
                                        true
                                )
                        )
                )
        );
    }
}