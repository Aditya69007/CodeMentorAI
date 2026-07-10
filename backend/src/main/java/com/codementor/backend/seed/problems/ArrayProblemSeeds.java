package com.codementor.backend.seed.problems;

import com.codementor.backend.entity.Difficulty;
import com.codementor.backend.seed.ProblemExampleSeedData;
import com.codementor.backend.seed.ProblemSeedData;
import com.codementor.backend.seed.TestCaseSeedData;

import java.util.List;

public final class ArrayProblemSeeds {

private ArrayProblemSeeds() {
}

public static List<ProblemSeedData> getProblems() {

    return List.of(

            // ==================================================
            // 1. FIND MAXIMUM ELEMENT
            // ==================================================

            new ProblemSeedData(
                    "Find Maximum Element",
                    """
                    Given an array containing n integers, find the largest value present in the array.

                    The array may contain positive numbers, negative numbers, zero, or duplicate values.

                    Your task is to examine all elements and print the maximum value found in the array.
                    """,
                    Difficulty.EASY,
                    "array",
                    """
                    1 <= n <= 100000

                    -1000000000 <= array[i] <= 1000000000
                    """,
                    """
                    The first line contains an integer n representing the number of elements in the array.

                    The second line contains n space-separated integers.
                    """,
                    """
                    Print a single integer representing the maximum value present in the array.
                    """,
                    """
                    5
                    4 8 2 9 1
                    """,
                    "9",
                    List.of("Array", "Traversal"),
                    List.of(
                            new ProblemExampleSeedData(
                                    """
                                    5
                                    4 8 2 9 1
                                    """,
                                    "9",
                                    "Among all elements, 9 is the largest value.",
                                    1
                            ),
                            new ProblemExampleSeedData(
                                    """
                                    4
                                    -5 -2 -10 -1
                                    """,
                                    "-1",
                                    "All elements are negative, and -1 is the largest value.",
                                    2
                            ),
                            new ProblemExampleSeedData(
                                    """
                                    1
                                    100
                                    """,
                                    "100",
                                    "The only element is automatically the maximum.",
                                    3
                            ),
                            new ProblemExampleSeedData(
                                    """
                                    5
                                    7 7 7 7 7
                                    """,
                                    "7",
                                    "All elements are equal, so the maximum is 7.",
                                    4
                            )
                    ),
                    List.of(
                            new TestCaseSeedData("5\n4 8 2 9 1", "9", false),
                            new TestCaseSeedData("4\n-5 -2 -10 -1", "-1", false),
                            new TestCaseSeedData("1\n100", "100", true),
                            new TestCaseSeedData("5\n7 7 7 7 7", "7", true),
                            new TestCaseSeedData("6\n-10 0 5 100 -20 99", "100", true)
                    )
            ),

            // ==================================================
            // 2. REVERSE AN ARRAY
            // ==================================================

            new ProblemSeedData(
                    "Reverse an Array",
                    """
                    Given an array containing n integers, print the elements of the array in reverse order.

                    The first element of the original array should become the last element, the second element should become the second-last element, and so on.
                    """,
                    Difficulty.EASY,
                    "array",
                    """
                    1 <= n <= 100000

                    -1000000000 <= array[i] <= 1000000000
                    """,
                    """
                    The first line contains an integer n.

                    The second line contains n space-separated integers.
                    """,
                    "Print the array elements in reverse order.",
                    """
                    5
                    1 2 3 4 5
                    """,
                    "5 4 3 2 1",
                    List.of("Array", "Two Pointers"),
                    List.of(
                            new ProblemExampleSeedData("5\n1 2 3 4 5", "5 4 3 2 1",
                                    "The complete order of the array is reversed.", 1),
                            new ProblemExampleSeedData("3\n10 20 30", "30 20 10",
                                    "Reading from right to left produces the result.", 2),
                            new ProblemExampleSeedData("1\n7", "7",
                                    "A single-element array remains unchanged.", 3),
                            new ProblemExampleSeedData("4\n-1 0 5 -8", "-8 5 0 -1",
                                    "The elements are printed from the last position to the first.", 4)
                    ),
                    List.of(
                            new TestCaseSeedData("5\n1 2 3 4 5", "5 4 3 2 1", false),
                            new TestCaseSeedData("3\n10 20 30", "30 20 10", false),
                            new TestCaseSeedData("1\n7", "7", true),
                            new TestCaseSeedData("4\n-1 0 5 -8", "-8 5 0 -1", true),
                            new TestCaseSeedData("6\n1 1 2 2 3 3", "3 3 2 2 1 1", true)
                    )
            ),

            // ==================================================
            // 3. TWO SUM
            // ==================================================

            new ProblemSeedData(
                    "Two Sum",
                    """
                    Given an array of n integers and a target value, find two different elements whose sum equals the target.

                    Print their zero-based indices in increasing order.

                    Exactly one valid answer exists, and the same element cannot be used twice.
                    """,
                    Difficulty.EASY,
                    "array",
                    """
                    2 <= n <= 100000

                    -1000000000 <= array[i], target <= 1000000000

                    Exactly one valid answer exists.
                    """,
                    """
                    The first line contains n.

                    The second line contains n integers.

                    The third line contains the target.
                    """,
                    "Print the two zero-based indices in increasing order.",
                    """
                    4
                    2 7 11 15
                    9
                    """,
                    "0 1",
                    List.of("Array", "Hashing"),
                    List.of(
                            new ProblemExampleSeedData("4\n2 7 11 15\n9", "0 1",
                                    "2 + 7 equals 9.", 1),
                            new ProblemExampleSeedData("3\n3 2 4\n6", "1 2",
                                    "2 + 4 equals 6.", 2),
                            new ProblemExampleSeedData("2\n3 3\n6", "0 1",
                                    "The equal values occur at different indices.", 3),
                            new ProblemExampleSeedData("5\n-5 10 2 8 1\n3", "0 3",
                                    "-5 + 8 equals 3.", 4)
                    ),
                    List.of(
                            new TestCaseSeedData("4\n2 7 11 15\n9", "0 1", false),
                            new TestCaseSeedData("3\n3 2 4\n6", "1 2", false),
                            new TestCaseSeedData("2\n3 3\n6", "0 1", true),
                            new TestCaseSeedData("5\n-5 10 2 8 1\n3", "0 3", true),
                            new TestCaseSeedData("6\n1 5 8 12 20 25\n32", "3 4", true)
                    )
            ),

            // ==================================================
            // 4. REMOVE DUPLICATES FROM SORTED ARRAY
            // ==================================================

            new ProblemSeedData(
                    "Remove Duplicates from Sorted Array",
                    """
                    Given a sorted array containing n integers, remove duplicate values so every distinct element appears exactly once.

                    Print the number of distinct elements followed by the distinct values in sorted order.
                    """,
                    Difficulty.EASY,
                    "array",
                    """
                    1 <= n <= 100000

                    The input array is sorted in non-decreasing order.
                    """,
                    """
                    The first line contains n.

                    The second line contains n sorted integers.
                    """,
                    """
                    Print the number of distinct elements on the first line.

                    Print the distinct values on the second line.
                    """,
                    "3\n1 1 2",
                    "2\n1 2",
                    List.of("Array", "Two Pointers"),
                    List.of(
                            new ProblemExampleSeedData("3\n1 1 2", "2\n1 2",
                                    "The duplicate 1 is included only once.", 1),
                            new ProblemExampleSeedData("10\n0 0 1 1 1 2 2 3 3 4", "5\n0 1 2 3 4",
                                    "Five distinct values remain.", 2),
                            new ProblemExampleSeedData("1\n7", "1\n7",
                                    "No duplicate exists.", 3),
                            new ProblemExampleSeedData("5\n2 2 2 2 2", "1\n2",
                                    "Only one distinct value remains.", 4)
                    ),
                    List.of(
                            new TestCaseSeedData("3\n1 1 2", "2\n1 2", false),
                            new TestCaseSeedData("10\n0 0 1 1 1 2 2 3 3 4", "5\n0 1 2 3 4", false),
                            new TestCaseSeedData("1\n7", "1\n7", true),
                            new TestCaseSeedData("5\n2 2 2 2 2", "1\n2", true),
                            new TestCaseSeedData("8\n-3 -3 -1 -1 0 2 2 5", "5\n-3 -1 0 2 5", true)
                    )
            ),

            // ==================================================
            // 5. MAXIMUM SUBARRAY SUM
            // ==================================================

            new ProblemSeedData(
                    "Maximum Subarray Sum",
                    """
                    Given an integer array containing n elements, find the contiguous subarray with the largest possible sum.

                    A subarray is a continuous sequence containing at least one element.
                    """,
                    Difficulty.MEDIUM,
                    "array",
                    """
                    1 <= n <= 100000

                    The answer fits within a signed 64-bit integer.
                    """,
                    "The first line contains n. The second line contains n integers.",
                    "Print the maximum contiguous subarray sum.",
                    "9\n-2 1 -3 4 -1 2 1 -5 4",
                    "6",
                    List.of("Array", "Dynamic Programming", "Kadane's Algorithm"),
                    List.of(
                            new ProblemExampleSeedData("9\n-2 1 -3 4 -1 2 1 -5 4", "6",
                                    "[4, -1, 2, 1] has sum 6.", 1),
                            new ProblemExampleSeedData("1\n5", "5",
                                    "The single element is the answer.", 2),
                            new ProblemExampleSeedData("5\n-8 -3 -6 -2 -5", "-2",
                                    "The best subarray contains only -2.", 3),
                            new ProblemExampleSeedData("5\n1 2 3 4 5", "15",
                                    "The entire array produces the maximum sum.", 4)
                    ),
                    List.of(
                            new TestCaseSeedData("9\n-2 1 -3 4 -1 2 1 -5 4", "6", false),
                            new TestCaseSeedData("1\n5", "5", false),
                            new TestCaseSeedData("5\n-8 -3 -6 -2 -5", "-2", true),
                            new TestCaseSeedData("5\n1 2 3 4 5", "15", true),
                            new TestCaseSeedData("8\n5 -2 3 -10 7 8 -1 4", "18", true),
                            new TestCaseSeedData("6\n-1 2 3 -2 5 -10", "8", true)
                    )
            ),

            // ==================================================
            // 6. ROTATE ARRAY
            // ==================================================

            new ProblemSeedData(
                    "Rotate Array",
                    """
                    Given an array containing n integers, rotate the array to the right by k positions.

                    During one right rotation, the last element moves to the first position and every other element moves one position to the right.

                    The value of k may be greater than n. In that case, only the effective number of rotations should be performed.

                    For example, rotating [1, 2, 3, 4, 5, 6, 7] three positions to the right produces [5, 6, 7, 1, 2, 3, 4].
                    """,
                    Difficulty.MEDIUM,
                    "array",
                    """
                    1 <= n <= 100000

                    0 <= k <= 1000000000

                    -1000000000 <= array[i] <= 1000000000
                    """,
                    """
                    The first line contains an integer n.

                    The second line contains n space-separated integers.

                    The third line contains the number of right rotations k.
                    """,
                    "Print the rotated array as n space-separated integers.",
                    "7\n1 2 3 4 5 6 7\n3",
                    "5 6 7 1 2 3 4",
                    List.of("Array", "Two Pointers", "Rotation"),
                    List.of(
                            new ProblemExampleSeedData("7\n1 2 3 4 5 6 7\n3", "5 6 7 1 2 3 4",
                                    "The final three elements move to the beginning.", 1),
                            new ProblemExampleSeedData("4\n-1 -100 3 99\n2", "3 99 -1 -100",
                                    "Rotating twice moves 3 and 99 to the front.", 2),
                            new ProblemExampleSeedData("5\n1 2 3 4 5\n0", "1 2 3 4 5",
                                    "Zero rotations leave the array unchanged.", 3),
                            new ProblemExampleSeedData("3\n10 20 30\n4", "30 10 20",
                                    "Four rotations on three elements are equivalent to one rotation.", 4),
                            new ProblemExampleSeedData("1\n8\n100", "8",
                                    "A single-element array remains unchanged.", 5)
                    ),
                    List.of(
                            new TestCaseSeedData("7\n1 2 3 4 5 6 7\n3", "5 6 7 1 2 3 4", false),
                            new TestCaseSeedData("4\n-1 -100 3 99\n2", "3 99 -1 -100", false),
                            new TestCaseSeedData("5\n1 2 3 4 5\n0", "1 2 3 4 5", true),
                            new TestCaseSeedData("3\n10 20 30\n4", "30 10 20", true),
                            new TestCaseSeedData("1\n8\n100", "8", true),
                            new TestCaseSeedData("6\n1 1 2 2 3 3\n8", "3 3 1 1 2 2", true)
                    )
            ),

            // ==================================================
            // 7. BEST TIME TO BUY AND SELL STOCK
            // ==================================================

            new ProblemSeedData(
                    "Best Time to Buy and Sell Stock",
                    """
                    You are given an array where prices[i] represents the price of a stock on day i.

                    Choose exactly one day to buy the stock and a later day to sell it in order to maximize your profit.

                    You may complete at most one transaction.

                    If no profitable transaction is possible, print 0.

                    For example, for prices [7, 1, 5, 3, 6, 4], buying at price 1 and selling later at price 6 produces the maximum profit of 5.
                    """,
                    Difficulty.EASY,
                    "array",
                    """
                    1 <= n <= 100000

                    0 <= prices[i] <= 1000000000
                    """,
                    """
                    The first line contains an integer n representing the number of days.

                    The second line contains n space-separated integers representing stock prices.
                    """,
                    "Print the maximum profit obtainable from at most one transaction.",
                    "6\n7 1 5 3 6 4",
                    "5",
                    List.of("Array", "Greedy"),
                    List.of(
                            new ProblemExampleSeedData("6\n7 1 5 3 6 4", "5",
                                    "Buy at price 1 and sell later at price 6 for profit 5.", 1),
                            new ProblemExampleSeedData("5\n7 6 4 3 1", "0",
                                    "Prices continuously decrease, so no profitable transaction exists.", 2),
                            new ProblemExampleSeedData("5\n1 2 3 4 5", "4",
                                    "Buy at price 1 and sell at price 5.", 3),
                            new ProblemExampleSeedData("1\n10", "0",
                                    "A transaction is impossible with only one day.", 4),
                            new ProblemExampleSeedData("6\n3 3 5 0 0 3", "3",
                                    "Buy at price 0 and later sell at price 3.", 5)
                    ),
                    List.of(
                            new TestCaseSeedData("6\n7 1 5 3 6 4", "5", false),
                            new TestCaseSeedData("5\n7 6 4 3 1", "0", false),
                            new TestCaseSeedData("5\n1 2 3 4 5", "4", true),
                            new TestCaseSeedData("1\n10", "0", true),
                            new TestCaseSeedData("6\n3 3 5 0 0 3", "3", true),
                            new TestCaseSeedData("8\n10 2 8 1 9 3 12 4", "11", true)
                    )
            ),

            // ==================================================
            // 8. MAJORITY ELEMENT
            // ==================================================

            new ProblemSeedData(
                    "Majority Element",
                    """
                    Given an array containing n integers, find the majority element.

                    The majority element is the value that appears more than floor(n / 2) times.

                    You may assume that a majority element always exists in the input array.

                    For example, in [2, 2, 1, 1, 1, 2, 2], the value 2 appears four times out of seven elements, so 2 is the majority element.
                    """,
                    Difficulty.EASY,
                    "array",
                    """
                    1 <= n <= 100000

                    -1000000000 <= array[i] <= 1000000000

                    A majority element always exists.
                    """,
                    """
                    The first line contains an integer n.

                    The second line contains n space-separated integers.
                    """,
                    "Print the majority element.",
                    "7\n2 2 1 1 1 2 2",
                    "2",
                    List.of("Array", "Hashing", "Boyer-Moore Voting Algorithm"),
                    List.of(
                            new ProblemExampleSeedData("3\n3 2 3", "3",
                                    "The value 3 appears twice, which is more than floor(3 / 2).", 1),
                            new ProblemExampleSeedData("7\n2 2 1 1 1 2 2", "2",
                                    "The value 2 appears four times out of seven elements.", 2),
                            new ProblemExampleSeedData("1\n8", "8",
                                    "The only element is automatically the majority element.", 3),
                            new ProblemExampleSeedData("5\n-1 -1 -1 2 3", "-1",
                                    "-1 appears three times, which is more than floor(5 / 2).", 4),
                            new ProblemExampleSeedData("6\n5 5 2 5 3 5", "5",
                                    "The value 5 appears four times out of six elements.", 5)
                    ),
                    List.of(
                            new TestCaseSeedData("3\n3 2 3", "3", false),
                            new TestCaseSeedData("7\n2 2 1 1 1 2 2", "2", false),
                            new TestCaseSeedData("1\n8", "8", true),
                            new TestCaseSeedData("5\n-1 -1 -1 2 3", "-1", true),
                            new TestCaseSeedData("6\n5 5 2 5 3 5", "5", true),
                            new TestCaseSeedData("9\n4 1 4 2 4 4 3 4 4", "4", true)
                    )
                        ),

            // ==================================================
            // 9. PRODUCT OF ARRAY EXCEPT SELF
            // ==================================================

            new ProblemSeedData(
                    "Product of Array Except Self",
                    """
                    Given an integer array containing n elements, construct a new array where each position contains the product of all elements of the original array except the element at that position.

                    You must solve the problem without using division.

                    The result for each index should be calculated using all elements that appear before and after that index.

                    For example, for the array [1, 2, 3, 4], the result is [24, 12, 8, 6].

                    At index 0, the product of all other elements is 2 × 3 × 4 = 24.

                    At index 1, the product is 1 × 3 × 4 = 12, and the same rule applies to every remaining position.
                    """,
                    Difficulty.MEDIUM,
                    "array",
                    """
                    2 <= n <= 100000

                    -30 <= array[i] <= 30

                    The product of any prefix or suffix of the array fits within a signed 64-bit integer.

                    Division must not be used to construct the result.
                    """,
                    """
                    The first line contains an integer n representing the number of elements in the array.

                    The second line contains n space-separated integers.
                    """,
                    """
                    Print n space-separated integers where the value at each index is the product of all elements except the original element at that index.
                    """,
                    """
                    4
                    1 2 3 4
                    """,
                    "24 12 8 6",
                    List.of(
                            "Array",
                            "Prefix Product",
                            "Suffix Product"
                    ),
                    List.of(
                            new ProblemExampleSeedData(
                                    """
                                    4
                                    1 2 3 4
                                    """,
                                    "24 12 8 6",
                                    """
                                    For each position, multiply every array element except the element at that position.

                                    The resulting values are 24, 12, 8, and 6.
                                    """,
                                    1
                            ),
                            new ProblemExampleSeedData(
                                    """
                                    5
                                    -1 1 0 -3 3
                                    """,
                                    "0 0 9 0 0",
                                    """
                                    Because the array contains one zero, every result except the position containing zero becomes 0.

                                    At the zero position, the product of all remaining elements is 9.
                                    """,
                                    2
                            ),
                            new ProblemExampleSeedData(
                                    """
                                    3
                                    2 3 4
                                    """,
                                    "12 8 6",
                                    """
                                    Excluding 2 gives 3 × 4 = 12.

                                    Excluding 3 gives 2 × 4 = 8.

                                    Excluding 4 gives 2 × 3 = 6.
                                    """,
                                    3
                            ),
                            new ProblemExampleSeedData(
                                    """
                                    4
                                    0 2 0 4
                                    """,
                                    "0 0 0 0",
                                    """
                                    The array contains more than one zero.

                                    Every product therefore contains at least one zero, so all result values are 0.
                                    """,
                                    4
                            ),
                            new ProblemExampleSeedData(
                                    """
                                    2
                                    5 10
                                    """,
                                    "10 5",
                                    """
                                    For the first position, only 10 remains.

                                    For the second position, only 5 remains.
                                    """,
                                    5
                            )
                    ),
                    List.of(
                            new TestCaseSeedData(
                                    "4\n1 2 3 4",
                                    "24 12 8 6",
                                    false
                            ),
                            new TestCaseSeedData(
                                    "5\n-1 1 0 -3 3",
                                    "0 0 9 0 0",
                                    false
                            ),
                            new TestCaseSeedData(
                                    "3\n2 3 4",
                                    "12 8 6",
                                    true
                            ),
                            new TestCaseSeedData(
                                    "4\n0 2 0 4",
                                    "0 0 0 0",
                                    true
                            ),
                            new TestCaseSeedData(
                                    "2\n5 10",
                                    "10 5",
                                    true
                            ),
                            new TestCaseSeedData(
                                    "5\n-1 -2 -3 -4 -5",
                                    "120 60 40 30 24",
                                    true
                            )
                    )
            ),

            // ==================================================
            // 10. MERGE INTERVALS
            // ==================================================

            new ProblemSeedData(
                    "Merge Intervals",
                    """
                    Given n intervals, merge all intervals that overlap and print the resulting non-overlapping intervals.

                    Each interval contains a start value and an end value.

                    Two intervals overlap when they share at least one common point. Overlapping intervals must be combined into a single interval covering the complete range of both intervals.

                    The input intervals may be given in any order.

                    The resulting merged intervals must be printed in increasing order of their starting values.

                    For example, the intervals [1, 3] and [2, 6] overlap. They should therefore be merged into the interval [1, 6].
                    """,
                    Difficulty.MEDIUM,
                    "array",
                    """
                    1 <= n <= 100000

                    -1000000000 <= start <= end <= 1000000000
                    """,
                    """
                    The first line contains an integer n representing the number of intervals.

                    Each of the next n lines contains two space-separated integers representing the start and end values of one interval.
                    """,
                    """
                    Print the number of merged intervals on the first line.

                    Then print each merged interval on a separate line as two space-separated integers.

                    The merged intervals must be printed in increasing order of their starting values.
                    """,
                    """
                    4
                    1 3
                    2 6
                    8 10
                    15 18
                    """,
                    """
                    3
                    1 6
                    8 10
                    15 18
                    """,
                    List.of(
                            "Array",
                            "Sorting",
                            "Intervals"
                    ),
                    List.of(
                            new ProblemExampleSeedData(
                                    """
                                    4
                                    1 3
                                    2 6
                                    8 10
                                    15 18
                                    """,
                                    """
                                    3
                                    1 6
                                    8 10
                                    15 18
                                    """,
                                    """
                                    The intervals [1, 3] and [2, 6] overlap, so they merge into [1, 6].

                                    The remaining intervals do not overlap.
                                    """,
                                    1
                            ),
                            new ProblemExampleSeedData(
                                    """
                                    2
                                    1 4
                                    4 5
                                    """,
                                    """
                                    1
                                    1 5
                                    """,
                                    """
                                    The intervals meet at value 4, so they overlap and merge into [1, 5].
                                    """,
                                    2
                            ),
                            new ProblemExampleSeedData(
                                    """
                                    3
                                    1 10
                                    2 5
                                    3 7
                                    """,
                                    """
                                    1
                                    1 10
                                    """,
                                    """
                                    The first interval completely contains the other two intervals.

                                    Therefore, only [1, 10] remains.
                                    """,
                                    3
                            ),
                            new ProblemExampleSeedData(
                                    """
                                    3
                                    8 10
                                    1 3
                                    4 6
                                    """,
                                    """
                                    3
                                    1 3
                                    4 6
                                    8 10
                                    """,
                                    """
                                    The intervals are initially unordered.

                                    After sorting by starting value, none of them overlap.
                                    """,
                                    4
                            ),
                            new ProblemExampleSeedData(
                                    """
                                    5
                                    -10 -5
                                    -7 0
                                    1 3
                                    2 8
                                    10 12
                                    """,
                                    """
                                    3
                                    -10 0
                                    1 8
                                    10 12
                                    """,
                                    """
                                    The first two intervals merge into [-10, 0].

                                    The intervals [1, 3] and [2, 8] merge into [1, 8].

                                    The final interval remains separate.
                                    """,
                                    5
                            )
                    ),
                    List.of(
                            new TestCaseSeedData(
                                    "4\n1 3\n2 6\n8 10\n15 18",
                                    "3\n1 6\n8 10\n15 18",
                                    false
                            ),
                            new TestCaseSeedData(
                                    "2\n1 4\n4 5",
                                    "1\n1 5",
                                    false
                            ),
                            new TestCaseSeedData(
                                    "3\n1 10\n2 5\n3 7",
                                    "1\n1 10",
                                    true
                            ),
                            new TestCaseSeedData(
                                    "3\n8 10\n1 3\n4 6",
                                    "3\n1 3\n4 6\n8 10",
                                    true
                            ),
                            new TestCaseSeedData(
                                    "5\n-10 -5\n-7 0\n1 3\n2 8\n10 12",
                                    "3\n-10 0\n1 8\n10 12",
                                    true
                            ),
                            new TestCaseSeedData(
                                    "6\n1 2\n2 3\n3 4\n10 15\n12 20\n25 30",
                                    "3\n1 4\n10 20\n25 30",
                                    true
                            )
                    )
            )
    );
}

}