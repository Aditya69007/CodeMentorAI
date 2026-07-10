package com.codementor.backend.seed.problems;

import com.codementor.backend.entity.Difficulty;
import com.codementor.backend.seed.ProblemExampleSeedData;
import com.codementor.backend.seed.ProblemSeedData;
import com.codementor.backend.seed.TestCaseSeedData;

import java.util.List;

public final class BinarySearchProblemSeeds {

    private BinarySearchProblemSeeds() {
    }

    public static List<ProblemSeedData> getProblems() {

        return List.of(

                // ==================================================
                // 1. SEARCH IN SORTED ARRAY
                // ==================================================

                new ProblemSeedData(

                        "Search in Sorted Array",

                        """
                        Given a sorted array containing n distinct integers and a target value, find the position of the target.

                        The array is sorted in ascending order.

                        If the target exists in the array, print its zero-based index.

                        If the target does not exist, print -1.

                        Your solution should take advantage of the sorted order and use binary search.

                        Binary search repeatedly compares the target with the middle element of the current search range and eliminates half of the remaining elements after every comparison.
                        """,

                        Difficulty.EASY,

                        "binary-search",

                        """
                        1 <= n <= 100000

                        -1000000000 <= array[i] <= 1000000000

                        -1000000000 <= target <= 1000000000

                        The array is sorted in strictly increasing order.
                        """,

                        """
                        The first line contains an integer n representing the number of elements.

                        The second line contains n space-separated integers sorted in ascending order.

                        The third line contains an integer target.
                        """,

                        """
                        Print the zero-based index of the target if it exists.

                        Otherwise, print -1.
                        """,

                        """
                        5
                        1 3 5 7 9
                        7
                        """,

                        "3",

                        List.of(
                                "Binary Search",
                                "Array",
                                "Searching"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        5
                                        1 3 5 7 9
                                        7
                                        """,
                                        "3",
                                        """
                                        The target value 7 appears at zero-based index 3.

                                        Therefore, the answer is 3.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        5
                                        1 3 5 7 9
                                        4
                                        """,
                                        "-1",
                                        """
                                        The target value 4 does not appear in the array.

                                        Therefore, the answer is -1.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        1
                                        10
                                        10
                                        """,
                                        "0",
                                        """
                                        The only element is equal to the target.

                                        Its zero-based index is 0.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        6
                                        -10 -5 0 4 8 15
                                        -10
                                        """,
                                        "0",
                                        """
                                        The target -10 is the first element.

                                        Therefore, its index is 0.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        6
                                        2 4 6 8 10 12
                                        12
                                        """,
                                        "5",
                                        """
                                        The target 12 is the final element.

                                        Therefore, its zero-based index is 5.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "5\n1 3 5 7 9\n7",
                                        "3",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "5\n1 3 5 7 9\n4",
                                        "-1",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "1\n10\n10",
                                        "0",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "6\n-10 -5 0 4 8 15\n-10",
                                        "0",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "6\n2 4 6 8 10 12\n12",
                                        "5",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "7\n-20 -10 -5 0 5 10 20\n100",
                                        "-1",
                                        true
                                )
                        )
                ),

                // ==================================================
                // 2. FIRST AND LAST POSITION OF TARGET
                // ==================================================

                new ProblemSeedData(

                        "First and Last Position of Target",

                        """
                        Given an integer array sorted in non-decreasing order and a target value, find the first and last positions where the target appears.

                        The array may contain duplicate values.

                        If the target exists, print its first zero-based index followed by its last zero-based index.

                        If the target does not exist, print -1 -1.

                        Your solution should use binary search and run in logarithmic time.

                        For example, in the array [5, 7, 7, 8, 8, 10], the target 8 first appears at index 3 and last appears at index 4.

                        Therefore, the answer is 3 4.
                        """,

                        Difficulty.EASY,

                        "binary-search",

                        """
                        1 <= n <= 100000

                        -1000000000 <= array[i] <= 1000000000

                        -1000000000 <= target <= 1000000000

                        The array is sorted in non-decreasing order.
                        """,

                        """
                        The first line contains an integer n representing the number of elements.

                        The second line contains n space-separated integers sorted in non-decreasing order.

                        The third line contains an integer target.
                        """,

                        """
                        Print two space-separated integers representing the first and last zero-based positions of the target.

                        If the target does not exist, print -1 -1.
                        """,

                        """
                        6
                        5 7 7 8 8 10
                        8
                        """,

                        "3 4",

                        List.of(
                                "Binary Search",
                                "Array"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        6
                                        5 7 7 8 8 10
                                        8
                                        """,
                                        "3 4",
                                        """
                                        The target 8 appears at indices 3 and 4.

                                        Therefore, its first position is 3 and its last position is 4.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        6
                                        5 7 7 8 8 10
                                        6
                                        """,
                                        "-1 -1",
                                        """
                                        The target 6 does not appear in the array.

                                        Therefore, both positions are -1.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        1
                                        5
                                        5
                                        """,
                                        "0 0",
                                        """
                                        The only element is equal to the target.

                                        Therefore, both the first and last positions are 0.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        7
                                        2 2 2 2 2 2 2
                                        2
                                        """,
                                        "0 6",
                                        """
                                        Every element is equal to the target.

                                        The first occurrence is at index 0 and the last occurrence is at index 6.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        8
                                        -5 -5 -2 0 0 0 7 10
                                        0
                                        """,
                                        "3 5",
                                        """
                                        The target 0 appears at indices 3, 4, and 5.

                                        Therefore, the first and last positions are 3 and 5.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "6\n5 7 7 8 8 10\n8",
                                        "3 4",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "6\n5 7 7 8 8 10\n6",
                                        "-1 -1",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "1\n5\n5",
                                        "0 0",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "7\n2 2 2 2 2 2 2\n2",
                                        "0 6",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "8\n-5 -5 -2 0 0 0 7 10\n0",
                                        "3 5",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "5\n1 2 3 4 5\n10",
                                        "-1 -1",
                                        true
                                )
                        )
                ),

