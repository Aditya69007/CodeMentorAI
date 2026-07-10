package com.codementor.backend.seed.problems;

import com.codementor.backend.entity.Difficulty;
import com.codementor.backend.seed.ProblemExampleSeedData;
import com.codementor.backend.seed.ProblemSeedData;
import com.codementor.backend.seed.TestCaseSeedData;

import java.util.List;

public final class HashingProblemSeeds {

    private HashingProblemSeeds() {
    }

    public static List<ProblemSeedData> getProblems() {

        return List.of(

                // ==================================================
                // CONTAINS DUPLICATE
                // ==================================================

                new ProblemSeedData(

                        "Contains Duplicate",

                        """
                        Given an array containing n integers, determine whether any value appears at least twice.

                        If an element occurs more than once anywhere in the array, print true.

                        If every element is distinct, print false.

                        The duplicate values do not need to appear next to each other.

                        For example, in the array [1, 2, 3, 1], the value 1 appears twice, so the answer is true.
                        """,

                        Difficulty.EASY,

                        "hashing",

                        """
                        1 <= n <= 100000

                        -1000000000 <= array[i] <= 1000000000
                        """,

                        """
                        The first line contains an integer n representing the number of elements.

                        The second line contains n space-separated integers.
                        """,

                        """
                        Print true if any value appears more than once.

                        Otherwise, print false.
                        """,

                        """
                        4
                        1 2 3 1
                        """,

                        "true",

                        List.of(
                                "Hashing",
                                "Array",
                                "Hash Set"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        4
                                        1 2 3 1
                                        """,
                                        "true",
                                        """
                                        The value 1 appears at indices 0 and 3.

                                        Since at least one value occurs more than once, the answer is true.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        4
                                        1 2 3 4
                                        """,
                                        "false",
                                        """
                                        Every value in the array is distinct.

                                        Therefore, the answer is false.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        1
                                        7
                                        """,
                                        "false",
                                        """
                                        The array contains only one element.

                                        A duplicate cannot exist, so the answer is false.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        5
                                        -1 2 -1 3 4
                                        """,
                                        "true",
                                        """
                                        The value -1 appears twice.

                                        Therefore, the array contains a duplicate.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        6
                                        5 5 5 5 5 5
                                        """,
                                        "true",
                                        """
                                        The value 5 appears multiple times.

                                        Finding even one repeated value is enough to print true.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "4\n1 2 3 1",
                                        "true",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "4\n1 2 3 4",
                                        "false",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "1\n7",
                                        "false",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "5\n-1 2 -1 3 4",
                                        "true",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "6\n5 5 5 5 5 5",
                                        "true",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "7\n10 20 30 40 50 60 10",
                                        "true",
                                        true
                                )
                        )
                ),

                // ==================================================
                // INTERSECTION OF TWO ARRAYS
                // ==================================================

                new ProblemSeedData(

                        "Intersection of Two Arrays",

                        """
                        Given two integer arrays, find all distinct values that appear in both arrays.

                        Each value must appear only once in the result, even if it occurs multiple times in either input array.

                        Print the common values in increasing numerical order.

                        For example, the arrays [1, 2, 2, 1] and [2, 2] share only the value 2. Therefore, the result contains one value: 2.
                        """,

                        Difficulty.EASY,

                        "hashing",

                        """
                        1 <= n, m <= 100000

                        -1000000000 <= array1[i], array2[i] <= 1000000000
                        """,

                        """
                        The first line contains an integer n representing the size of the first array.

                        The second line contains n space-separated integers.

                        The third line contains an integer m representing the size of the second array.

                        The fourth line contains m space-separated integers.
                        """,

                        """
                        Print the number of distinct common values on the first line.

                        Print the common values in increasing order on the second line.

                        If no common values exist, print only 0.
                        """,

                        """
                        4
                        1 2 2 1
                        2
                        2 2
                        """,

                        """
                        1
                        2
                        """,

                        List.of(
                                "Hashing",
                                "Array",
                                "Hash Set"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        4
                                        1 2 2 1
                                        2
                                        2 2
                                        """,
                                        """
                                        1
                                        2
                                        """,
                                        """
                                        The only value appearing in both arrays is 2.

                                        Duplicate occurrences are included only once.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        3
                                        4 9 5
                                        5
                                        9 4 9 8 4
                                        """,
                                        """
                                        2
                                        4 9
                                        """,
                                        """
                                        The values 4 and 9 occur in both arrays.

                                        After removing duplicates and sorting, the result is 4 9.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        3
                                        1 2 3
                                        3
                                        4 5 6
                                        """,
                                        "0",
                                        """
                                        The arrays do not share any values.

                                        Therefore, the number of common values is 0.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        1
                                        -5
                                        1
                                        -5
                                        """,
                                        """
                                        1
                                        -5
                                        """,
                                        """
                                        The value -5 appears in both single-element arrays.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        5
                                        1 1 1 2 2
                                        6
                                        1 1 2 2 3 3
                                        """,
                                        """
                                        2
                                        1 2
                                        """,
                                        """
                                        The distinct values shared by both arrays are 1 and 2.

                                        Repeated occurrences do not affect the result.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "4\n1 2 2 1\n2\n2 2",
                                        "1\n2",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "3\n4 9 5\n5\n9 4 9 8 4",
                                        "2\n4 9",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "3\n1 2 3\n3\n4 5 6",
                                        "0",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "1\n-5\n1\n-5",
                                        "1\n-5",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "5\n1 1 1 2 2\n6\n1 1 2 2 3 3",
                                        "2\n1 2",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "6\n-3 -1 0 2 5 8\n7\n8 8 5 4 0 -3 10",
                                        "4\n-3 0 5 8",
                                        true
                                )
                        )
                ),

