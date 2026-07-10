package com.codementor.backend.seed.problems;

import com.codementor.backend.entity.Difficulty;
import com.codementor.backend.seed.ProblemExampleSeedData;
import com.codementor.backend.seed.ProblemSeedData;
import com.codementor.backend.seed.TestCaseSeedData;

import java.util.List;

public final class SlidingWindowProblemSeeds {

    private SlidingWindowProblemSeeds() {
    }

    public static List<ProblemSeedData> getProblems() {

        return List.of(

                // ==================================================
                // MAXIMUM SUM SUBARRAY OF SIZE K
                // ==================================================

                new ProblemSeedData(

                        "Maximum Sum Subarray of Size K",

                        """
                        Given an integer array containing n elements and an integer k, find the maximum sum among all contiguous subarrays containing exactly k elements.

                        A subarray is a continuous sequence of elements from the original array.

                        The selected subarray must contain exactly k elements.

                        For example, in the array [2, 1, 5, 1, 3, 2] with k = 3, the contiguous subarrays of size 3 have sums 8, 7, 9, and 6.

                        Therefore, the maximum possible sum is 9.
                        """,

                        Difficulty.EASY,

                        "sliding-window",

                        """
                        1 <= n <= 100000

                        1 <= k <= n

                        -1000000000 <= array[i] <= 1000000000

                        The answer fits within a signed 64-bit integer.
                        """,

                        """
                        The first line contains two space-separated integers n and k.

                        The second line contains n space-separated integers.
                        """,

                        """
                        Print a single integer representing the maximum sum among all contiguous subarrays of exactly k elements.
                        """,

                        """
                        6 3
                        2 1 5 1 3 2
                        """,

                        "9",

                        List.of(
                                "Sliding Window",
                                "Array"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        6 3
                                        2 1 5 1 3 2
                                        """,
                                        "9",
                                        """
                                        The contiguous subarrays of size 3 are [2, 1, 5], [1, 5, 1], [5, 1, 3], and [1, 3, 2].

                                        Their sums are 8, 7, 9, and 6.

                                        Therefore, the maximum sum is 9.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        5 2
                                        4 2 1 7 8
                                        """,
                                        "15",
                                        """
                                        The contiguous pairs have sums 6, 3, 8, and 15.

                                        The subarray [7, 8] has the maximum sum of 15.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        4 4
                                        1 2 3 4
                                        """,
                                        "10",
                                        """
                                        Since k equals n, only the complete array can be selected.

                                        Its sum is 10.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        5 1
                                        -5 -2 -8 -1 -4
                                        """,
                                        "-1",
                                        """
                                        Since k is 1, every single element forms a valid subarray.

                                        The largest value is -1, so the maximum sum is -1.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        6 3
                                        -4 -2 -7 -1 -3 -5
                                        """,
                                        "-9",
                                        """
                                        The window sums are -13, -10, -11, and -9.

                                        The maximum among these values is -9.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "6 3\n2 1 5 1 3 2",
                                        "9",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "5 2\n4 2 1 7 8",
                                        "15",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "4 4\n1 2 3 4",
                                        "10",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "5 1\n-5 -2 -8 -1 -4",
                                        "-1",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "6 3\n-4 -2 -7 -1 -3 -5",
                                        "-9",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "7 2\n10 -5 8 12 -3 7 4",
                                        "20",
                                        true
                                )
                        )
                ),

                // ==================================================
                // MINIMUM SIZE SUBARRAY SUM
                // ==================================================

                new ProblemSeedData(

                        "Minimum Size Subarray Sum",

                        """
                        Given an array containing n positive integers and a positive integer target, find the minimum length of a contiguous subarray whose sum is greater than or equal to target.

                        A subarray is a continuous sequence of elements from the original array.

                        If no contiguous subarray has a sum greater than or equal to target, print 0.

                        For example, for target = 7 and array [2, 3, 1, 2, 4, 3], the subarray [4, 3] has sum 7 and length 2.

                        No valid subarray of length 1 exists, so the answer is 2.
                        """,

                        Difficulty.MEDIUM,

                        "sliding-window",

                        """
                        1 <= n <= 100000

                        1 <= target <= 100000000000000

                        1 <= array[i] <= 1000000000

                        The sum of all array elements fits within a signed 64-bit integer.
                        """,

                        """
                        The first line contains two space-separated integers n and target.

                        The second line contains n space-separated positive integers.
                        """,

                        """
                        Print the minimum length of a contiguous subarray whose sum is greater than or equal to target.

                        If no such subarray exists, print 0.
                        """,

                        """
                        6 7
                        2 3 1 2 4 3
                        """,

                        "2",

                        List.of(
                                "Sliding Window",
                                "Array",
                                "Two Pointers"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        6 7
                                        2 3 1 2 4 3
                                        """,
                                        "2",
                                        """
                                        The subarray [4, 3] has sum 7 and length 2.

                                        No single element has a value greater than or equal to 7.

                                        Therefore, the minimum valid length is 2.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        1 4
                                        4
                                        """,
                                        "1",
                                        """
                                        The only element has value 4, which is equal to the target.

                                        Therefore, the minimum valid length is 1.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        5 11
                                        1 1 1 1 1
                                        """,
                                        "0",
                                        """
                                        Even the sum of the complete array is only 5.

                                        Therefore, no valid subarray exists and the answer is 0.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        5 10
                                        1 2 10 3 4
                                        """,
                                        "1",
                                        """
                                        The single element 10 is already equal to the target.

                                        Therefore, a valid subarray of length 1 exists.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        8 15
                                        1 2 3 4 5 6 7 8
                                        """,
                                        "2",
                                        """
                                        The subarray [7, 8] has sum 15 and length 2.

                                        No single value reaches the target, so the minimum length is 2.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "6 7\n2 3 1 2 4 3",
                                        "2",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "1 4\n4",
                                        "1",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "5 11\n1 1 1 1 1",
                                        "0",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "5 10\n1 2 10 3 4",
                                        "1",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "8 15\n1 2 3 4 5 6 7 8",
                                        "2",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "6 20\n5 1 3 5 10 7",
                                        "3",
                                        true
                                )
                        )
                ),

