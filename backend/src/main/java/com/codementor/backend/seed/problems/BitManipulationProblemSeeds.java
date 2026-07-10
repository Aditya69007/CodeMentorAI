package com.codementor.backend.seed.problems;

import com.codementor.backend.entity.Difficulty;
import com.codementor.backend.seed.ProblemExampleSeedData;
import com.codementor.backend.seed.ProblemSeedData;
import com.codementor.backend.seed.TestCaseSeedData;

import java.util.List;

public final class BitManipulationProblemSeeds {

    private BitManipulationProblemSeeds() {
    }

    public static List<ProblemSeedData> getProblems() {

        return List.of(

                // ==================================================
                // 1. SINGLE NUMBER
                // ==================================================

                new ProblemSeedData(

                        "Single Number",

                        """
                        Given an array containing n integers, every value appears exactly twice except for one value that appears exactly once.

                        Find and print the value that appears only once.

                        Your solution should use bit manipulation and constant extra space.

                        The XOR operation has useful properties for this problem.

                        XORing a value with itself produces 0, and XORing any value with 0 produces the original value.

                        Therefore, when all array elements are XORed together, values appearing twice cancel each other and only the unique value remains.
                        """,

                        Difficulty.EASY,

                        "bit-manipulation",

                        """
                        1 <= n <= 100000

                        n is odd.

                        -1000000000 <= array[i] <= 1000000000

                        Every value appears exactly twice except for one value that appears exactly once.
                        """,

                        """
                        The first line contains an integer n representing the number of elements.

                        The second line contains n space-separated integers.
                        """,

                        """
                        Print the integer that appears exactly once.
                        """,

                        """
                        5
                        4 1 2 1 2
                        """,

                        "4",

                        List.of(
                                "Bit Manipulation",
                                "Array",
                                "XOR"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        5
                                        4 1 2 1 2
                                        """,
                                        "4",
                                        """
                                        The values 1 and 2 each appear twice.

                                        XORing equal values cancels them.

                                        Therefore, 4 is the only value that remains.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        3
                                        2 2 1
                                        """,
                                        "1",
                                        """
                                        The value 2 appears twice while 1 appears once.

                                        Therefore, the answer is 1.
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
                                        The array contains only one value.

                                        Therefore, that value is the unique element.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        7
                                        -1 5 3 5 -1 8 3
                                        """,
                                        "8",
                                        """
                                        The values -1, 5, and 3 each appear twice.

                                        The value 8 appears only once.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        9
                                        10 20 30 40 20 30 10 50 40
                                        """,
                                        "50",
                                        """
                                        Every value except 50 appears exactly twice.

                                        Therefore, the unique value is 50.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "5\n4 1 2 1 2",
                                        "4",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "3\n2 2 1",
                                        "1",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "1\n10",
                                        "10",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "7\n-1 5 3 5 -1 8 3",
                                        "8",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "9\n10 20 30 40 20 30 10 50 40",
                                        "50",
                                        true
                                )
                        )
                ),

                // ==================================================
                // 2. NUMBER OF SET BITS
                // ==================================================

                new ProblemSeedData(

                        "Number of Set Bits",

                        """
                        Given a non-negative integer n, count the number of bits equal to 1 in its binary representation.

                        A bit whose value is 1 is called a set bit.

                        For example, the decimal number 11 has binary representation 1011.

                        This representation contains three set bits.

                        Therefore, the answer is 3.
                        """,

                        Difficulty.EASY,

                        "bit-manipulation",

                        """
                        0 <= n <= 4294967295
                        """,

                        """
                        A single non-negative integer n.
                        """,

                        """
                        Print the number of set bits in the binary representation of n.
                        """,

                        "11",

                        "3",

                        List.of(
                                "Bit Manipulation",
                                "Math"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        "11",
                                        "3",
                                        """
                                        The binary representation of 11 is 1011.

                                        It contains three bits equal to 1.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        "0",
                                        "0",
                                        """
                                        The binary representation of 0 contains no set bits.

                                        Therefore, the answer is 0.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        "1",
                                        "1",
                                        """
                                        The binary representation of 1 is 1.

                                        Therefore, it contains one set bit.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        "16",
                                        "1",
                                        """
                                        The binary representation of 16 is 10000.

                                        Only one bit is set.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        "15",
                                        "4",
                                        """
                                        The binary representation of 15 is 1111.

                                        All four bits are set.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "11",
                                        "3",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "0",
                                        "0",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "1",
                                        "1",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "16",
                                        "1",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "15",
                                        "4",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "4294967295",
                                        "32",
                                        true
                                )
                        )
                ),

                // ==================================================
                // 3. COUNTING BITS
                // ==================================================

                new ProblemSeedData(

                        "Counting Bits",

                        """
                        Given a non-negative integer n, determine the number of set bits in the binary representation of every integer from 0 to n.

                        A set bit is a binary digit whose value is 1.

                        Print n + 1 space-separated integers.

                        The value at position i must represent the number of set bits in integer i.

                        For example, when n = 5:

                        0 has binary representation 0 and contains 0 set bits.

                        1 has binary representation 1 and contains 1 set bit.

                        2 has binary representation 10 and contains 1 set bit.

                        3 has binary representation 11 and contains 2 set bits.

                        4 has binary representation 100 and contains 1 set bit.

                        5 has binary representation 101 and contains 2 set bits.

                        Therefore, the result is 0 1 1 2 1 2.
                        """,

                        Difficulty.MEDIUM,

                        "bit-manipulation",

                        """
                        0 <= n <= 100000
                        """,

                        """
                        A single non-negative integer n.
                        """,

                        """
                        Print n + 1 space-separated integers.

                        The value at zero-based position i must equal the number of set bits in i.
                        """,

                        "5",

                        "0 1 1 2 1 2",

                        List.of(
                                "Bit Manipulation",
                                "Dynamic Programming"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        "5",
                                        "0 1 1 2 1 2",
                                        """
                                        The numbers from 0 through 5 contain 0, 1, 1, 2, 1, and 2 set bits respectively.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        "2",
                                        "0 1 1",
                                        """
                                        The binary representations are 0, 1, and 10.

                                        Their set-bit counts are 0, 1, and 1.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        "0",
                                        "0",
                                        """
                                        Only the number 0 is considered.

                                        It contains no set bits.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        "3",
                                        "0 1 1 2",
                                        """
                                        The values 0, 1, 2, and 3 contain 0, 1, 1, and 2 set bits.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        "8",
                                        "0 1 1 2 1 2 2 3 1",
                                        """
                                        The numbers from 0 through 8 are processed.

                                        Their set-bit counts are 0, 1, 1, 2, 1, 2, 2, 3, and 1.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "5",
                                        "0 1 1 2 1 2",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "2",
                                        "0 1 1",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "0",
                                        "0",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "3",
                                        "0 1 1 2",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "8",
                                        "0 1 1 2 1 2 2 3 1",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "10",
                                        "0 1 1 2 1 2 2 3 1 2 2",
                                        true
                                )
                        )
                ),