                // ==================================================
                // SUBARRAY SUM EQUALS K
                // ==================================================

                new ProblemSeedData(

                        "Subarray Sum Equals K",

                        """
                        Given an integer array and an integer k, count the number of contiguous subarrays whose sum is exactly equal to k.

                        A subarray is a continuous sequence containing one or more elements from the original array.

                        Different subarrays are counted separately, even when they contain the same values at different positions.

                        The array may contain positive values, negative values, and zero.

                        For example, in [1, 1, 1] with k = 2, there are two valid subarrays: the elements at indices 0 to 1 and the elements at indices 1 to 2.
                        """,

                        Difficulty.MEDIUM,

                        "hashing",

                        """
                        1 <= n <= 100000

                        -1000000000 <= array[i] <= 1000000000

                        -100000000000000 <= k <= 100000000000000

                        The answer fits within a signed 64-bit integer.
                        """,

                        """
                        The first line contains an integer n representing the number of elements.

                        The second line contains n space-separated integers.

                        The third line contains the target sum k.
                        """,

                        """
                        Print the number of contiguous subarrays whose sum is exactly equal to k.
                        """,

                        """
                        3
                        1 1 1
                        2
                        """,

                        "2",

                        List.of(
                                "Hashing",
                                "Prefix Sum",
                                "Array"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        3
                                        1 1 1
                                        2
                                        """,
                                        "2",
                                        """
                                        The first valid subarray contains the first two elements: [1, 1].

                                        The second valid subarray contains the last two elements: [1, 1].

                                        Therefore, the answer is 2.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        3
                                        1 2 3
                                        3
                                        """,
                                        "2",
                                        """
                                        The subarray [1, 2] has sum 3.

                                        The single-element subarray [3] also has sum 3.

                                        Therefore, two valid subarrays exist.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        5
                                        1 -1 0 1 -1
                                        0
                                        """,
                                        "7",
                                        """
                                        Negative values and zero allow several different contiguous ranges to produce sum 0.

                                        Counting every valid range gives a total of 7 subarrays.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        1
                                        5
                                        5
                                        """,
                                        "1",
                                        """
                                        The only element equals k.

                                        Therefore, the complete array forms one valid subarray.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        4
                                        2 4 6 8
                                        5
                                        """,
                                        "0",
                                        """
                                        No contiguous subarray has sum 5.

                                        Therefore, the answer is 0.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "3\n1 1 1\n2",
                                        "2",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "3\n1 2 3\n3",
                                        "2",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "5\n1 -1 0 1 -1\n0",
                                        "7",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "1\n5\n5",
                                        "1",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "4\n2 4 6 8\n5",
                                        "0",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "6\n3 4 7 2 -3 1\n7",
                                        "4",
                                        true
                                )
                        )
                ),

                // ==================================================
                // LONGEST CONSECUTIVE SEQUENCE
                // ==================================================

                new ProblemSeedData(

                        "Longest Consecutive Sequence",

                        """
                        Given an unsorted array of integers, find the length of the longest sequence of consecutive values.

                        Consecutive values differ by exactly 1.

                        The values forming the sequence do not need to appear next to each other in the original array.

                        Duplicate values do not increase the length of a sequence.

                        For example, in [100, 4, 200, 1, 3, 2], the values 1, 2, 3, and 4 form the longest consecutive sequence. Therefore, the answer is 4.
                        """,

                        Difficulty.MEDIUM,

                        "hashing",

                        """
                        1 <= n <= 100000

                        -1000000000 <= array[i] <= 1000000000
                        """,

                        """
                        The first line contains an integer n.

                        The second line contains n space-separated integers.
                        """,

                        """
                        Print the length of the longest consecutive sequence.
                        """,

                        """
                        6
                        100 4 200 1 3 2
                        """,

                        "4",

                        List.of(
                                "Hashing",
                                "Array",
                                "Hash Set"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        6
                                        100 4 200 1 3 2
                                        """,
                                        "4",
                                        """
                                        The values 1, 2, 3, and 4 form a consecutive sequence.

                                        No longer sequence exists, so the answer is 4.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        10
                                        0 3 7 2 5 8 4 6 0 1
                                        """,
                                        "9",
                                        """
                                        The values from 0 through 8 are all present.

                                        They form a consecutive sequence containing 9 distinct values.

                                        The duplicate 0 does not increase the sequence length.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        1
                                        50
                                        """,
                                        "1",
                                        """
                                        A single value forms a consecutive sequence of length 1.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        5
                                        5 5 5 5 5
                                        """,
                                        "1",
                                        """
                                        The array contains only one distinct value.

                                        Duplicate occurrences do not extend a consecutive sequence.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        6
                                        -3 -2 -1 5 6 7
                                        """,
                                        "3",
                                        """
                                        Two consecutive sequences exist: [-3, -2, -1] and [5, 6, 7].

                                        Both have length 3, so the maximum length is 3.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "6\n100 4 200 1 3 2",
                                        "4",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "10\n0 3 7 2 5 8 4 6 0 1",
                                        "9",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "1\n50",
                                        "1",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "5\n5 5 5 5 5",
                                        "1",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "6\n-3 -2 -1 5 6 7",
                                        "3",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "8\n10 11 12 1 2 3 4 5",
                                        "5",
                                        true
                                )
                        )
                ),

                // ==================================================
                // ISOMORPHIC STRINGS
                // ==================================================

                new ProblemSeedData(

                        "Isomorphic Strings",

                        """
                        Given two strings s and t of equal length, determine whether they are isomorphic.

                        Two strings are isomorphic when every character in s can be replaced consistently to produce t.

                        Every occurrence of the same character in s must map to the same character in t.

                        Two different characters in s cannot map to the same character in t.

                        For example, "egg" and "add" are isomorphic because 'e' can map to 'a' and 'g' can map to 'd'.

                        However, "foo" and "bar" are not isomorphic because the two occurrences of 'o' would need to map to two different characters.
                        """,

                        Difficulty.EASY,

                        "hashing",

                        """
                        1 <= length of s, t <= 100000

                        s and t contain only lowercase English letters.

                        s and t have equal lengths.
                        """,

                        """
                        The first line contains string s.

                        The second line contains string t.
                        """,

                        """
                        Print true if s and t are isomorphic.

                        Otherwise, print false.
                        """,

                        """
                        egg
                        add
                        """,

                        "true",

                        List.of(
                                "Hashing",
                                "String",
                                "Hash Map"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        egg
                                        add
                                        """,
                                        "true",
                                        """
                                        The character 'e' consistently maps to 'a'.

                                        The character 'g' consistently maps to 'd'.

                                        Therefore, the strings are isomorphic.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        foo
                                        bar
                                        """,
                                        "false",
                                        """
                                        The first 'o' would map to 'a', while the second 'o' would map to 'r'.

                                        One character cannot map to two different characters, so the strings are not isomorphic.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        paper
                                        title
                                        """,
                                        "true",
                                        """
                                        Every character in "paper" has a consistent one-to-one mapping with a character in "title".

                                        Therefore, the strings are isomorphic.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        badc
                                        baba
                                        """,
                                        "false",
                                        """
                                        The required character mappings are not one-to-one.

                                        Therefore, the strings are not isomorphic.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        a
                                        z
                                        """,
                                        "true",
                                        """
                                        A single character can consistently map to another single character.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "egg\nadd",
                                        "true",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "foo\nbar",
                                        "false",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "paper\ntitle",
                                        "true",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "badc\nbaba",
                                        "false",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "a\nz",
                                        "true",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "ab\naa",
                                        "false",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "abc\ndef",
                                        "true",
                                        true
                                )
                        )
                )
        );
    }
}