                // ==================================================
                // 3. SEARCH IN ROTATED SORTED ARRAY
                // ==================================================

                new ProblemSeedData(

                        "Search in Rotated Sorted Array",

                        """
                        Given an array containing n distinct integers that was originally sorted in ascending order and then possibly rotated, find the zero-based index of a target value.

                        A rotation moves some elements from the beginning of the sorted array to the end while preserving their relative order.

                        For example, the sorted array [0, 1, 2, 4, 5, 6, 7] may become [4, 5, 6, 7, 0, 1, 2].

                        If the target exists, print its zero-based index.

                        Otherwise, print -1.

                        Your solution should use the properties of the rotated sorted array and run in logarithmic time.
                        """,

                        Difficulty.MEDIUM,

                        "binary-search",

                        """
                        1 <= n <= 100000

                        -1000000000 <= array[i] <= 1000000000

                        -1000000000 <= target <= 1000000000

                        All array elements are distinct.

                        The array was originally sorted in ascending order and may have been rotated.
                        """,

                        """
                        The first line contains an integer n.

                        The second line contains n space-separated integers representing the rotated sorted array.

                        The third line contains the target value.
                        """,

                        """
                        Print the zero-based index of the target if it exists.

                        Otherwise, print -1.
                        """,

                        """
                        7
                        4 5 6 7 0 1 2
                        0
                        """,

                        "4",

                        List.of(
                                "Binary Search",
                                "Array"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        7
                                        4 5 6 7 0 1 2
                                        0
                                        """,
                                        "4",
                                        """
                                        The target 0 appears at zero-based index 4.

                                        Therefore, the answer is 4.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        7
                                        4 5 6 7 0 1 2
                                        3
                                        """,
                                        "-1",
                                        """
                                        The target 3 does not appear in the rotated array.

                                        Therefore, the answer is -1.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        1
                                        1
                                        1
                                        """,
                                        "0",
                                        """
                                        The only element is equal to the target.

                                        Therefore, its index is 0.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        6
                                        6 7 1 2 3 4
                                        7
                                        """,
                                        "1",
                                        """
                                        The target 7 appears at index 1.

                                        Therefore, the answer is 1.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        5
                                        1 2 3 4 5
                                        4
                                        """,
                                        "3",
                                        """
                                        The array has not been rotated.

                                        Standard binary search finds the target 4 at index 3.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "7\n4 5 6 7 0 1 2\n0",
                                        "4",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "7\n4 5 6 7 0 1 2\n3",
                                        "-1",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "1\n1\n1",
                                        "0",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "6\n6 7 1 2 3 4\n7",
                                        "1",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "5\n1 2 3 4 5\n4",
                                        "3",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "8\n30 40 50 60 5 10 20 25\n10",
                                        "5",
                                        true
                                )
                        )
                ),

                // ==================================================
                // 4. FIND MINIMUM IN ROTATED SORTED ARRAY
                // ==================================================

                new ProblemSeedData(

                        "Find Minimum in Rotated Sorted Array",

                        """
                        Given an array containing n distinct integers that was originally sorted in ascending order and then possibly rotated, find the minimum element.

                        For example, the sorted array [0, 1, 2, 4, 5, 6, 7] may be rotated to become [4, 5, 6, 7, 0, 1, 2].

                        The minimum element in this rotated array is 0.

                        The array may also remain completely sorted without any rotation.

                        Your solution should use binary search and run in logarithmic time.
                        """,

                        Difficulty.MEDIUM,

                        "binary-search",

                        """
                        1 <= n <= 100000

                        -1000000000 <= array[i] <= 1000000000

                        All array elements are distinct.

                        The array was originally sorted in ascending order and may have been rotated.
                        """,

                        """
                        The first line contains an integer n.

                        The second line contains n space-separated integers representing the rotated sorted array.
                        """,

                        """
                        Print the minimum element in the array.
                        """,

                        """
                        5
                        3 4 5 1 2
                        """,

                        "1",

                        List.of(
                                "Binary Search",
                                "Array"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        5
                                        3 4 5 1 2
                                        """,
                                        "1",
                                        """
                                        The values decrease between 5 and 1 because of the rotation.

                                        The value 1 is the smallest element.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        7
                                        4 5 6 7 0 1 2
                                        """,
                                        "0",
                                        """
                                        The minimum value is 0.

                                        It appears immediately after the rotation point.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        4
                                        11 13 15 17
                                        """,
                                        "11",
                                        """
                                        The array was not rotated.

                                        Therefore, the first element 11 is the minimum.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        1
                                        -5
                                        """,
                                        "-5",
                                        """
                                        A single-element array has that element as its minimum.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        6
                                        2 3 4 5 6 1
                                        """,
                                        "1",
                                        """
                                        The smallest element 1 appears at the final position after rotation.

                                        Therefore, the answer is 1.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "5\n3 4 5 1 2",
                                        "1",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "7\n4 5 6 7 0 1 2",
                                        "0",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "4\n11 13 15 17",
                                        "11",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "1\n-5",
                                        "-5",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "6\n2 3 4 5 6 1",
                                        "1",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "8\n100 200 300 400 -50 -20 0 50",
                                        "-50",
                                        true
                                )
                        )
                ),

                // ==================================================
                // 5. MEDIAN OF TWO SORTED ARRAYS
                // ==================================================

                new ProblemSeedData(

                        "Median of Two Sorted Arrays",

                        """
                        Given two sorted integer arrays, find the median of all values that would appear if both arrays were merged into one sorted sequence.

                        The median is the middle value of a sorted sequence containing an odd number of elements.

                        For a sorted sequence containing an even number of elements, the median is the average of the two middle values.

                        Print the answer with exactly one digit after the decimal point.

                        For example, merging [1, 3] and [2] produces [1, 2, 3].

                        The middle value is 2, so the median is 2.0.

                        Your solution should use binary search and should not require fully merging both arrays.
                        """,

                        Difficulty.HARD,

                        "binary-search",

                        """
                        0 <= n, m <= 100000

                        1 <= n + m <= 200000

                        -1000000000 <= array1[i], array2[i] <= 1000000000

                        Both arrays are sorted in non-decreasing order.
                        """,

                        """
                        The first line contains two space-separated integers n and m.

                        The second line contains n space-separated integers representing the first sorted array.

                        The third line contains m space-separated integers representing the second sorted array.

                        If one array has size 0, its corresponding input line is empty.
                        """,

                        """
                        Print the median with exactly one digit after the decimal point.
                        """,

                        """
                        2 1
                        1 3
                        2
                        """,

                        "2.0",

                        List.of(
                                "Binary Search",
                                "Array",
                                "Divide and Conquer"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        2 1
                                        1 3
                                        2
                                        """,
                                        "2.0",
                                        """
                                        Combining the values conceptually produces [1, 2, 3].

                                        The middle value is 2.

                                        Therefore, the median is 2.0.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        2 2
                                        1 2
                                        3 4
                                        """,
                                        "2.5",
                                        """
                                        The combined sorted sequence is [1, 2, 3, 4].

                                        The two middle values are 2 and 3.

                                        Their average is 2.5.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        2 2
                                        0 0
                                        0 0
                                        """,
                                        "0.0",
                                        """
                                        Every value is 0.

                                        Therefore, the median is 0.0.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        0 2

                                        1 5
                                        """,
                                        "3.0",
                                        """
                                        The first array is empty.

                                        The combined sorted sequence is [1, 5].

                                        The average of 1 and 5 is 3.0.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        3 4
                                        -5 -1 10
                                        -3 0 4 20
                                        """,
                                        "0.0",
                                        """
                                        The combined sorted sequence is [-5, -3, -1, 0, 4, 10, 20].

                                        The middle value is 0.

                                        Therefore, the median is 0.0.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "2 1\n1 3\n2",
                                        "2.0",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "2 2\n1 2\n3 4",
                                        "2.5",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "2 2\n0 0\n0 0",
                                        "0.0",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "0 2\n\n1 5",
                                        "3.0",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "3 4\n-5 -1 10\n-3 0 4 20",
                                        "0.0",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "1 2\n100\n200 300",
                                        "200.0",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "3 3\n1 3 8\n7 9 10",
                                        "7.5",
                                        true
                                )
                        )
                )
        );
    }
}