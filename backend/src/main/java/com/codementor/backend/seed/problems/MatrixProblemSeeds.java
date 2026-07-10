package com.codementor.backend.seed.problems;

import com.codementor.backend.entity.Difficulty;
import com.codementor.backend.seed.ProblemExampleSeedData;
import com.codementor.backend.seed.ProblemSeedData;
import com.codementor.backend.seed.TestCaseSeedData;

import java.util.List;

public final class MatrixProblemSeeds {

    private MatrixProblemSeeds() {
    }

    public static List<ProblemSeedData> getProblems() {

        return List.of(

                // ==================================================
                // 1. MATRIX DIAGONAL SUM
                // ==================================================

                new ProblemSeedData(

                        "Matrix Diagonal Sum",

                        """
                        Given a square matrix of size n by n, calculate the sum of all elements on the primary diagonal and the secondary diagonal.

                        The primary diagonal contains the elements where the row index and column index are equal.

                        The secondary diagonal contains the elements where the sum of the row index and column index equals n - 1.

                        If the matrix has an odd size, the center element belongs to both diagonals.

                        The center element must be counted only once.

                        Print the total diagonal sum.
                        """,

                        Difficulty.EASY,

                        "matrix",

                        """
                        1 <= n <= 1000

                        -1000000 <= matrix[i][j] <= 1000000
                        """,

                        """
                        The first line contains an integer n representing the size of the square matrix.

                        The next n lines each contain n space-separated integers representing the matrix.
                        """,

                        """
                        Print the sum of all elements on the primary and secondary diagonals.

                        Count the center element only once when n is odd.
                        """,

                        """
                        3
                        1 2 3
                        4 5 6
                        7 8 9
                        """,

                        "25",

                        List.of(
                                "Matrix",
                                "Simulation"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        3
                                        1 2 3
                                        4 5 6
                                        7 8 9
                                        """,
                                        "25",
                                        """
                                        The primary diagonal contains 1, 5, and 9.

                                        The secondary diagonal contains 3, 5, and 7.

                                        The center value 5 belongs to both diagonals, so it is counted only once.

                                        Therefore, the total sum is 1 + 5 + 9 + 3 + 7 = 25.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        1
                                        10
                                        """,
                                        "10",
                                        """
                                        The matrix contains only one element.

                                        That element belongs to both diagonals but must be counted only once.

                                        Therefore, the answer is 10.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        2
                                        1 2
                                        3 4
                                        """,
                                        "10",
                                        """
                                        The primary diagonal contains 1 and 4.

                                        The secondary diagonal contains 2 and 3.

                                        Therefore, the total sum is 10.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        3
                                        0 0 0
                                        0 0 0
                                        0 0 0
                                        """,
                                        "0",
                                        """
                                        Every diagonal element is 0.

                                        Therefore, the diagonal sum is 0.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        3
                                        -1 2 -3
                                        4 -5 6
                                        -7 8 -9
                                        """,
                                        "-25",
                                        """
                                        The unique diagonal elements are -1, -3, -5, -7, and -9.

                                        Their total sum is -25.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "3\n1 2 3\n4 5 6\n7 8 9",
                                        "25",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "1\n10",
                                        "10",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "2\n1 2\n3 4",
                                        "10",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "3\n0 0 0\n0 0 0\n0 0 0",
                                        "0",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "3\n-1 2 -3\n4 -5 6\n-7 8 -9",
                                        "-25",
                                        true
                                )
                        )
                ),

                // ==================================================
                // 2. TRANSPOSE MATRIX
                // ==================================================

                new ProblemSeedData(

                        "Transpose Matrix",

                        """
                        Given a matrix containing rows rows and columns columns, find its transpose.

                        The transpose of a matrix is created by converting every row into a column.

                        More precisely, the element located at row i and column j in the original matrix appears at row j and column i in the transposed matrix.

                        A matrix with rows rows and columns columns becomes a matrix with columns rows and rows columns after transposition.

                        Print the transposed matrix.
                        """,

                        Difficulty.EASY,

                        "matrix",

                        """
                        1 <= rows, columns <= 1000

                        1 <= rows * columns <= 1000000

                        -1000000000 <= matrix[i][j] <= 1000000000
                        """,

                        """
                        The first line contains two space-separated integers rows and columns.

                        The next rows lines each contain columns space-separated integers representing the matrix.
                        """,

                        """
                        Print the transposed matrix.

                        The output must contain columns rows, with each row containing rows space-separated integers.
                        """,

                        """
                        2 3
                        1 2 3
                        4 5 6
                        """,

                        """
                        1 4
                        2 5
                        3 6
                        """,

                        List.of(
                                "Matrix",
                                "Simulation"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        2 3
                                        1 2 3
                                        4 5 6
                                        """,
                                        """
                                        1 4
                                        2 5
                                        3 6
                                        """,
                                        """
                                        The first column of the original matrix becomes the first row of the transpose.

                                        The second column becomes the second row.

                                        The third column becomes the third row.

                                        Therefore, the resulting matrix has 3 rows and 2 columns.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        1 3
                                        1 2 3
                                        """,
                                        """
                                        1
                                        2
                                        3
                                        """,
                                        """
                                        The original matrix contains one row and three columns.

                                        After transposition, it contains three rows and one column.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        3 1
                                        1
                                        2
                                        3
                                        """,
                                        "1 2 3",
                                        """
                                        The original matrix contains three rows and one column.

                                        After transposition, it contains one row and three columns.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        2 2
                                        1 2
                                        3 4
                                        """,
                                        """
                                        1 3
                                        2 4
                                        """,
                                        """
                                        The rows and columns are exchanged.

                                        Therefore, the transposed matrix is 1 3 on the first row and 2 4 on the second row.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        1 1
                                        7
                                        """,
                                        "7",
                                        """
                                        A one-by-one matrix remains unchanged after transposition.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "2 3\n1 2 3\n4 5 6",
                                        "1 4\n2 5\n3 6",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "1 3\n1 2 3",
                                        "1\n2\n3",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "3 1\n1\n2\n3",
                                        "1 2 3",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "2 2\n1 2\n3 4",
                                        "1 3\n2 4",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "1 1\n7",
                                        "7",
                                        true
                                )
                        )
                ),

