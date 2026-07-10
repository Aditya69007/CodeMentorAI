package com.codementor.backend.seed.problems;

import com.codementor.backend.entity.Difficulty;
import com.codementor.backend.seed.ProblemExampleSeedData;
import com.codementor.backend.seed.ProblemSeedData;
import com.codementor.backend.seed.TestCaseSeedData;

import java.util.List;

public final class RecursionProblemSeeds {

    private RecursionProblemSeeds() {
    }

    public static List<ProblemSeedData> getProblems() {

        return List.of(

                // ==================================================
                // SUM OF DIGITS
                // ==================================================

                new ProblemSeedData(

                        "Sum of Digits",

                        """
                        Given a non-negative integer n, find the sum of all digits in the number.

                        Solve the problem using recursion.

                        For example, the number 582 contains the digits 5, 8, and 2.

                        Their sum is 5 + 8 + 2 = 15.

                        Therefore, the answer is 15.
                        """,

                        Difficulty.EASY,

                        "recursion",

                        """
                        0 <= n <= 1000000000000000000
                        """,

                        """
                        A single non-negative integer n.
                        """,

                        """
                        Print a single integer representing the sum of all digits of n.
                        """,

                        "582",

                        "15",

                        List.of(
                                "Recursion",
                                "Math"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        "582",
                                        "15",
                                        """
                                        The digits are 5, 8, and 2.

                                        Their sum is 5 + 8 + 2 = 15.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        "0",
                                        "0",
                                        """
                                        The number contains only the digit 0.

                                        Therefore, the digit sum is 0.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        "12345",
                                        "15",
                                        """
                                        Adding the digits gives 1 + 2 + 3 + 4 + 5 = 15.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        "9999",
                                        "36",
                                        """
                                        Each of the four digits is 9.

                                        Therefore, the sum is 9 + 9 + 9 + 9 = 36.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        "1000001",
                                        "2",
                                        """
                                        Only the first and final digits contribute 1 to the sum.

                                        All remaining digits are zero.

                                        Therefore, the answer is 2.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "582",
                                        "15",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "0",
                                        "0",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "12345",
                                        "15",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "9999",
                                        "36",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "1000001",
                                        "2",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "9876543210",
                                        "45",
                                        true
                                )
                        )
                ),

                // ==================================================
                // REVERSE A STRING RECURSIVELY
                // ==================================================

                new ProblemSeedData(

                        "Reverse a String Recursively",

                        """
                        Given a string s containing lowercase English letters, reverse the string.

                        Solve the problem using recursion.

                        The first character of the original string must become the final character of the reversed string, the second character must become the second-last character, and so on.

                        For example, reversing "hello" produces "olleh".
                        """,

                        Difficulty.EASY,

                        "recursion",

                        """
                        1 <= length of s <= 100000

                        s contains only lowercase English letters.
                        """,

                        """
                        A single string s containing lowercase English letters.
                        """,

                        """
                        Print the reversed string.
                        """,

                        "hello",

                        "olleh",

                        List.of(
                                "Recursion",
                                "String"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        "hello",
                                        "olleh",
                                        """
                                        Reading the characters from the end of "hello" to the beginning produces "olleh".
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        "a",
                                        "a",
                                        """
                                        A single-character string remains unchanged when reversed.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        "abcdef",
                                        "fedcba",
                                        """
                                        Reversing the character order produces f, e, d, c, b, and a.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        "level",
                                        "level",
                                        """
                                        The string is a palindrome.

                                        Therefore, reversing it produces the same string.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        "coding",
                                        "gnidoc",
                                        """
                                        Reading the original characters from right to left produces "gnidoc".
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "hello",
                                        "olleh",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "a",
                                        "a",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "abcdef",
                                        "fedcba",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "level",
                                        "level",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "coding",
                                        "gnidoc",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "recursion",
                                        "noisrucer",
                                        true
                                )
                        )
                ),

                // ==================================================
                // GENERATE ALL SUBSEQUENCES
                // ==================================================

                new ProblemSeedData(

                        "Generate All Subsequences",

                        """
                        Given a string s containing distinct lowercase English letters, generate all non-empty subsequences of the string.

                        A subsequence is created by removing zero or more characters while preserving the relative order of the remaining characters.

                        Two characters cannot exchange their original positions.

                        Print all non-empty subsequences in lexicographical order.

                        First print the total number of non-empty subsequences.

                        Then print each subsequence on a separate line.

                        For example, the non-empty subsequences of "ab" are "a", "ab", and "b".
                        """,

                        Difficulty.MEDIUM,

                        "recursion",

                        """
                        1 <= length of s <= 15

                        s contains distinct lowercase English letters.
                        """,

                        """
                        A single string s containing distinct lowercase English letters.
                        """,

                        """
                        Print the total number of non-empty subsequences on the first line.

                        Then print every subsequence on a separate line in lexicographical order.
                        """,

                        "ab",

                        """
                        3
                        a
                        ab
                        b
                        """,

                        List.of(
                                "Recursion",
                                "Backtracking",
                                "String"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        "ab",
                                        """
                                        3
                                        a
                                        ab
                                        b
                                        """,
                                        """
                                        The subsequences are formed by choosing 'a', choosing both 'a' and 'b', or choosing only 'b'.

                                        Therefore, there are three non-empty subsequences.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        "a",
                                        """
                                        1
                                        a
                                        """,
                                        """
                                        A single-character string has exactly one non-empty subsequence.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        "abc",
                                        """
                                        7
                                        a
                                        ab
                                        abc
                                        ac
                                        b
                                        bc
                                        c
                                        """,
                                        """
                                        A string of three distinct characters has 2^3 - 1 = 7 non-empty subsequences.

                                        After generation, they are printed in lexicographical order.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        "ba",
                                        """
                                        3
                                        a
                                        b
                                        ba
                                        """,
                                        """
                                        The valid subsequences are "b", "ba", and "a".

                                        Sorting them lexicographically produces "a", "b", and "ba".
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        "ac",
                                        """
                                        3
                                        a
                                        ac
                                        c
                                        """,
                                        """
                                        The three non-empty subsequences are "a", "ac", and "c".
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "ab",
                                        "3\na\nab\nb",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "a",
                                        "1\na",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "abc",
                                        "7\na\nab\nabc\nac\nb\nbc\nc",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "ba",
                                        "3\na\nb\nba",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "ac",
                                        "3\na\nac\nc",
                                        true
                                )
                        )
                ),

                // ==================================================
                // JOSEPHUS PROBLEM
                // ==================================================

                new ProblemSeedData(

                        "Josephus Problem",

                        """
                        There are n people standing in a circle and numbered from 1 to n.

                        Starting from person 1, count k people around the circle.

                        The kth person is removed.

                        Counting then continues from the next remaining person, and the process repeats until only one person remains.

                        Find and print the number of the surviving person.

                        For example, when n = 7 and k = 3, the elimination process eventually leaves person 4 as the survivor.
                        """,

                        Difficulty.MEDIUM,

                        "recursion",

                        """
                        1 <= n <= 100000

                        1 <= k <= 1000000000
                        """,

                        """
                        The first line contains two space-separated integers n and k.
                        """,

                        """
                        Print the number of the final surviving person.
                        """,

                        "7 3",

                        "4",

                        List.of(
                                "Recursion",
                                "Math",
                                "Simulation"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        "7 3",
                                        "4",
                                        """
                                        Starting with seven people and removing every third remaining person eventually leaves person 4.

                                        Therefore, the answer is 4.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        "1 5",
                                        "1",
                                        """
                                        Only one person is present.

                                        Therefore, person 1 survives regardless of k.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        "5 2",
                                        "3",
                                        """
                                        Removing every second person eventually leaves person 3 as the survivor.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        "6 1",
                                        "6",
                                        """
                                        When k equals 1, each current person is removed immediately.

                                        The final remaining person is person 6.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        "10 3",
                                        "4",
                                        """
                                        Applying the repeated circular elimination process leaves person 4.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "7 3",
                                        "4",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "1 5",
                                        "1",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "5 2",
                                        "3",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "6 1",
                                        "6",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "10 3",
                                        "4",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "8 2",
                                        "1",
                                        true
                                )
                        )
                ),

