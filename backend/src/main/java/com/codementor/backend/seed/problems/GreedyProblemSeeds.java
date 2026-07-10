package com.codementor.backend.seed.problems;

import com.codementor.backend.entity.Difficulty;
import com.codementor.backend.seed.ProblemExampleSeedData;
import com.codementor.backend.seed.ProblemSeedData;
import com.codementor.backend.seed.TestCaseSeedData;

import java.util.List;

public final class GreedyProblemSeeds {

    private GreedyProblemSeeds() {
    }

    public static List<ProblemSeedData> getProblems() {

        return List.of(

                // ==================================================
                // 1. ASSIGN COOKIES
                // ==================================================

                new ProblemSeedData(

                        "Assign Cookies",

                        """
                        You are given n children and m cookies.

                        Each child has a greed value representing the minimum cookie size required to satisfy that child.

                        Each cookie has a size.

                        A child can receive at most one cookie, and each cookie can be assigned to at most one child.

                        A child is satisfied if the size of the assigned cookie is greater than or equal to the child's greed value.

                        Determine the maximum number of children that can be satisfied.

                        Print the maximum possible number of satisfied children.
                        """,

                        Difficulty.EASY,

                        "greedy",

                        """
                        1 <= n, m <= 100000

                        1 <= greed[i] <= 1000000000

                        1 <= size[i] <= 1000000000
                        """,

                        """
                        The first line contains two space-separated integers n and m.

                        The second line contains n space-separated integers representing the greed values of the children.

                        The third line contains m space-separated integers representing the sizes of the cookies.
                        """,

                        """
                        Print the maximum number of children that can be satisfied.
                        """,

                        """
                        3 2
                        1 2 3
                        1 1
                        """,

                        "1",

                        List.of(
                                "Greedy",
                                "Sorting",
                                "Two Pointers"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        3 2
                                        1 2 3
                                        1 1
                                        """,
                                        "1",
                                        """
                                        Both cookies have size 1.

                                        Only the child with greed value 1 can be satisfied.

                                        Therefore, the maximum number of satisfied children is 1.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        2 3
                                        1 2
                                        1 2 3
                                        """,
                                        "2",
                                        """
                                        Assign the cookie of size 1 to the child with greed 1.

                                        Assign the cookie of size 2 to the child with greed 2.

                                        Therefore, both children can be satisfied.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        1 1
                                        5
                                        3
                                        """,
                                        "0",
                                        """
                                        The only cookie is smaller than the child's greed value.

                                        Therefore, no child can be satisfied.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        4 4
                                        1 2 3 4
                                        4 3 2 1
                                        """,
                                        "4",
                                        """
                                        After sorting, every child can receive a cookie that satisfies the required greed value.

                                        Therefore, all four children are satisfied.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        5 3
                                        2 2 3 4 5
                                        2 3 5
                                        """,
                                        "3",
                                        """
                                        The three cookies can satisfy three different children.

                                        Since each cookie can be used only once, at most three children can be satisfied.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "3 2\n1 2 3\n1 1",
                                        "1",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "2 3\n1 2\n1 2 3",
                                        "2",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "1 1\n5\n3",
                                        "0",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "4 4\n1 2 3 4\n4 3 2 1",
                                        "4",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "5 3\n2 2 3 4 5\n2 3 5",
                                        "3",
                                        true
                                )
                        )
                ),

                // ==================================================
                // 2. MAXIMUM NUMBER OF ACTIVITIES
                // ==================================================

                new ProblemSeedData(

                        "Maximum Number of Activities",

                        """
                        You are given n activities.

                        Each activity has a start time and a finish time.

                        Only one activity can be performed at a time.

                        An activity can be selected if its start time is greater than or equal to the finish time of the previously selected activity.

                        Determine the maximum number of non-overlapping activities that can be selected.

                        Print the maximum possible number of activities.

                        A greedy strategy can select activities according to increasing finish time.
                        """,

                        Difficulty.EASY,

                        "greedy",

                        """
                        1 <= n <= 100000

                        0 <= start[i] < finish[i] <= 1000000000
                        """,

                        """
                        The first line contains an integer n.

                        The next n lines each contain two space-separated integers start and finish representing one activity.
                        """,

                        """
                        Print the maximum number of non-overlapping activities that can be selected.
                        """,

                        """
                        6
                        1 2
                        3 4
                        0 6
                        5 7
                        8 9
                        5 9
                        """,

                        "4",

                        List.of(
                                "Greedy",
                                "Sorting",
                                "Intervals"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        6
                                        1 2
                                        3 4
                                        0 6
                                        5 7
                                        8 9
                                        5 9
                                        """,
                                        "4",
                                        """
                                        Select activities 1-2, 3-4, 5-7, and 8-9.

                                        These activities do not overlap.

                                        Therefore, the maximum number of selected activities is 4.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        3
                                        1 2
                                        2 3
                                        3 4
                                        """,
                                        "3",
                                        """
                                        Each activity starts when the previous activity finishes.

                                        Therefore, all three activities can be selected.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        3
                                        1 10
                                        2 9
                                        3 8
                                        """,
                                        "1",
                                        """
                                        Every activity overlaps with the others.

                                        Therefore, only one activity can be selected.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        1
                                        5 10
                                        """,
                                        "1",
                                        """
                                        Only one activity exists.

                                        Therefore, it can always be selected.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        5
                                        4 6
                                        1 2
                                        2 4
                                        6 8
                                        8 10
                                        """,
                                        "5",
                                        """
                                        After arranging the activities by finish time, all five can be selected without overlap.

                                        Therefore, the answer is 5.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "6\n1 2\n3 4\n0 6\n5 7\n8 9\n5 9",
                                        "4",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "3\n1 2\n2 3\n3 4",
                                        "3",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "3\n1 10\n2 9\n3 8",
                                        "1",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "1\n5 10",
                                        "1",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "5\n4 6\n1 2\n2 4\n6 8\n8 10",
                                        "5",
                                        true
                                )
                        )
                ),