                // ==================================================
                // 3. SPIRAL MATRIX TRAVERSAL
                // ==================================================

                new ProblemSeedData(

                        "Spiral Matrix Traversal",

                        """
                        Given a rectangular matrix, print all elements in spiral order.

                        Begin from the top-left corner.

                        First move from left to right across the top row.

                        Then move downward along the rightmost unvisited column.

                        Next move from right to left across the bottommost unvisited row.

                        Then move upward along the leftmost unvisited column.

                        Continue this process while moving toward the center of the matrix until every element has been visited exactly once.

                        Print the elements in the order they are visited.
                        """,

                        Difficulty.MEDIUM,

                        "matrix",

                        """
                        1 <= rows, columns <= 1000

                        1 <= rows * columns <= 1000000

                        -1000000000 <= matrix[i][j] <= 1000000000
                        """,

                        """
                        The first line contains two space-separated integers rows and columns.

                        The next rows lines each contain columns space-separated integers representing the matrix.
                        """,

                        """
                        Print all matrix elements in spiral order as space-separated integers.
                        """,

                        """
                        3 3
                        1 2 3
                        4 5 6
                        7 8 9
                        """,

                        "1 2 3 6 9 8 7 4 5",

                        List.of(
                                "Matrix",
                                "Simulation"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        3 3
                                        1 2 3
                                        4 5 6
                                        7 8 9
                                        """,
                                        "1 2 3 6 9 8 7 4 5",
                                        """
                                        Traverse the top row from left to right: 1, 2, 3.

                                        Move downward through 6 and 9.

                                        Move left through 8 and 7.

                                        Move upward to 4.

                                        Finally, visit the center element 5.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        3 4
                                        1 2 3 4
                                        5 6 7 8
                                        9 10 11 12
                                        """,
                                        "1 2 3 4 8 12 11 10 9 5 6 7",
                                        """
                                        The outer boundary is visited first.

                                        After completing the outer layer, the remaining elements 6 and 7 are visited.

                                        Therefore, all elements appear exactly once in spiral order.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        1 4
                                        1 2 3 4
                                        """,
                                        "1 2 3 4",
                                        """
                                        The matrix contains only one row.

                                        Therefore, the spiral traversal is the same as the original row.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        4 1
                                        1
                                        2
                                        3
                                        4
                                        """,
                                        "1 2 3 4",
                                        """
                                        The matrix contains only one column.

                                        Therefore, the traversal proceeds directly from top to bottom.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        2 2
                                        1 2
                                        3 4
                                        """,
                                        "1 2 4 3",
                                        """
                                        Visit the top row, then the right side, and finally the remaining bottom-left element.

                                        Therefore, the traversal is 1 2 4 3.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "3 3\n1 2 3\n4 5 6\n7 8 9",
                                        "1 2 3 6 9 8 7 4 5",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "3 4\n1 2 3 4\n5 6 7 8\n9 10 11 12",
                                        "1 2 3 4 8 12 11 10 9 5 6 7",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "1 4\n1 2 3 4",
                                        "1 2 3 4",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "4 1\n1\n2\n3\n4",
                                        "1 2 3 4",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "2 2\n1 2\n3 4",
                                        "1 2 4 3",
                                        true
                                )
                        )
                ),

                // ==================================================
                // 4. SET MATRIX ZEROES
                // ==================================================

                new ProblemSeedData(

                        "Set Matrix Zeroes",

                        """
                        Given a matrix containing rows rows and columns columns, modify the matrix according to the following rule.

                        If any element is equal to 0, set every element in that element's row and column to 0.

                        The changes must be based on the positions of zeroes in the original matrix.

                        A zero created during the modification process must not cause additional rows or columns to become zero.

                        Print the final matrix after applying all required changes.

                        Try to design a solution that uses constant additional space.
                        """,

                        Difficulty.MEDIUM,

                        "matrix",

                        """
                        1 <= rows, columns <= 1000

                        1 <= rows * columns <= 1000000

                        -1000000000 <= matrix[i][j] <= 1000000000
                        """,

                        """
                        The first line contains two space-separated integers rows and columns.

                        The next rows lines each contain columns space-separated integers representing the matrix.
                        """,

                        """
                        Print the final matrix after setting the required rows and columns to zero.
                        """,

                        """
                        3 3
                        1 1 1
                        1 0 1
                        1 1 1
                        """,

                        """
                        1 0 1
                        0 0 0
                        1 0 1
                        """,

                        List.of(
                                "Matrix",
                                "Array",
                                "In-Place Algorithm"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        3 3
                                        1 1 1
                                        1 0 1
                                        1 1 1
                                        """,
                                        """
                                        1 0 1
                                        0 0 0
                                        1 0 1
                                        """,
                                        """
                                        The original zero is located at row 1 and column 1.

                                        Therefore, every element in row 1 and column 1 becomes zero.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        3 4
                                        0 1 2 0
                                        3 4 5 2
                                        1 3 1 5
                                        """,
                                        """
                                        0 0 0 0
                                        0 4 5 0
                                        0 3 1 0
                                        """,
                                        """
                                        The original matrix contains zeroes at positions (0, 0) and (0, 3).

                                        Therefore, row 0 and columns 0 and 3 are set to zero.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        1 1
                                        5
                                        """,
                                        "5",
                                        """
                                        The matrix contains no zero.

                                        Therefore, it remains unchanged.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        2 2
                                        0 1
                                        1 1
                                        """,
                                        """
                                        0 0
                                        0 1
                                        """,
                                        """
                                        The zero in the first row and first column causes that entire row and column to become zero.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        2 3
                                        1 2 3
                                        4 5 6
                                        """,
                                        """
                                        1 2 3
                                        4 5 6
                                        """,
                                        """
                                        No zero exists in the original matrix.

                                        Therefore, no element is changed.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "3 3\n1 1 1\n1 0 1\n1 1 1",
                                        "1 0 1\n0 0 0\n1 0 1",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "3 4\n0 1 2 0\n3 4 5 2\n1 3 1 5",
                                        "0 0 0 0\n0 4 5 0\n0 3 1 0",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "1 1\n5",
                                        "5",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "2 2\n0 1\n1 1",
                                        "0 0\n0 1",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "2 3\n1 2 3\n4 5 6",
                                        "1 2 3\n4 5 6",
                                        true
                                )
                        )
                ),

                // ==================================================
                // 5. MAXIMAL RECTANGLE
                // ==================================================

                new ProblemSeedData(

                        "Maximal Rectangle",

                        """
                        Given a binary matrix containing only 0 and 1, find the area of the largest rectangle containing only 1 values.

                        The sides of the rectangle must be parallel to the sides of the matrix.

                        For every row, the matrix can be viewed as a histogram.

                        The height of each histogram column represents the number of consecutive 1 values ending at the current row.

                        An efficient solution can process every row and calculate the largest rectangle in the corresponding histogram using a monotonic stack.

                        Print the maximum rectangular area containing only 1 values.
                        """,

                        Difficulty.HARD,

                        "matrix",

                        """
                        1 <= rows, columns <= 500

                        matrix[i][j] is either 0 or 1.
                        """,

                        """
                        The first line contains two space-separated integers rows and columns.

                        The next rows lines each contain columns space-separated integers representing the binary matrix.
                        """,

                        """
                        Print the area of the largest rectangle containing only 1 values.
                        """,

                        """
                        4 5
                        1 0 1 0 0
                        1 0 1 1 1
                        1 1 1 1 1
                        1 0 0 1 0
                        """,

                        "6",

                        List.of(
                                "Matrix",
                                "Stack",
                                "Monotonic Stack",
                                "Histogram"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        4 5
                                        1 0 1 0 0
                                        1 0 1 1 1
                                        1 1 1 1 1
                                        1 0 0 1 0
                                        """,
                                        "6",
                                        """
                                        The largest rectangle of 1 values spans two rows and three columns.

                                        Its area is 2 × 3 = 6.

                                        No larger all-ones rectangle exists.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        1 1
                                        0
                                        """,
                                        "0",
                                        """
                                        The matrix contains no cell with value 1.

                                        Therefore, no valid rectangle exists and the answer is 0.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        1 1
                                        1
                                        """,
                                        "1",
                                        """
                                        The only cell contains 1.

                                        Therefore, the largest rectangle has area 1.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        2 3
                                        1 1 1
                                        1 1 1
                                        """,
                                        "6",
                                        """
                                        Every cell contains 1.

                                        Therefore, the entire 2 by 3 matrix forms a valid rectangle with area 6.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        3 4
                                        1 0 1 1
                                        1 0 1 1
                                        1 0 1 1
                                        """,
                                        "6",
                                        """
                                        The final two columns contain 1 values across all three rows.

                                        They form a rectangle of height 3 and width 2.

                                        Therefore, the maximum area is 6.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "4 5\n1 0 1 0 0\n1 0 1 1 1\n1 1 1 1 1\n1 0 0 1 0",
                                        "6",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "1 1\n0",
                                        "0",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "1 1\n1",
                                        "1",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "2 3\n1 1 1\n1 1 1",
                                        "6",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "3 4\n1 0 1 1\n1 0 1 1\n1 0 1 1",
                                        "6",
                                        true
                                )
                        )
                )
        );
    }
}