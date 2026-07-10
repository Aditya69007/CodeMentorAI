package com.codementor.backend.seed.problems;

import com.codementor.backend.entity.Difficulty;
import com.codementor.backend.seed.ProblemExampleSeedData;
import com.codementor.backend.seed.ProblemSeedData;
import com.codementor.backend.seed.TestCaseSeedData;

import java.util.List;

public final class DepthFirstSearchProblemSeeds {

    private DepthFirstSearchProblemSeeds() {
    }

    public static List<ProblemSeedData> getProblems() {

        return List.of(

                // ==================================================
                // 1. DFS TRAVERSAL OF GRAPH
                // ==================================================

                new ProblemSeedData(

                        "DFS Traversal of Graph",

                        """
                        Given an undirected graph containing n vertices and m edges, perform a Depth-First Search traversal starting from a given source vertex.

                        The vertices are numbered from 0 to n - 1.

                        Depth-First Search explores as far as possible along one path before returning to explore other unvisited paths.

                        When multiple unvisited neighbors are available, visit them in increasing numerical order.

                        Print the vertices in the order they are first visited.

                        Only vertices reachable from the source should be included in the traversal.
                        """,

                        Difficulty.EASY,

                        "depth-first-search",

                        """
                        1 <= n <= 100000

                        0 <= m <= 200000

                        0 <= u, v < n

                        0 <= source < n

                        The graph is undirected.

                        The graph does not contain self-loops or duplicate edges.
                        """,

                        """
                        The first line contains two space-separated integers n and m.

                        The next m lines each contain two space-separated integers u and v representing an undirected edge.

                        The final line contains the source vertex.
                        """,

                        """
                        Print the vertices visited by Depth-First Search as space-separated integers.
                        """,

                        """
                        5 5
                        0 1
                        0 2
                        1 3
                        1 4
                        2 4
                        0
                        """,

                        "0 1 3 4 2",

                        List.of(
                                "Depth-First Search",
                                "Graph",
                                "Recursion"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        5 5
                                        0 1
                                        0 2
                                        1 3
                                        1 4
                                        2 4
                                        0
                                        """,
                                        "0 1 3 4 2",
                                        """
                                        The traversal begins at vertex 0.

                                        Among its neighbors, vertex 1 is visited first.

                                        From vertex 1, DFS continues to vertex 3. After returning, it visits vertex 4 and then reaches vertex 2.

                                        Therefore, the traversal order is 0 1 3 4 2.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        1 0
                                        0
                                        """,
                                        "0",
                                        """
                                        The graph contains only one vertex.

                                        Therefore, the traversal contains only vertex 0.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        4 3
                                        0 1
                                        1 2
                                        2 3
                                        0
                                        """,
                                        "0 1 2 3",
                                        """
                                        The graph forms a single path.

                                        DFS follows that path until the final vertex is reached.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        6 3
                                        0 1
                                        1 2
                                        3 4
                                        0
                                        """,
                                        "0 1 2",
                                        """
                                        Only vertices 0, 1, and 2 are reachable from the source.

                                        Vertices in disconnected components are not visited.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        5 4
                                        0 4
                                        0 2
                                        0 1
                                        0 3
                                        0
                                        """,
                                        "0 1 2 3 4",
                                        """
                                        All remaining vertices are direct neighbors of vertex 0.

                                        Since neighbors are visited in increasing numerical order, DFS visits 1, 2, 3, and 4.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "5 5\n0 1\n0 2\n1 3\n1 4\n2 4\n0",
                                        "0 1 3 4 2",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "1 0\n0",
                                        "0",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "4 3\n0 1\n1 2\n2 3\n0",
                                        "0 1 2 3",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "6 3\n0 1\n1 2\n3 4\n0",
                                        "0 1 2",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "5 4\n0 4\n0 2\n0 1\n0 3\n0",
                                        "0 1 2 3 4",
                                        true
                                )
                        )
                ),

                // ==================================================
                // 2. COUNT CONNECTED COMPONENTS
                // ==================================================

                new ProblemSeedData(

                        "Count Connected Components",

                        """
                        Given an undirected graph containing n vertices and m edges, count the number of connected components.

                        The vertices are numbered from 0 to n - 1.

                        A connected component is a maximal group of vertices where every vertex can be reached from every other vertex in the same group.

                        An isolated vertex with no edges forms its own connected component.

                        Use graph traversal to explore every unvisited component.

                        Print the total number of connected components.
                        """,

                        Difficulty.EASY,

                        "depth-first-search",

                        """
                        1 <= n <= 100000

                        0 <= m <= 200000

                        0 <= u, v < n

                        The graph is undirected.

                        The graph does not contain self-loops or duplicate edges.
                        """,

                        """
                        The first line contains two space-separated integers n and m.

                        The next m lines each contain two space-separated integers u and v representing an undirected edge.
                        """,

                        """
                        Print the total number of connected components.
                        """,

                        """
                        5 3
                        0 1
                        1 2
                        3 4
                        """,

                        "2",

                        List.of(
                                "Depth-First Search",
                                "Graph",
                                "Connected Components"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        5 3
                                        0 1
                                        1 2
                                        3 4
                                        """,
                                        "2",
                                        """
                                        Vertices 0, 1, and 2 form one connected component.

                                        Vertices 3 and 4 form another connected component.

                                        Therefore, the graph contains two connected components.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        4 0
                                        """,
                                        "4",
                                        """
                                        No edges exist.

                                        Therefore, every vertex forms its own connected component.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        4 3
                                        0 1
                                        1 2
                                        2 3
                                        """,
                                        "1",
                                        """
                                        Every vertex can be reached from every other vertex.

                                        Therefore, the entire graph forms one connected component.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        1 0
                                        """,
                                        "1",
                                        """
                                        A graph containing one isolated vertex has one connected component.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        7 3
                                        0 1
                                        2 3
                                        4 5
                                        """,
                                        "4",
                                        """
                                        The pairs 0-1, 2-3, and 4-5 form three components.

                                        Vertex 6 is isolated and forms the fourth component.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "5 3\n0 1\n1 2\n3 4",
                                        "2",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "4 0",
                                        "4",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "4 3\n0 1\n1 2\n2 3",
                                        "1",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "1 0",
                                        "1",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "7 3\n0 1\n2 3\n4 5",
                                        "4",
                                        true
                                )
                        )
                ),