                // ==================================================
                // 4. BITWISE AND OF NUMBER RANGE
                // ==================================================

                new ProblemSeedData(

                        "Bitwise AND of Number Range",

                        """
                        Given two non-negative integers left and right, compute the bitwise AND of every integer in the inclusive range from left to right.

                        The bitwise AND operation keeps a bit equal to 1 only when that bit is equal to 1 in every value being processed.

                        For example, for the range from 5 to 7:

                        5 has binary representation 101.

                        6 has binary representation 110.

                        7 has binary representation 111.

                        Performing 5 AND 6 AND 7 produces 100 in binary, which is 4 in decimal.

                        Therefore, the answer is 4.
                        """,

                        Difficulty.MEDIUM,

                        "bit-manipulation",

                        """
                        0 <= left <= right <= 2147483647
                        """,

                        """
                        A single line contains two space-separated integers left and right.
                        """,

                        """
                        Print the bitwise AND of every integer in the inclusive range [left, right].
                        """,

                        "5 7",

                        "4",

                        List.of(
                                "Bit Manipulation",
                                "Math"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        "5 7",
                                        "4",
                                        """
                                        The binary values are 101, 110, and 111.

                                        Only the highest bit remains set in every number.

                                        Therefore, the result is 4.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        "0 0",
                                        "0",
                                        """
                                        The range contains only the number 0.

                                        Therefore, the result is 0.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        "1 1",
                                        "1",
                                        """
                                        The range contains only the number 1.

                                        Therefore, the result is 1.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        "10 15",
                                        "8",
                                        """
                                        The values from 10 to 15 share only the bit representing decimal value 8.

                                        Therefore, their bitwise AND is 8.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        "8 9",
                                        "8",
                                        """
                                        The binary representations are 1000 and 1001.

                                        Their bitwise AND is 1000, which equals 8.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "5 7",
                                        "4",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "0 0",
                                        "0",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "1 1",
                                        "1",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "10 15",
                                        "8",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "8 9",
                                        "8",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "0 2147483647",
                                        "0",
                                        true
                                )
                        )
                ),

                // ==================================================
                // 5. MINIMUM XOR ASSIGNMENT
                // ==================================================

                new ProblemSeedData(

                        "Minimum XOR Assignment",

                        """
                        Given two integer arrays a and b, each containing exactly n elements, reorder the elements of array b so that the total XOR score is minimized.

                        Every element from array a must be paired with exactly one element from array b.

                        Every element from array b must be used exactly once.

                        The score of a pairing is the sum of a[i] XOR pairedB[i] for every index i.

                        Find and print the minimum possible total score.

                        For example, consider a = [1, 2] and b = [2, 3].

                        Pairing 1 with 2 and 2 with 3 produces a score of (1 XOR 2) + (2 XOR 3) = 3 + 1 = 4.

                        Pairing 1 with 3 and 2 with 2 produces a score of (1 XOR 3) + (2 XOR 2) = 2 + 0 = 2.

                        Therefore, the minimum possible score is 2.
                        """,

                        Difficulty.HARD,

                        "bit-manipulation",

                        """
                        1 <= n <= 14

                        0 <= a[i], b[i] <= 1000000000
                        """,

                        """
                        The first line contains an integer n.

                        The second line contains n space-separated integers representing array a.

                        The third line contains n space-separated integers representing array b.
                        """,

                        """
                        Print the minimum possible total XOR score.
                        """,

                        """
                        2
                        1 2
                        2 3
                        """,

                        "2",

                        List.of(
                                "Bit Manipulation",
                                "Dynamic Programming",
                                "Bitmask"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        2
                                        1 2
                                        2 3
                                        """,
                                        "2",
                                        """
                                        Pairing 1 with 3 produces XOR value 2.

                                        Pairing 2 with 2 produces XOR value 0.

                                        The total score is 2, which is the minimum possible.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        3
                                        1 0 3
                                        5 3 4
                                        """,
                                        "8",
                                        """
                                        One optimal assignment pairs 1 with 5, 0 with 4, and 3 with 3.

                                        The XOR values are 4, 4, and 0.

                                        Therefore, the minimum total score is 8.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        1
                                        10
                                        7
                                        """,
                                        "13",
                                        """
                                        Only one pairing is possible.

                                        The value of 10 XOR 7 is 13.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        3
                                        1 2 3
                                        1 2 3
                                        """,
                                        "0",
                                        """
                                        Each value can be paired with an identical value.

                                        XORing equal values produces 0.

                                        Therefore, the minimum score is 0.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        2
                                        0 0
                                        1 2
                                        """,
                                        "3",
                                        """
                                        Each zero must be paired with one value from the second array.

                                        The score is (0 XOR 1) + (0 XOR 2) = 1 + 2 = 3.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "2\n1 2\n2 3",
                                        "2",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "3\n1 0 3\n5 3 4",
                                        "8",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "1\n10\n7",
                                        "13",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "3\n1 2 3\n1 2 3",
                                        "0",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "2\n0 0\n1 2",
                                        "3",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "4\n5 10 15 20\n20 15 10 5",
                                        "0",
                                        true
                                )
                        )
                )
        );
    }
}