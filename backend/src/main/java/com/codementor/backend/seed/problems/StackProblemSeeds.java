package com.codementor.backend.seed.problems;

import com.codementor.backend.entity.Difficulty;
import com.codementor.backend.seed.ProblemExampleSeedData;
import com.codementor.backend.seed.ProblemSeedData;
import com.codementor.backend.seed.TestCaseSeedData;

import java.util.List;

public final class StackProblemSeeds {

    private StackProblemSeeds() {
    }

    public static List<ProblemSeedData> getProblems() {

        return List.of(

                // ==================================================
                // 1. VALID PARENTHESES
                // ==================================================

                new ProblemSeedData(

                        "Valid Parentheses",

                        """
                        Given a string s containing only bracket characters,determine whether the bracket sequence is valid. A bracket sequence is considered valid when all of the following conditions are satisfied:

                        1. Every opening bracket must be closed by the same type of closing bracket.
                        2. Open brackets must be closed in the correct order.
                        3. Every closing bracket must have a corresponding opening bracket before it.
                        Return true when the complete bracket sequence is valid. Otherwise, return false.
                        """,

                        Difficulty.EASY,

                        "stack",

                        """
                        1 <= length of s <= 10000

                        s contains only the characters:
                        '(', ')', '{', '}', '[' and ']'.
                        """,

                        """
                        A single string s containing only bracket characters.
                        """,

                        """
                        Print true if the bracket sequence is valid. Otherwise, print false.
                        """,

                        "()[]{}",

                        "true",

                        List.of(
                                "Stack",
                                "String"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        "()",
                                        "true",
                                        """
                                        The opening parenthesis is correctly matched with the closing parenthesis.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        "()[]{}",
                                        "true",
                                        """
                                        Every opening bracket has a matching closing bracket, and all brackets are closed in the correct order.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        "(]",
                                        "false",
                                        """
                                        The opening parenthesis '(' is closed using ']', which is a different bracket type.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        "{[]}",
                                        "true",
                                        """
                                        The brackets are correctly nested and closed in the proper order.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        "([)]",
                                        "false",
                                        """
                                        The brackets are closed in the wrong order.
                                        """,
                                        5
                                )
                        ),

                        List.of(

                                new TestCaseSeedData(
                                        "()",
                                        "true",
                                        false
                                ),

                                new TestCaseSeedData(
                                        "()[]{}",
                                        "true",
                                        false
                                ),

                                new TestCaseSeedData(
                                        "(]",
                                        "false",
                                        true
                                ),

                                new TestCaseSeedData(
                                        "([)]",
                                        "false",
                                        true
                                ),

                                new TestCaseSeedData(
                                        "{[]}",
                                        "true",
                                        true
                                ),

                                new TestCaseSeedData(
                                        "]",
                                        "false",
                                        true
                                ),

                                new TestCaseSeedData(
                                        "((()))",
                                        "true",
                                        true
                                )
                        )
                ),


                // ==================================================
                // 2. REMOVE ADJACENT DUPLICATES
                // ==================================================

                new ProblemSeedData(

                        "Remove Adjacent Duplicates",

                        """
                        Given a string s containing lowercase English letters, repeatedly remove two adjacent characters when they are equal.
                        Removing one pair may cause a new pair of equal adjacent characters to appear. Therefore, the process must continue until no adjacent duplicate pair remains.
                        Print the final string after all possible removals have been performed.
                        The order in which valid duplicate pairs are removed does not change the final result.

                        If every character is removed, print an empty string.
                        """,

                        Difficulty.EASY,

                        "stack",

                        """
                        1 <= length of s <= 100000

                        s contains only lowercase English letters.
                        """,

                        """
                        A single string s containing lowercase English letters.
                        """,

                        """
                        Print the final string after repeatedly removing all adjacent duplicate pairs.
                        """,

                        "abbaca",

                        "ca",

                        List.of(
                                "Stack",
                                "String"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        "abbaca",
                                        "ca",
                                        """
                                        First remove "bb" from "abbaca" to obtain "aaca".
                                        The two 'a' characters are now adjacent, so remove "aa" to obtain "ca". No adjacent duplicate characters remain.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        "azxxzy",
                                        "ay",
                                        """
                                        Remove "xx" to obtain "azzy". The two 'z' characters then become adjacent. Remove "zz" to obtain "ay".
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        "aaaaaaaa",
                                        "",
                                        """
                                        Every character can be removed as part of an adjacent duplicate pair, so the final string is empty.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        "abc",
                                        "abc",
                                        """
                                        There are no equal adjacent characters, so the original string remains unchanged.
                                        """,
                                        4
                                )
                        ),

                        List.of(

                                new TestCaseSeedData(
                                        "abbaca",
                                        "ca",
                                        false
                                ),

                                new TestCaseSeedData(
                                        "azxxzy",
                                        "ay",
                                        false
                                ),

                                new TestCaseSeedData(
                                        "aaaaaaaa",
                                        "",
                                        true
                                ),

                                new TestCaseSeedData(
                                        "abc",
                                        "abc",
                                        true
                                ),

                                new TestCaseSeedData(
                                        "aababaab",
                                        "ba",
                                        true
                                ),

                                new TestCaseSeedData(
                                        "aa",
                                        "",
                                        true
                                ),

                                new TestCaseSeedData(
                                        "a",
                                        "a",
                                        true
                                )
                        )
                ),


                // ==================================================
                // 3. NEXT GREATER ELEMENT
                // ==================================================

                new ProblemSeedData(

                        "Next Greater Element",

                        """
                        Given an array of n integers, find the next greater element for every position in the array.
                        The next greater element of an element is the first element appearing to its right that has a strictly greater value.
                        If no greater element exists to the right of a position, the answer for that position is -1.
                        Print the next greater element for every array position in the same order as the input.

                        A solution that checks every pair of elements may be too slow for large inputs. Consider how previously visited elements can be efficiently tracked.
                        """,

                        Difficulty.MEDIUM,

                        "stack",

                        """
                        1 <= n <= 100000

                        -1000000000 <= array[i] <= 1000000000
                        """,

                        """
                        The first line contains an integer n representing the number of elements.
                        The second line contains n space-separated integers.
                        """,

                        """
                        Print n space-separated integers.
                        The value at each position must be the first greater element appearing to the right of the corresponding input element.
                        Print -1 when no greater element exists.
                        """,

                        """
                        4
                        4 5 2 10
                        """,

                        "5 10 10 -1",

                        List.of(
                                "Stack",
                                "Monotonic Stack",
                                "Array"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        4
                                        4 5 2 10
                                        """,
                                        "5 10 10 -1",
                                        """
                                        For 4, the first greater element is 5.
                                        For 5, the first greater element is 10.
                                        For 2, the first greater element is 10.
                                        There is no greater element to the right of 10, so its answer is -1.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        3
                                        3 2 1
                                        """,
                                        "-1 -1 -1",
                                        """
                                        The array is strictly decreasing. Therefore, no element has a greater value to its right.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        5
                                        1 3 2 4 5
                                        """,
                                        "3 4 4 5 -1",
                                        """
                                        The first greater values are:

                                        1 -> 3
                                        3 -> 4
                                        2 -> 4
                                        4 -> 5
                                        5 -> -1
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        4
                                        5 5 5 5
                                        """,
                                        "-1 -1 -1 -1",
                                        """
                                        The next greater element must be strictly greater. Equal values do not count.
                                        """,
                                        4
                                )
                        ),

                        List.of(

                                new TestCaseSeedData(
                                        """
                                        4
                                        4 5 2 10
                                        """,
                                        "5 10 10 -1",
                                        false
                                ),

                                new TestCaseSeedData(
                                        """
                                        3
                                        3 2 1
                                        """,
                                        "-1 -1 -1",
                                        false
                                ),

                                new TestCaseSeedData(
                                        """
                                        5
                                        1 3 2 4 5
                                        """,
                                        "3 4 4 5 -1",
                                        true
                                ),

                                new TestCaseSeedData(
                                        """
                                        4
                                        5 5 5 5
                                        """,
                                        "-1 -1 -1 -1",
                                        true
                                ),

                                new TestCaseSeedData(
                                        """
                                        1
                                        100
                                        """,
                                        "-1",
                                        true
                                ),

                                new TestCaseSeedData(
                                        """
                                        5
                                        -5 -3 -4 -1 -2
                                        """,
                                        "-3 -1 -1 -1 -1",
                                        true
                                )
                        )
                )
        );
    }
}