                // ==================================================
                // TOWER OF HANOI
                // ==================================================

                new ProblemSeedData(

                        "Tower of Hanoi",

                        """
                        The Tower of Hanoi puzzle contains three rods named A, B, and C and n disks of different sizes.

                        Initially, all disks are placed on rod A in decreasing order of size, with the largest disk at the bottom.

                        Move the complete stack from rod A to rod C using rod B as auxiliary storage.

                        You must follow these rules:

                        Only one disk can be moved at a time.

                        Only the top disk of a rod can be moved.

                        A larger disk can never be placed on top of a smaller disk.

                        Print the minimum number of moves required to solve the puzzle.

                        Then print every move in the correct order using the format:

                        A C

                        This means move the top disk from rod A to rod C.
                        """,

                        Difficulty.HARD,

                        "recursion",

                        """
                        1 <= n <= 20
                        """,

                        """
                        A single integer n representing the number of disks.
                        """,

                        """
                        Print the minimum number of moves on the first line.

                        Then print every required move on a separate line.

                        Each move contains the source rod and destination rod separated by one space.
                        """,

                        "2",

                        """
                        3
                        A B
                        A C
                        B C
                        """,

                        List.of(
                                "Recursion",
                                "Divide and Conquer"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        "2",
                                        """
                                        3
                                        A B
                                        A C
                                        B C
                                        """,
                                        """
                                        First move the smaller disk from A to B.

                                        Then move the larger disk from A to C.

                                        Finally, move the smaller disk from B to C.

                                        Therefore, three moves are required.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        "1",
                                        """
                                        1
                                        A C
                                        """,
                                        """
                                        With one disk, move it directly from rod A to rod C.

                                        Therefore, only one move is required.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        "3",
                                        """
                                        7
                                        A C
                                        A B
                                        C B
                                        A C
                                        B A
                                        B C
                                        A C
                                        """,
                                        """
                                        Moving three disks requires moving the top two disks to B, moving the largest disk to C, and then moving the two disks from B to C.

                                        The minimum number of moves is 7.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        "4",
                                        """
                                        15
                                        A B
                                        A C
                                        B C
                                        A B
                                        C A
                                        C B
                                        A B
                                        A C
                                        B C
                                        B A
                                        C A
                                        B C
                                        A B
                                        A C
                                        B C
                                        """,
                                        """
                                        The minimum number of moves for four disks is 2^4 - 1 = 15.

                                        The listed recursive sequence moves every disk from A to C without placing a larger disk above a smaller disk.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        "5",
                                        """
                                        31
                                        A C
                                        A B
                                        C B
                                        A C
                                        B A
                                        B C
                                        A C
                                        A B
                                        C B
                                        C A
                                        B A
                                        C B
                                        A C
                                        A B
                                        C B
                                        A C
                                        B A
                                        B C
                                        A C
                                        B A
                                        C B
                                        C A
                                        B A
                                        B C
                                        A C
                                        A B
                                        C B
                                        A C
                                        B A
                                        B C
                                        A C
                                        """,
                                        """
                                        The minimum number of moves for five disks is 2^5 - 1 = 31.

                                        The recursive process first moves four disks to the auxiliary rod, moves the largest disk to the destination, and then moves the four smaller disks onto it.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "2",
                                        "3\nA B\nA C\nB C",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "1",
                                        "1\nA C",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "3",
                                        "7\nA C\nA B\nC B\nA C\nB A\nB C\nA C",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "4",
                                        "15\nA B\nA C\nB C\nA B\nC A\nC B\nA B\nA C\nB C\nB A\nC A\nB C\nA B\nA C\nB C",
                                        true
                                )
                        )
                )
        );
    }
}