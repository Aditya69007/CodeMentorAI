package com.codementor.backend.seed.problems;

import com.codementor.backend.entity.Difficulty;
import com.codementor.backend.seed.ProblemExampleSeedData;
import com.codementor.backend.seed.ProblemSeedData;
import com.codementor.backend.seed.TestCaseSeedData;

import java.util.List;

public final class DynamicProgrammingProblemSeeds {

    private DynamicProgrammingProblemSeeds() {
    }

    public static List<ProblemSeedData> getProblems() {

        return List.of(

                // ==================================================
                // 1. CLIMBING STAIRS
                // ==================================================

                new ProblemSeedData(

                        "Climbing Stairs",

                        """
                        You are climbing a staircase containing n steps.

                        From each step, you may climb either 1 step or 2 steps at a time.

                        Determine the total number of distinct ways to reach exactly the top of the staircase.

                        Two ways are considered different if the sequence of 1-step and 2-step moves is different.

                        For example, when n = 3, there are three possible ways:

                        1 + 1 + 1

                        1 + 2

                        2 + 1

                        Print the total number of distinct ways to reach the top.
                        """,

                        Difficulty.EASY,

                        "dynamic-programming",

                        """
                        1 <= n <= 45
                        """,

                        """
                        A single integer n representing the number of staircase steps.
                        """,

                        """
                        Print the total number of distinct ways to reach the top.
                        """,

                        "3",

                        "3",

                        List.of(
                                "Dynamic Programming",
                                "Memoization",
                                "Tabulation"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        "3",
                                        "3",
                                        """
                                        There are three possible ways to reach step 3:

                                        1 + 1 + 1

                                        1 + 2

                                        2 + 1

                                        Therefore, the answer is 3.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        "2",
                                        "2",
                                        """
                                        The staircase can be climbed using 1 + 1 or a single 2-step move.

                                        Therefore, there are two distinct ways.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        "1",
                                        "1",
                                        """
                                        Only one move is possible.

                                        Therefore, exactly one way exists.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        "4",
                                        "5",
                                        """
                                        The five possible ways are:

                                        1 + 1 + 1 + 1

                                        1 + 1 + 2

                                        1 + 2 + 1

                                        2 + 1 + 1

                                        2 + 2

                                        Therefore, the answer is 5.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        "5",
                                        "8",
                                        """
                                        The number of ways to reach step 5 is the sum of the ways to reach steps 4 and 3.

                                        Therefore, the answer is 5 + 3 = 8.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData("3", "3", false),
                                new TestCaseSeedData("2", "2", false),
                                new TestCaseSeedData("1", "1", true),
                                new TestCaseSeedData("4", "5", true),
                                new TestCaseSeedData("5", "8", true),
                                new TestCaseSeedData("10", "89", true)
                        )
                ),

                // ==================================================
                // 2. MINIMUM COST CLIMBING STAIRS
                // ==================================================

                new ProblemSeedData(

                        "Minimum Cost Climbing Stairs",

                        """
                        You are given an array cost containing n integers.

                        The value cost[i] represents the cost of stepping on stair i.

                        After paying the cost of the current stair, you may climb either one step or two steps.

                        You may begin from stair 0 or stair 1.

                        The top of the staircase is located immediately after the final array element.

                        Find the minimum total cost required to reach the top.

                        Print the minimum possible cost.
                        """,

                        Difficulty.EASY,

                        "dynamic-programming",

                        """
                        2 <= n <= 100000

                        0 <= cost[i] <= 100000
                        """,

                        """
                        The first line contains an integer n.

                        The second line contains n space-separated integers representing the cost array.
                        """,

                        """
                        Print the minimum total cost required to reach the top.
                        """,

                        """
                        3
                        10 15 20
                        """,

                        "15",

                        List.of(
                                "Dynamic Programming",
                                "Array",
                                "Space Optimization"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        3
                                        10 15 20
                                        """,
                                        "15",
                                        """
                                        Start from stair 1 and pay cost 15.

                                        Then move two steps directly to the top.

                                        Therefore, the minimum total cost is 15.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        10
                                        1 100 1 1 1 100 1 1 100 1
                                        """,
                                        "6",
                                        """
                                        An optimal route avoids the expensive stairs whenever possible.

                                        The minimum accumulated cost is 6.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        2
                                        5 10
                                        """,
                                        "5",
                                        """
                                        Start from stair 0, pay cost 5, and move two steps to the top.

                                        Therefore, the answer is 5.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        4
                                        0 0 0 0
                                        """,
                                        "0",
                                        """
                                        Every stair has zero cost.

                                        Therefore, the top can be reached without paying any cost.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        5
                                        5 1 2 10 1
                                        """,
                                        "4",
                                        """
                                        One optimal route uses stairs with costs 1, 2, and 1.

                                        Their total cost is 4.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "3\n10 15 20",
                                        "15",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "10\n1 100 1 1 1 100 1 1 100 1",
                                        "6",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "2\n5 10",
                                        "5",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "4\n0 0 0 0",
                                        "0",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "5\n5 1 2 10 1",
                                        "4",
                                        true
                                )
                        )
                ),

                // ==================================================
                // 3. HOUSE ROBBER
                // ==================================================

                new ProblemSeedData(

                        "House Robber",

                        """
                        You are given an array containing the amount of money stored in each house along a street.

                        A security system prevents you from taking money from two adjacent houses.

                        If two neighboring houses are both selected, the security system is triggered.

                        Determine the maximum total amount of money that can be collected without selecting two adjacent houses.

                        You may choose any valid subset of houses.

                        Print the maximum possible amount.
                        """,

                        Difficulty.MEDIUM,

                        "dynamic-programming",

                        """
                        1 <= n <= 100000

                        0 <= money[i] <= 100000
                        """,

                        """
                        The first line contains an integer n.

                        The second line contains n space-separated integers representing the amount of money in each house.
                        """,

                        """
                        Print the maximum amount of money that can be collected without selecting adjacent houses.
                        """,

                        """
                        4
                        1 2 3 1
                        """,

                        "4",

                        List.of(
                                "Dynamic Programming",
                                "Array",
                                "Space Optimization"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        4
                                        1 2 3 1
                                        """,
                                        "4",
                                        """
                                        Select the first house containing 1 and the third house containing 3.

                                        These houses are not adjacent.

                                        The total amount is 1 + 3 = 4.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        5
                                        2 7 9 3 1
                                        """,
                                        "12",
                                        """
                                        Select houses containing 2, 9, and 1.

                                        None of the selected houses are adjacent.

                                        Their total value is 12.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        1
                                        10
                                        """,
                                        "10",
                                        """
                                        Only one house exists.

                                        Therefore, selecting it gives the maximum amount of 10.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        4
                                        5 5 5 5
                                        """,
                                        "10",
                                        """
                                        Select either the first and third houses or the second and fourth houses.

                                        The maximum total is 10.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        6
                                        10 1 1 10 1 10
                                        """,
                                        "30",
                                        """
                                        Select the first, fourth, and sixth houses.

                                        No two selected houses are adjacent.

                                        Their total amount is 30.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "4\n1 2 3 1",
                                        "4",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "5\n2 7 9 3 1",
                                        "12",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "1\n10",
                                        "10",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "4\n5 5 5 5",
                                        "10",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "6\n10 1 1 10 1 10",
                                        "30",
                                        true
                                )
                        )
                ),

                // ==================================================
                // 4. LONGEST COMMON SUBSEQUENCE
                // ==================================================

                new ProblemSeedData(

                        "Longest Common Subsequence",

                        """
                        Given two strings first and second, find the length of their longest common subsequence.

                        A subsequence is created by deleting zero or more characters from a string without changing the relative order of the remaining characters.

                        The characters of the subsequence do not need to be adjacent in the original string.

                        A common subsequence must appear in both strings.

                        Print the maximum possible length of a common subsequence.
                        """,

                        Difficulty.MEDIUM,

                        "dynamic-programming",

                        """
                        1 <= length of first, second <= 1000

                        Both strings contain only lowercase English letters.
                        """,

                        """
                        The first line contains the string first.

                        The second line contains the string second.
                        """,

                        """
                        Print the length of the longest common subsequence.
                        """,

                        """
                        abcde
                        ace
                        """,

                        "3",

                        List.of(
                                "Dynamic Programming",
                                "String",
                                "Subsequence"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        abcde
                                        ace
                                        """,
                                        "3",
                                        """
                                        The string "ace" appears as a subsequence of both strings.

                                        Its length is 3.

                                        No longer common subsequence exists.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        abc
                                        abc
                                        """,
                                        "3",
                                        """
                                        Both strings are identical.

                                        Therefore, the entire string is the longest common subsequence.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        abc
                                        def
                                        """,
                                        "0",
                                        """
                                        The two strings do not share any character.

                                        Therefore, the longest common subsequence has length 0.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        aggtab
                                        gxtxayb
                                        """,
                                        "4",
                                        """
                                        One longest common subsequence is "gtab".

                                        Its length is 4.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        abcba
                                        abcbcba
                                        """,
                                        "5",
                                        """
                                        The entire string "abcba" appears as a subsequence of the second string.

                                        Therefore, the longest common subsequence has length 5.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "abcde\nace",
                                        "3",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "abc\nabc",
                                        "3",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "abc\ndef",
                                        "0",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "aggtab\ngxtxayb",
                                        "4",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "abcba\nabcbcba",
                                        "5",
                                        true
                                )
                        )
                ),

                // ==================================================
                // 5. EDIT DISTANCE
                // ==================================================

                new ProblemSeedData(

                        "Edit Distance",

                        """
                        Given two strings first and second, find the minimum number of operations required to transform the first string into the second string.

                        You may perform the following operations:

                        Insert one character.

                        Delete one character.

                        Replace one character with another character.

                        Each operation has a cost of 1.

                        Determine the minimum total number of operations needed to make the two strings identical.

                        Print the minimum edit distance.
                        """,

                        Difficulty.HARD,

                        "dynamic-programming",

                        """
                        1 <= length of first, second <= 1000

                        Both strings contain only lowercase English letters.
                        """,

                        """
                        The first line contains the string first.

                        The second line contains the string second.
                        """,

                        """
                        Print the minimum number of operations required to transform the first string into the second string.
                        """,

                        """
                        horse
                        ros
                        """,

                        "3",

                        List.of(
                                "Dynamic Programming",
                                "String",
                                "Edit Distance"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        horse
                                        ros
                                        """,
                                        "3",
                                        """
                                        Replace h with r to obtain "rorse".

                                        Delete the second r to obtain "rose".

                                        Delete e to obtain "ros".

                                        Therefore, the minimum number of operations is 3.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        intention
                                        execution
                                        """,
                                        "5",
                                        """
                                        The first string can be transformed into the second string using five operations.

                                        No sequence using fewer operations can complete the transformation.

                                        Therefore, the edit distance is 5.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        abc
                                        abc
                                        """,
                                        "0",
                                        """
                                        The two strings are already identical.

                                        Therefore, no operation is required.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        cat
                                        cut
                                        """,
                                        "1",
                                        """
                                        Replace the character a with u.

                                        The resulting string becomes "cut".

                                        Therefore, only one operation is required.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        saturday
                                        sunday
                                        """,
                                        "3",
                                        """
                                        The first string can be transformed into "sunday" using three operations.

                                        Therefore, the minimum edit distance is 3.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "horse\nros",
                                        "3",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "intention\nexecution",
                                        "5",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "abc\nabc",
                                        "0",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "cat\ncut",
                                        "1",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "saturday\nsunday",
                                        "3",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "algorithm\naltruistic",
                                        "6",
                                        true
                                )
                        )
                )
        );
    }
}