                // ==================================================
                // 3. DETECT CYCLE IN UNDIRECTED GRAPH
                // ==================================================

                new ProblemSeedData(

                        "Detect Cycle in Undirected Graph",

                        """
                        Given an undirected graph containing n vertices and m edges, determine whether the graph contains a cycle.

                        The vertices are numbered from 0 to n - 1.

                        A cycle exists when it is possible to start from a vertex, follow a sequence of edges, and return to the starting vertex without using the same edge twice.

                        While performing Depth-First Search, keep track of the parent vertex.

                        If an already visited adjacent vertex is found and that vertex is not the current vertex's parent, then a cycle exists.

                        The graph may contain multiple disconnected components.

                        Print true if any component contains a cycle.

                        Otherwise, print false.
                        """,

                        Difficulty.MEDIUM,

                        "depth-first-search",

                        """
                        1 <= n <= 100000

                        0 <= m <= 200000

                        0 <= u, v < n

                        The graph is undirected.

                        The graph does not contain self-loops or duplicate edges.
                        """,

                        """
                        The first line contains two space-separated integers n and m.

                        The next m lines each contain two space-separated integers u and v representing an undirected edge.
                        """,

                        """
                        Print true if the graph contains a cycle.

                        Otherwise, print false.
                        """,

                        """
                        4 4
                        0 1
                        1 2
                        2 0
                        2 3
                        """,

                        "true",

                        List.of(
                                "Depth-First Search",
                                "Graph",
                                "Cycle Detection"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        4 4
                                        0 1
                                        1 2
                                        2 0
                                        2 3
                                        """,
                                        "true",
                                        """
                                        Vertices 0, 1, and 2 form the cycle 0 -> 1 -> 2 -> 0.

                                        Therefore, the graph contains a cycle.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        4 3
                                        0 1
                                        1 2
                                        2 3
                                        """,
                                        "false",
                                        """
                                        The graph forms a simple path.

                                        No sequence of edges returns to an earlier vertex without reusing an edge.

                                        Therefore, no cycle exists.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        3 3
                                        0 1
                                        1 2
                                        0 2
                                        """,
                                        "true",
                                        """
                                        The three vertices form a triangle.

                                        Therefore, the graph contains a cycle.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        5 2
                                        0 1
                                        3 4
                                        """,
                                        "false",
                                        """
                                        The graph contains disconnected components, but none contains a cycle.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        6 5
                                        0 1
                                        2 3
                                        3 4
                                        4 2
                                        4 5
                                        """,
                                        "true",
                                        """
                                        The component containing vertices 2, 3, and 4 contains a cycle.

                                        Therefore, the entire graph is considered cyclic.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "4 4\n0 1\n1 2\n2 0\n2 3",
                                        "true",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "4 3\n0 1\n1 2\n2 3",
                                        "false",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "3 3\n0 1\n1 2\n0 2",
                                        "true",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "5 2\n0 1\n3 4",
                                        "false",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "6 5\n0 1\n2 3\n3 4\n4 2\n4 5",
                                        "true",
                                        true
                                )
                        )
                ),

                // ==================================================
                // 4. SURROUNDED REGIONS
                // ==================================================

                new ProblemSeedData(

                        "Surrounded Regions",

                        """
                        Given a rectangular board containing only the characters X and O, capture every region of O cells that is completely surrounded by X cells.

                        Two O cells belong to the same region when they are connected horizontally or vertically.

                        An O cell should not be captured if it is located on the boundary or is connected to another O cell that can reach the boundary.

                        Replace every completely surrounded O with X.

                        Print the final board after all surrounded regions have been captured.
                        """,

                        Difficulty.MEDIUM,

                        "depth-first-search",

                        """
                        1 <= rows, columns <= 500

                        Every board cell contains either X or O.
                        """,

                        """
                        The first line contains two space-separated integers rows and columns.

                        The next rows lines each contain a string of length columns representing one row of the board.
                        """,

                        """
                        Print the final board after capturing all surrounded regions.

                        Print each row on a separate line.
                        """,

                        """
                        4 4
                        XXXX
                        XOOX
                        XXOX
                        XOXX
                        """,

                        """
                        XXXX
                        XXXX
                        XXXX
                        XOXX
                        """,

                        List.of(
                                "Depth-First Search",
                                "Graph",
                                "Matrix"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        4 4
                                        XXXX
                                        XOOX
                                        XXOX
                                        XOXX
                                        """,
                                        """
                                        XXXX
                                        XXXX
                                        XXXX
                                        XOXX
                                        """,
                                        """
                                        The O cells in the center cannot reach the board boundary and are completely surrounded.

                                        Therefore, they are replaced with X.

                                        The O in the final row touches the boundary and remains unchanged.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        1 1
                                        O
                                        """,
                                        "O",
                                        """
                                        The only cell is located on the boundary.

                                        Therefore, it cannot be captured.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        3 3
                                        XXX
                                        XOX
                                        XXX
                                        """,
                                        """
                                        XXX
                                        XXX
                                        XXX
                                        """,
                                        """
                                        The center O is completely surrounded by X cells.

                                        Therefore, it is captured.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        3 4
                                        OOOO
                                        OXXO
                                        OOOO
                                        """,
                                        """
                                        OOOO
                                        OXXO
                                        OOOO
                                        """,
                                        """
                                        Every O is located on the boundary or connected to a boundary O.

                                        Therefore, no cell is captured.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        5 5
                                        XXXXX
                                        XOOOX
                                        XOXXX
                                        XOOOX
                                        XXXXX
                                        """,
                                        """
                                        XXXXX
                                        XXXXX
                                        XXXXX
                                        XXXXX
                                        XXXXX
                                        """,
                                        """
                                        Every O belongs to a region completely enclosed by X cells.

                                        Therefore, all O cells are captured.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "4 4\nXXXX\nXOOX\nXXOX\nXOXX",
                                        "XXXX\nXXXX\nXXXX\nXOXX",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "1 1\nO",
                                        "O",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "3 3\nXXX\nXOX\nXXX",
                                        "XXX\nXXX\nXXX",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "3 4\nOOOO\nOXXO\nOOOO",
                                        "OOOO\nOXXO\nOOOO",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "5 5\nXXXXX\nXOOOX\nXOXXX\nXOOOX\nXXXXX",
                                        "XXXXX\nXXXXX\nXXXXX\nXXXXX\nXXXXX",
                                        true
                                )
                        )
                ),

                // ==================================================
                // 5. LONGEST INCREASING PATH IN MATRIX
                // ==================================================

                new ProblemSeedData(

                        "Longest Increasing Path in Matrix",

                        """
                        Given a rectangular matrix of integers, find the length of the longest strictly increasing path.

                        From each cell, you may move one position up, down, left, or right.

                        Diagonal movement is not allowed.

                        You may move from the current cell to an adjacent cell only when the adjacent value is strictly greater than the current value.

                        A cell cannot appear more than once in the same path.

                        Print the maximum number of cells that can appear in a valid increasing path.

                        An efficient solution can combine Depth-First Search with memoization so that the longest path beginning from each cell is calculated only once.
                        """,

                        Difficulty.HARD,

                        "depth-first-search",

                        """
                        1 <= rows, columns <= 200

                        1 <= rows * columns <= 40000

                        -1000000000 <= matrix[i][j] <= 1000000000
                        """,

                        """
                        The first line contains two space-separated integers rows and columns.

                        The next rows lines each contain columns space-separated integers representing the matrix.
                        """,

                        """
                        Print the length of the longest strictly increasing path.
                        """,

                        """
                        3 3
                        9 9 4
                        6 6 8
                        2 1 1
                        """,

                        "4",

                        List.of(
                                "Depth-First Search",
                                "Dynamic Programming",
                                "Memoization",
                                "Matrix"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        3 3
                                        9 9 4
                                        6 6 8
                                        2 1 1
                                        """,
                                        "4",
                                        """
                                        One longest increasing path is 1 -> 2 -> 6 -> 9.

                                        This path contains four cells.

                                        Therefore, the answer is 4.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        3 3
                                        3 4 5
                                        3 2 6
                                        2 2 1
                                        """,
                                        "4",
                                        """
                                        One longest increasing path is 3 -> 4 -> 5 -> 6.

                                        Therefore, the maximum path length is 4.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        1 1
                                        10
                                        """,
                                        "1",
                                        """
                                        The matrix contains only one cell.

                                        Therefore, the longest increasing path has length 1.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        1 5
                                        1 2 3 4 5
                                        """,
                                        "5",
                                        """
                                        Moving from left to right visits every cell in strictly increasing order.

                                        Therefore, the path length is 5.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        2 3
                                        6 5 4
                                        1 2 3
                                        """,
                                        "6",
                                        """
                                        A valid path is 1 -> 2 -> 3 -> 4 -> 5 -> 6.

                                        Every consecutive value is located horizontally or vertically adjacent.

                                        Therefore, the longest path contains six cells.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "3 3\n9 9 4\n6 6 8\n2 1 1",
                                        "4",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "3 3\n3 4 5\n3 2 6\n2 2 1",
                                        "4",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "1 1\n10",
                                        "1",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "1 5\n1 2 3 4 5",
                                        "5",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "2 3\n6 5 4\n1 2 3",
                                        "6",
                                        true
                                )
                        )
                )
        );
    }
}