                // ==================================================
                // 3. JUMP GAME
                // ==================================================

                new ProblemSeedData(

                        "Jump Game",

                        """
                        You are given an array containing n non-negative integers.

                        You begin at index 0.

                        The value at each index represents the maximum number of positions you may jump forward from that index.

                        Determine whether it is possible to reach the final index.

                        You do not need to use the maximum jump distance every time. You may jump any positive distance up to the allowed maximum.

                        Print true if the final index can be reached.

                        Otherwise, print false.
                        """,

                        Difficulty.MEDIUM,

                        "greedy",

                        """
                        1 <= n <= 100000

                        0 <= array[i] <= 100000
                        """,

                        """
                        The first line contains an integer n.

                        The second line contains n space-separated integers representing the array.
                        """,

                        """
                        Print true if the final index can be reached.

                        Otherwise, print false.
                        """,

                        """
                        5
                        2 3 1 1 4
                        """,

                        "true",

                        List.of(
                                "Greedy",
                                "Array"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        5
                                        2 3 1 1 4
                                        """,
                                        "true",
                                        """
                                        From index 0, jump to index 1.

                                        The value at index 1 allows a jump directly to the final index.

                                        Therefore, the answer is true.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        5
                                        3 2 1 0 4
                                        """,
                                        "false",
                                        """
                                        Every possible path reaches index 3, whose value is 0.

                                        The final index cannot be reached.

                                        Therefore, the answer is false.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        1
                                        0
                                        """,
                                        "true",
                                        """
                                        The starting index is already the final index.

                                        Therefore, the answer is true.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        2
                                        1 0
                                        """,
                                        "true",
                                        """
                                        The first value allows a jump of one position.

                                        Therefore, the final index can be reached.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        6
                                        2 0 2 0 1 0
                                        """,
                                        "true",
                                        """
                                        Jump from index 0 to index 2.

                                        Then jump to index 4 and finally to index 5.

                                        Therefore, the final index is reachable.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "5\n2 3 1 1 4",
                                        "true",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "5\n3 2 1 0 4",
                                        "false",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "1\n0",
                                        "true",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "2\n1 0",
                                        "true",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "6\n2 0 2 0 1 0",
                                        "true",
                                        true
                                )
                        )
                ),

                // ==================================================
                // 4. GAS STATION
                // ==================================================

                new ProblemSeedData(

                        "Gas Station",

                        """
                        There are n gas stations arranged in a circular route.

                        The amount of fuel available at station i is gas[i].

                        The amount of fuel required to travel from station i to the next station is cost[i].

                        You begin the journey with an empty fuel tank.

                        Find the index of a starting gas station from which it is possible to travel around the entire circular route exactly once.

                        If no valid starting station exists, print -1.

                        If a valid solution exists, the answer is guaranteed to be unique.
                        """,

                        Difficulty.MEDIUM,

                        "greedy",

                        """
                        1 <= n <= 100000

                        0 <= gas[i], cost[i] <= 100000

                        If a valid starting station exists, it is unique.
                        """,

                        """
                        The first line contains an integer n.

                        The second line contains n space-separated integers representing gas.

                        The third line contains n space-separated integers representing cost.
                        """,

                        """
                        Print the zero-based index of the valid starting station.

                        If completing the route is impossible, print -1.
                        """,

                        """
                        5
                        1 2 3 4 5
                        3 4 5 1 2
                        """,

                        "3",

                        List.of(
                                "Greedy",
                                "Array"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        5
                                        1 2 3 4 5
                                        3 4 5 1 2
                                        """,
                                        "3",
                                        """
                                        Starting from station 3 provides enough fuel to continue through stations 4, 0, 1, and 2.

                                        The entire circular route can be completed.

                                        Therefore, the answer is index 3.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        3
                                        2 3 4
                                        3 4 3
                                        """,
                                        "-1",
                                        """
                                        The total available fuel is smaller than the total travel cost.

                                        Therefore, completing the circular route is impossible.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        1
                                        5
                                        4
                                        """,
                                        "0",
                                        """
                                        The only station provides enough fuel to complete the route.

                                        Therefore, the valid starting index is 0.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        4
                                        4 1 2 3
                                        2 2 2 4
                                        """,
                                        "0",
                                        """
                                        Starting from station 0 provides enough accumulated fuel to complete the circular route.

                                        Therefore, the answer is 0.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        5
                                        5 1 1 1 1
                                        1 2 2 2 2
                                        """,
                                        "0",
                                        """
                                        Starting from station 0 creates enough initial surplus to complete every remaining segment.

                                        Therefore, the valid starting index is 0.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "5\n1 2 3 4 5\n3 4 5 1 2",
                                        "3",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "3\n2 3 4\n3 4 3",
                                        "-1",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "1\n5\n4",
                                        "0",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "4\n4 1 2 3\n2 2 2 4",
                                        "0",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "5\n5 1 1 1 1\n1 2 2 2 2",
                                        "0",
                                        true
                                )
                        )
                ),

                // ==================================================
                // 5. CANDY DISTRIBUTION
                // ==================================================

                new ProblemSeedData(

                        "Candy Distribution",

                        """
                        There are n children standing in a line.

                        Each child has a rating.

                        You must distribute candies according to the following rules:

                        Every child must receive at least one candy.

                        A child with a higher rating than an adjacent child must receive more candies than that adjacent child.

                        Determine the minimum total number of candies required to satisfy all conditions.

                        Print the minimum possible total number of candies.
                        """,

                        Difficulty.HARD,

                        "greedy",

                        """
                        1 <= n <= 100000

                        0 <= rating[i] <= 1000000000
                        """,

                        """
                        The first line contains an integer n.

                        The second line contains n space-separated integers representing the ratings.
                        """,

                        """
                        Print the minimum total number of candies required.
                        """,

                        """
                        3
                        1 0 2
                        """,

                        "5",

                        List.of(
                                "Greedy",
                                "Array",
                                "Two Pass"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        3
                                        1 0 2
                                        """,
                                        "5",
                                        """
                                        One minimum valid distribution is 2, 1, and 2 candies.

                                        The first child has a higher rating than the second child and receives more candies.

                                        The third child also has a higher rating than the second child and receives more candies.

                                        Therefore, the minimum total is 5.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        3
                                        1 2 2
                                        """,
                                        "4",
                                        """
                                        One minimum valid distribution is 1, 2, and 1 candies.

                                        Equal ratings do not require equal candy counts.

                                        Therefore, the minimum total is 4.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        1
                                        10
                                        """,
                                        "1",
                                        """
                                        Only one child exists.

                                        Every child must receive at least one candy.

                                        Therefore, the answer is 1.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        5
                                        1 2 3 4 5
                                        """,
                                        "15",
                                        """
                                        The ratings are strictly increasing.

                                        The minimum valid candy distribution is 1, 2, 3, 4, and 5.

                                        Their total is 15.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        5
                                        5 4 3 2 1
                                        """,
                                        "15",
                                        """
                                        The ratings are strictly decreasing.

                                        The minimum valid candy distribution is 5, 4, 3, 2, and 1.

                                        Their total is 15.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "3\n1 0 2",
                                        "5",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "3\n1 2 2",
                                        "4",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "1\n10",
                                        "1",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "5\n1 2 3 4 5",
                                        "15",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "5\n5 4 3 2 1",
                                        "15",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "5\n1 3 2 2 1",
                                        "7",
                                        true
                                )
                        )
                )
        );
    }
}