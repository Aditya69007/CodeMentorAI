package com.codementor.backend.seed.problems;

import com.codementor.backend.entity.Difficulty;
import com.codementor.backend.seed.ProblemExampleSeedData;
import com.codementor.backend.seed.ProblemSeedData;
import com.codementor.backend.seed.TestCaseSeedData;

import java.util.List;

public final class MathProblemSeeds {

    private MathProblemSeeds() {
    }

    public static List<ProblemSeedData> getProblems() {

        return List.of(

                // ==================================================
                // 1. COUNT DIGITS
                // ==================================================

                new ProblemSeedData(

                        "Count Digits",

                        """
                        Given a non-negative integer n, determine the number of digits in its decimal representation.

                        For example, the number 5832 contains four digits.

                        The number 0 is considered to contain exactly one digit.

                        Print the total number of digits in n.
                        """,

                        Difficulty.EASY,

                        "math",

                        """
                        0 <= n <= 1000000000000000000
                        """,

                        """
                        A single non-negative integer n.
                        """,

                        """
                        Print the number of digits in n.
                        """,

                        "5832",

                        "4",

                        List.of(
                                "Math",
                                "Number Theory"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        "5832",
                                        "4",
                                        """
                                        The decimal representation of 5832 contains the digits 5, 8, 3, and 2.

                                        Therefore, the number contains 4 digits.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        "0",
                                        "1",
                                        """
                                        The number 0 is represented using one digit.

                                        Therefore, the answer is 1.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        "9",
                                        "1",
                                        """
                                        The number 9 contains only one digit.

                                        Therefore, the answer is 1.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        "100",
                                        "3",
                                        """
                                        The decimal representation of 100 contains three digits.

                                        Therefore, the answer is 3.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        "987654321",
                                        "9",
                                        """
                                        The given number contains nine decimal digits.

                                        Therefore, the answer is 9.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData("5832", "4", false),
                                new TestCaseSeedData("0", "1", false),
                                new TestCaseSeedData("9", "1", true),
                                new TestCaseSeedData("100", "3", true),
                                new TestCaseSeedData("987654321", "9", true)
                        )
                ),

                // ==================================================
                // 2. GREATEST COMMON DIVISOR
                // ==================================================

                new ProblemSeedData(

                        "Greatest Common Divisor",

                        """
                        Given two positive integers a and b, find their greatest common divisor.

                        The greatest common divisor is the largest positive integer that divides both numbers without leaving a remainder.

                        Use an efficient approach such as the Euclidean algorithm.

                        The Euclidean algorithm repeatedly replaces the larger problem with the remainder obtained from division.

                        Print the greatest common divisor of a and b.
                        """,

                        Difficulty.EASY,

                        "math",

                        """
                        1 <= a, b <= 1000000000000000000
                        """,

                        """
                        A single line contains two space-separated positive integers a and b.
                        """,

                        """
                        Print the greatest common divisor of a and b.
                        """,

                        "48 18",

                        "6",

                        List.of(
                                "Math",
                                "Number Theory",
                                "Euclidean Algorithm"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        "48 18",
                                        "6",
                                        """
                                        The positive common divisors of 48 and 18 include 1, 2, 3, and 6.

                                        The largest common divisor is 6.

                                        Therefore, the answer is 6.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        "10 5",
                                        "5",
                                        """
                                        The number 5 divides both 10 and 5.

                                        No larger positive integer divides both numbers.

                                        Therefore, the greatest common divisor is 5.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        "7 13",
                                        "1",
                                        """
                                        The two numbers are distinct prime numbers.

                                        Their only positive common divisor is 1.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        "100 100",
                                        "100",
                                        """
                                        Both numbers are equal.

                                        Therefore, their greatest common divisor is the number itself.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        "54 24",
                                        "6",
                                        """
                                        Applying the Euclidean algorithm eventually produces the value 6.

                                        Therefore, the greatest common divisor is 6.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData("48 18", "6", false),
                                new TestCaseSeedData("10 5", "5", false),
                                new TestCaseSeedData("7 13", "1", true),
                                new TestCaseSeedData("100 100", "100", true),
                                new TestCaseSeedData("54 24", "6", true)
                        )
                ),

                // ==================================================
                // 3. COUNT PRIME NUMBERS
                // ==================================================

                new ProblemSeedData(

                        "Count Prime Numbers",

                        """
                        Given a non-negative integer n, count how many prime numbers are strictly smaller than n.

                        A prime number is a positive integer greater than 1 that has exactly two positive divisors: 1 and itself.

                        For example, the prime numbers smaller than 10 are 2, 3, 5, and 7.

                        An efficient solution can use the Sieve of Eratosthenes.

                        Print the total number of prime numbers strictly smaller than n.
                        """,

                        Difficulty.MEDIUM,

                        "math",

                        """
                        0 <= n <= 5000000
                        """,

                        """
                        A single integer n.
                        """,

                        """
                        Print the number of prime numbers strictly smaller than n.
                        """,

                        "10",

                        "4",

                        List.of(
                                "Math",
                                "Prime Numbers",
                                "Sieve of Eratosthenes"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        "10",
                                        "4",
                                        """
                                        The prime numbers strictly smaller than 10 are 2, 3, 5, and 7.

                                        There are four such numbers.

                                        Therefore, the answer is 4.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        "0",
                                        "0",
                                        """
                                        No positive integers are smaller than 0.

                                        Therefore, no prime numbers exist in the required range.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        "2",
                                        "0",
                                        """
                                        The only non-negative integers smaller than 2 are 0 and 1.

                                        Neither is prime.

                                        Therefore, the answer is 0.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        "3",
                                        "1",
                                        """
                                        The only prime number strictly smaller than 3 is 2.

                                        Therefore, the answer is 1.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        "20",
                                        "8",
                                        """
                                        The prime numbers smaller than 20 are 2, 3, 5, 7, 11, 13, 17, and 19.

                                        Therefore, the answer is 8.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData("10", "4", false),
                                new TestCaseSeedData("0", "0", false),
                                new TestCaseSeedData("2", "0", true),
                                new TestCaseSeedData("3", "1", true),
                                new TestCaseSeedData("20", "8", true),
                                new TestCaseSeedData("100", "25", true)
                        )
                ),

                // ==================================================
                // 4. POWER USING FAST EXPONENTIATION
                // ==================================================

                new ProblemSeedData(

                        "Power Using Fast Exponentiation",

                        """
                        Given two integers base and exponent, calculate base raised to the power exponent.

                        The exponent is non-negative.

                        Since the result can become very large, print the answer modulo 1000000007.

                        An efficient solution should use binary exponentiation, also called fast exponentiation.

                        Instead of multiplying the base exponent times, repeatedly square the base and process the binary representation of the exponent.

                        Print the final modular result.
                        """,

                        Difficulty.MEDIUM,

                        "math",

                        """
                        0 <= base <= 1000000000

                        0 <= exponent <= 1000000000000000000
                        """,

                        """
                        A single line contains two space-separated integers base and exponent.
                        """,

                        """
                        Print base raised to exponent modulo 1000000007.
                        """,

                        "2 10",

                        "1024",

                        List.of(
                                "Math",
                                "Binary Exponentiation",
                                "Modular Arithmetic"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        "2 10",
                                        "1024",
                                        """
                                        The value of 2 raised to the power 10 is 1024.

                                        Since 1024 is smaller than the modulus, the final answer remains 1024.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        "5 0",
                                        "1",
                                        """
                                        Any non-zero number raised to the power 0 equals 1.

                                        Therefore, the answer is 1.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        "3 4",
                                        "81",
                                        """
                                        The value of 3 raised to the power 4 is 81.

                                        Therefore, the answer is 81.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        "10 9",
                                        "1000000000",
                                        """
                                        The value of 10 raised to the power 9 is 1000000000.

                                        This value is smaller than 1000000007.

                                        Therefore, the answer remains 1000000000.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        "1000000007 5",
                                        "0",
                                        """
                                        The base is exactly equal to the modulus.

                                        Therefore, the base becomes 0 under modular arithmetic, and every positive power is also 0.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData("2 10", "1024", false),
                                new TestCaseSeedData("5 0", "1", false),
                                new TestCaseSeedData("3 4", "81", true),
                                new TestCaseSeedData("10 9", "1000000000", true),
                                new TestCaseSeedData("1000000007 5", "0", true),
                                new TestCaseSeedData("2 100", "976371285", true)
                        )
                ),

                // ==================================================
                // 5. COUNT UNIQUE PATHS
                // ==================================================

                new ProblemSeedData(

                        "Count Unique Paths",

                        """
                        A robot is located at the top-left corner of a rectangular grid containing rows rows and columns columns.

                        The robot wants to reach the bottom-right corner.

                        From each cell, the robot may move only one position to the right or one position downward.

                        Determine the total number of distinct paths from the starting cell to the destination cell.

                        Two paths are considered different if at some point they use different moves.

                        The answer is guaranteed to fit inside a signed 64-bit integer.

                        Print the total number of unique paths.

                        An efficient mathematical solution can use combinations instead of constructing a complete dynamic programming table.
                        """,

                        Difficulty.HARD,

                        "math",

                        """
                        1 <= rows, columns <= 30

                        The answer is guaranteed to fit inside a signed 64-bit integer.
                        """,

                        """
                        A single line contains two space-separated integers rows and columns.
                        """,

                        """
                        Print the total number of unique paths from the top-left corner to the bottom-right corner.
                        """,

                        "3 7",

                        "28",

                        List.of(
                                "Math",
                                "Combinatorics",
                                "Dynamic Programming"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        "3 7",
                                        "28",
                                        """
                                        Every valid path requires exactly 2 downward moves and 6 right moves.

                                        These 8 moves can be arranged in 28 distinct ways.

                                        Therefore, the answer is 28.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        "3 2",
                                        "3",
                                        """
                                        Every path requires two downward moves and one right move.

                                        There are three different arrangements of these moves.

                                        Therefore, the answer is 3.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        "1 1",
                                        "1",
                                        """
                                        The robot is already located at the destination.

                                        Therefore, exactly one path exists.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        "1 5",
                                        "1",
                                        """
                                        The robot can only move to the right.

                                        Therefore, exactly one valid path exists.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        "4 4",
                                        "20",
                                        """
                                        Every valid path contains three downward moves and three right moves.

                                        The number of distinct arrangements is 20.

                                        Therefore, the answer is 20.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData("3 7", "28", false),
                                new TestCaseSeedData("3 2", "3", false),
                                new TestCaseSeedData("1 1", "1", true),
                                new TestCaseSeedData("1 5", "1", true),
                                new TestCaseSeedData("4 4", "20", true),
                                new TestCaseSeedData("10 10", "48620", true)
                        )
                )
        );
    }
}