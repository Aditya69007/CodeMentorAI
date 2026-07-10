package com.codementor.backend.seed.problems;

import com.codementor.backend.entity.Difficulty;
import com.codementor.backend.seed.ProblemExampleSeedData;
import com.codementor.backend.seed.ProblemSeedData;
import com.codementor.backend.seed.TestCaseSeedData;

import java.util.List;

public final class BacktrackingProblemSeeds {

    private BacktrackingProblemSeeds() {
    }

    public static List<ProblemSeedData> getProblems() {

        return List.of(

                // ==================================================
                // GENERATE BINARY STRINGS
                // ==================================================

                new ProblemSeedData(

                        "Generate Binary Strings",

                        """
                        Given a positive integer n, generate all binary strings of length n.

                        A binary string contains only the characters 0 and 1.

                        Print all generated strings in lexicographical order.

                        Since 0 comes before 1, strings beginning with 0 must appear before strings beginning with 1.

                        First print the total number of binary strings.

                        Then print each binary string on a separate line.

                        For example, when n = 2, the binary strings are 00, 01, 10, and 11.
                        """,

                        Difficulty.EASY,

                        "backtracking",

                        """
                        1 <= n <= 15
                        """,

                        """
                        A single integer n representing the required length of every binary string.
                        """,

                        """
                        Print the total number of binary strings on the first line.

                        Then print every binary string of length n on a separate line in lexicographical order.
                        """,

                        "2",

                        """
                        4
                        00
                        01
                        10
                        11
                        """,

                        List.of(
                                "Backtracking",
                                "Recursion",
                                "String"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        "2",
                                        """
                                        4
                                        00
                                        01
                                        10
                                        11
                                        """,
                                        """
                                        Each of the two positions can contain either 0 or 1.

                                        Therefore, 2^2 = 4 binary strings exist.

                                        In lexicographical order, they are 00, 01, 10, and 11.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        "1",
                                        """
                                        2
                                        0
                                        1
                                        """,
                                        """
                                        A binary string of length 1 can contain either 0 or 1.

                                        Therefore, two strings exist.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        "3",
                                        """
                                        8
                                        000
                                        001
                                        010
                                        011
                                        100
                                        101
                                        110
                                        111
                                        """,
                                        """
                                        Three positions each have two possible choices.

                                        Therefore, 2^3 = 8 strings are generated and printed in lexicographical order.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        "4",
                                        """
                                        16
                                        0000
                                        0001
                                        0010
                                        0011
                                        0100
                                        0101
                                        0110
                                        0111
                                        1000
                                        1001
                                        1010
                                        1011
                                        1100
                                        1101
                                        1110
                                        1111
                                        """,
                                        """
                                        Four binary positions produce 2^4 = 16 possible strings.

                                        Backtracking explores the choice 0 before 1 at every position, producing lexicographical order.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        "5",
                                        """
                                        32
                                        00000
                                        00001
                                        00010
                                        00011
                                        00100
                                        00101
                                        00110
                                        00111
                                        01000
                                        01001
                                        01010
                                        01011
                                        01100
                                        01101
                                        01110
                                        01111
                                        10000
                                        10001
                                        10010
                                        10011
                                        10100
                                        10101
                                        10110
                                        10111
                                        11000
                                        11001
                                        11010
                                        11011
                                        11100
                                        11101
                                        11110
                                        11111
                                        """,
                                        """
                                        Five binary positions produce 2^5 = 32 possible strings.

                                        Every possible combination of 0 and 1 is generated exactly once.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "2",
                                        "4\n00\n01\n10\n11",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "1",
                                        "2\n0\n1",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "3",
                                        "8\n000\n001\n010\n011\n100\n101\n110\n111",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "4",
                                        "16\n0000\n0001\n0010\n0011\n0100\n0101\n0110\n0111\n1000\n1001\n1010\n1011\n1100\n1101\n1110\n1111",
                                        true
                                )
                        )
                ),

                // ==================================================
                // LETTER CASE PERMUTATION
                // ==================================================

                new ProblemSeedData(

                        "Letter Case Permutation",

                        """
                        Given a string s containing lowercase English letters and decimal digits, generate every possible string that can be created by changing the case of its letters.

                        Every letter may independently remain lowercase or become uppercase.

                        Digits must remain unchanged.

                        Print all unique generated strings in lexicographical order.

                        First print the total number of generated strings.

                        Then print each string on a separate line.

                        For example, from "a1b" we can generate "A1B", "A1b", "a1B", and "a1b".
                        """,

                        Difficulty.EASY,

                        "backtracking",

                        """
                        1 <= length of s <= 12

                        s contains only lowercase English letters and decimal digits.
                        """,

                        """
                        A single string s.
                        """,

                        """
                        Print the total number of generated strings on the first line.

                        Then print every generated string on a separate line in lexicographical order.
                        """,

                        "a1b",

                        """
                        4
                        A1B
                        A1b
                        a1B
                        a1b
                        """,

                        List.of(
                                "Backtracking",
                                "Recursion",
                                "String"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        "a1b",
                                        """
                                        4
                                        A1B
                                        A1b
                                        a1B
                                        a1b
                                        """,
                                        """
                                        The letters 'a' and 'b' each have two possible cases.

                                        The digit 1 remains unchanged.

                                        Therefore, 2^2 = 4 strings are generated.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        "3z4",
                                        """
                                        2
                                        3Z4
                                        3z4
                                        """,
                                        """
                                        Only the letter 'z' can change case.

                                        Therefore, two possible strings exist.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        "123",
                                        """
                                        1
                                        123
                                        """,
                                        """
                                        The input contains no letters.

                                        Digits cannot change, so only the original string exists.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        "ab",
                                        """
                                        4
                                        AB
                                        Ab
                                        aB
                                        ab
                                        """,
                                        """
                                        Both letters independently have uppercase and lowercase choices.

                                        Therefore, four strings are generated.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        "x",
                                        """
                                        2
                                        X
                                        x
                                        """,
                                        """
                                        The single letter can be uppercase or lowercase.

                                        Therefore, two results exist.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "a1b",
                                        "4\nA1B\nA1b\na1B\na1b",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "3z4",
                                        "2\n3Z4\n3z4",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "123",
                                        "1\n123",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "ab",
                                        "4\nAB\nAb\naB\nab",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "x",
                                        "2\nX\nx",
                                        true
                                )
                        )
                ),

                // ==================================================
                // GENERATE PARENTHESES
                // ==================================================

                new ProblemSeedData(

                        "Generate Parentheses",

                        """
                        Given an integer n representing the number of pairs of parentheses, generate every valid combination containing exactly n opening parentheses and n closing parentheses.

                        A parentheses string is valid when every opening parenthesis is matched with a closing parenthesis and no prefix contains more closing parentheses than opening parentheses.

                        Print all valid combinations in lexicographical order.

                        First print the total number of valid combinations.

                        Then print each combination on a separate line.

                        For example, when n = 2, the valid combinations are "(())" and "()()".
                        """,

                        Difficulty.MEDIUM,

                        "backtracking",

                        """
                        1 <= n <= 8
                        """,

                        """
                        A single integer n representing the number of pairs of parentheses.
                        """,

                        """
                        Print the total number of valid combinations on the first line.

                        Then print every valid parentheses string on a separate line in lexicographical order.
                        """,

                        "3",

                        """
                        5
                        ((()))
                        (()())
                        (())()
                        ()(())
                        ()()()
                        """,

                        List.of(
                                "Backtracking",
                                "Recursion",
                                "String"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        "3",
                                        """
                                        5
                                        ((()))
                                        (()())
                                        (())()
                                        ()(())
                                        ()()()
                                        """,
                                        """
                                        Exactly five valid arrangements exist for three pairs of parentheses.

                                        Each arrangement uses three opening and three closing parentheses while remaining valid at every prefix.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        "1",
                                        """
                                        1
                                        ()
                                        """,
                                        """
                                        With one pair, only one valid combination exists.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        "2",
                                        """
                                        2
                                        (())
                                        ()()
                                        """,
                                        """
                                        Two valid arrangements exist for two pairs of parentheses.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        "4",
                                        """
                                        14
                                        (((())))
                                        ((()()))
                                        ((())())
                                        ((()))()
                                        (()(()))
                                        (()()())
                                        (()())()
                                        (())(())
                                        (())()()
                                        ()((()))
                                        ()(()())
                                        ()(())()
                                        ()()(())
                                        ()()()()
                                        """,
                                        """
                                        Four pairs of parentheses have 14 valid combinations.

                                        Backtracking adds a closing parenthesis only when it cannot make the current prefix invalid.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        "5",
                                        "42",
                                        """
                                        Five pairs of parentheses have 42 valid combinations.

                                        The first output line therefore contains 42, followed by all 42 valid strings.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "3",
                                        "5\n((()))\n(()())\n(())()\n()(())\n()()()",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "1",
                                        "1\n()",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "2",
                                        "2\n(())\n()()",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "4",
                                        "14\n(((())))\n((()()))\n((())())\n((()))()\n(()(()))\n(()()())\n(()())()\n(())(())\n(())()()\n()((()))\n()(()())\n()(())()\n()()(())\n()()()()",
                                        true
                                )
                        )
                ),

                // ==================================================
                // COMBINATION SUM
                // ==================================================

                new ProblemSeedData(

                        "Combination Sum",

                        """
                        Given an array of n distinct positive integers and a positive target value, find all unique combinations whose sum is exactly equal to the target.

                        Every input value may be selected any number of times.

                        Values inside each combination must appear in non-decreasing order.

                        Print all combinations in lexicographical order.

                        First print the total number of valid combinations.

                        Then print each combination on a separate line.

                        For example, for values [2, 3, 6, 7] and target 7, the valid combinations are [2, 2, 3] and [7].
                        """,

                        Difficulty.MEDIUM,

                        "backtracking",

                        """
                        1 <= n <= 20

                        1 <= array[i] <= 100

                        All array values are distinct.

                        1 <= target <= 500
                        """,

                        """
                        The first line contains two space-separated integers n and target.

                        The second line contains n space-separated distinct positive integers.
                        """,

                        """
                        Print the total number of unique combinations on the first line.

                        Then print each combination on a separate line as space-separated integers.

                        If no valid combination exists, print only 0.
                        """,

                        """
                        4 7
                        2 3 6 7
                        """,

                        """
                        2
                        2 2 3
                        7
                        """,

                        List.of(
                                "Backtracking",
                                "Recursion",
                                "Array"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        4 7
                                        2 3 6 7
                                        """,
                                        """
                                        2
                                        2 2 3
                                        7
                                        """,
                                        """
                                        Selecting 2 twice and 3 once produces 7.

                                        Selecting 7 once also reaches the target.

                                        Therefore, two unique combinations exist.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        3 8
                                        2 3 5
                                        """,
                                        """
                                        3
                                        2 2 2 2
                                        2 3 3
                                        3 5
                                        """,
                                        """
                                        Three unique non-decreasing combinations sum to 8.

                                        Reusing input values is allowed.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        2 1
                                        2 3
                                        """,
                                        "0",
                                        """
                                        Every available value is larger than the target.

                                        Therefore, no valid combination exists.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        1 6
                                        2
                                        """,
                                        """
                                        1
                                        2 2 2
                                        """,
                                        """
                                        The value 2 may be selected repeatedly.

                                        Selecting it three times produces the target 6.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        3 7
                                        5 3 2
                                        """,
                                        """
                                        2
                                        2 2 3
                                        2 5
                                        """,
                                        """
                                        After considering values in sorted order, two unique combinations sum to 7.

                                        They are 2 2 3 and 2 5.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "4 7\n2 3 6 7",
                                        "2\n2 2 3\n7",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "3 8\n2 3 5",
                                        "3\n2 2 2 2\n2 3 3\n3 5",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "2 1\n2 3",
                                        "0",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "1 6\n2",
                                        "1\n2 2 2",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "3 7\n5 3 2",
                                        "2\n2 2 3\n2 5",
                                        true
                                )
                        )
                ),

                // ==================================================
                // N-QUEENS
                // ==================================================

                new ProblemSeedData(

                        "N-Queens",

                        """
                        Given an integer n, place n queens on an n × n chessboard so that no two queens attack each other.

                        Two queens attack each other when they are placed in the same row, the same column, or the same diagonal.

                        Every valid board must contain exactly one queen in every row.

                        Represent a queen using Q and an empty cell using a period.

                        Print all valid board configurations in lexicographical order.

                        First print the total number of valid configurations.

                        For every configuration, print its n rows.

                        Print an empty line between consecutive configurations.

                        If no valid configuration exists, print only 0.
                        """,

                        Difficulty.HARD,

                        "backtracking",

                        """
                        1 <= n <= 10
                        """,

                        """
                        A single integer n representing the size of the chessboard and the number of queens.
                        """,

                        """
                        Print the total number of valid configurations on the first line.

                        Then print each board configuration.

                        Each configuration contains n lines.

                        Print one empty line between consecutive configurations.

                        If no solution exists, print only 0.
                        """,

                        "4",

                        """
                        2
                        .Q..
                        ...Q
                        Q...
                        ..Q.

                        ..Q.
                        Q...
                        ...Q
                        .Q..
                        """,

                        List.of(
                                "Backtracking",
                                "Recursion",
                                "Matrix"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        "4",
                                        """
                                        2
                                        .Q..
                                        ...Q
                                        Q...
                                        ..Q.

                                        ..Q.
                                        Q...
                                        ...Q
                                        .Q..
                                        """,
                                        """
                                        Exactly two arrangements allow four queens to be placed without sharing a row, column, or diagonal.

                                        Both valid boards are printed.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        "1",
                                        """
                                        1
                                        Q
                                        """,
                                        """
                                        A one-cell board can contain one queen safely.

                                        Therefore, exactly one solution exists.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        "2",
                                        "0",
                                        """
                                        It is impossible to place two queens on a 2 × 2 board without an attack.

                                        Therefore, no solution exists.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        "3",
                                        "0",
                                        """
                                        No arrangement of three queens on a 3 × 3 board avoids all attacks.

                                        Therefore, the answer is 0.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        "5",
                                        "10",
                                        """
                                        A 5 × 5 chessboard has exactly 10 valid N-Queens configurations.

                                        The first output line therefore contains 10, followed by all valid boards.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "4",
                                        "2\n.Q..\n...Q\nQ...\n..Q.\n\n..Q.\nQ...\n...Q\n.Q..",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "1",
                                        "1\nQ",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "2",
                                        "0",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "3",
                                        "0",
                                        true
                                )
                        )
                )
        );
    }
}