                // ==================================================
                // PERMUTATION IN STRING
                // ==================================================

                new ProblemSeedData(

                        "Permutation in String",

                        """
                        Given two lowercase strings pattern and text, determine whether text contains a contiguous substring that is a permutation of pattern.

                        A permutation contains exactly the same characters with exactly the same frequencies, but the characters may appear in a different order.

                        The matching characters must form one continuous substring inside text.

                        For example, pattern "ab" has the permutations "ab" and "ba".

                        The string "eidbaooo" contains "ba", so the answer is true.

                        Print true if such a substring exists. Otherwise, print false.
                        """,

                        Difficulty.MEDIUM,

                        "sliding-window",

                        """
                        1 <= length of pattern, text <= 100000

                        pattern and text contain only lowercase English letters.
                        """,

                        """
                        The first line contains the string pattern.

                        The second line contains the string text.
                        """,

                        """
                        Print true if text contains a contiguous substring that is a permutation of pattern.

                        Otherwise, print false.
                        """,

                        """
                        ab
                        eidbaooo
                        """,

                        "true",

                        List.of(
                                "Sliding Window",
                                "String",
                                "Hashing",
                                "Frequency Counting"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        ab
                                        eidbaooo
                                        """,
                                        "true",
                                        """
                                        The text contains the substring "ba".

                                        It contains exactly one 'a' and one 'b', which matches the character frequencies of pattern "ab".

                                        Therefore, the answer is true.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        ab
                                        eidboaoo
                                        """,
                                        "false",
                                        """
                                        No substring of length 2 contains exactly one 'a' and one 'b'.

                                        Therefore, no permutation of "ab" exists in the text.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        adc
                                        dcda
                                        """,
                                        "true",
                                        """
                                        The substring "cda" contains one 'a', one 'c', and one 'd'.

                                        Therefore, it is a permutation of "adc".
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        a
                                        a
                                        """,
                                        "true",
                                        """
                                        The complete text is identical to the pattern.

                                        Therefore, it is also a valid permutation.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        hello
                                        world
                                        """,
                                        "false",
                                        """
                                        The text does not contain any substring with the same character frequencies as "hello".

                                        Therefore, the answer is false.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "ab\neidbaooo",
                                        "true",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "ab\neidboaoo",
                                        "false",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "adc\ndcda",
                                        "true",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "a\na",
                                        "true",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "hello\nworld",
                                        "false",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "abc\nccccbbbbaaaa",
                                        "false",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "abc\nbbbca",
                                        "true",
                                        true
                                )
                        )
                ),

                // ==================================================
                // MINIMUM WINDOW SUBSTRING
                // ==================================================

                new ProblemSeedData(

                        "Minimum Window Substring",

                        """
                        Given two strings text and pattern, find the shortest contiguous substring of text that contains every character from pattern with at least the required frequency.

                        If a character appears multiple times in pattern, the selected window must contain that character at least the same number of times.

                        If no valid window exists, print -1.

                        If multiple valid windows have the same minimum length, print the one that appears first in text.

                        For example, for text "ADOBECODEBANC" and pattern "ABC", the substring "BANC" contains all required characters and has the minimum possible length.

                        Therefore, the answer is "BANC".
                        """,

                        Difficulty.HARD,

                        "sliding-window",

                        """
                        1 <= length of text, pattern <= 100000

                        text and pattern contain uppercase and lowercase English letters.

                        Character comparisons are case-sensitive.
                        """,

                        """
                        The first line contains the string text.

                        The second line contains the string pattern.
                        """,

                        """
                        Print the shortest contiguous substring of text containing every character from pattern with the required frequencies.

                        If no valid substring exists, print -1.

                        If multiple minimum-length answers exist, print the one that appears first.
                        """,

                        """
                        ADOBECODEBANC
                        ABC
                        """,

                        "BANC",

                        List.of(
                                "Sliding Window",
                                "String",
                                "Hashing"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        ADOBECODEBANC
                                        ABC
                                        """,
                                        "BANC",
                                        """
                                        The substring "BANC" contains 'A', 'B', and 'C'.

                                        No shorter substring contains all three required characters.

                                        Therefore, "BANC" is the answer.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        a
                                        a
                                        """,
                                        "a",
                                        """
                                        The complete text contains the required character.

                                        Therefore, the minimum window is "a".
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        a
                                        aa
                                        """,
                                        "-1",
                                        """
                                        The pattern requires two occurrences of 'a', but the text contains only one.

                                        Therefore, no valid window exists.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        aaabdabcefaecbef
                                        abc
                                        """,
                                        "abc",
                                        """
                                        The text contains the contiguous substring "abc".

                                        It contains every required character and has length 3, which is the smallest possible length for this pattern.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        ABAACBAB
                                        AABC
                                        """,
                                        "ACB",
                                        """
                                        This pattern requires two 'A' characters, one 'B', and one 'C'.

                                        The substring "ACB" does not contain two 'A' characters, so it is not valid.

                                        The shortest valid window is "AACB", which contains two 'A' characters, one 'C', and one 'B'.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "ADOBECODEBANC\nABC",
                                        "BANC",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "a\na",
                                        "a",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "a\naa",
                                        "-1",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "aaabdabcefaecbef\nabc",
                                        "abc",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "ABAACBAB\nAABC",
                                        "AACB",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "thisisateststring\ntist",
                                        "tstri",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "abcdef\nxyz",
                                        "-1",
                                        true
                                )
                        )
                )
        );
    }
}