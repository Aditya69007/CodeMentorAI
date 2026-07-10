package com.codementor.backend.seed.problems;

import com.codementor.backend.entity.Difficulty;
import com.codementor.backend.seed.ProblemExampleSeedData;
import com.codementor.backend.seed.ProblemSeedData;
import com.codementor.backend.seed.TestCaseSeedData;

import java.util.List;

public final class BreadthFirstSearchProblemSeeds {

    private BreadthFirstSearchProblemSeeds() {
    }

    public static List<ProblemSeedData> getProblems() {

        return List.of(

                // ==================================================
                // 1. BFS TRAVERSAL OF GRAPH
                // ==================================================

                new ProblemSeedData(

                        "BFS Traversal of Graph",

                        """
                        Given an undirected graph containing n vertices and m edges, perform a Breadth-First Search traversal starting from a given source vertex.

                        The vertices are numbered from 0 to n - 1.

                        Breadth-First Search explores vertices level by level. It first visits the source vertex, then all unvisited neighbors of the source, followed by their unvisited neighbors, and so on.

                        When multiple unvisited neighbors are available, visit them in increasing numerical order.

                        Print the vertices in the order they are visited.

                        Only vertices reachable from the source should be included in the traversal.
                        """,

                        Difficulty.EASY,

                        "breadth-first-search",

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
                        Print the vertices visited by Breadth-First Search as space-separated integers.
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

                        "0 1 2 3 4",

                        List.of(
                                "Breadth-First Search",
                                "Graph",
                                "Queue"
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
                                        "0 1 2 3 4",
                                        """
                                        The traversal begins at vertex 0.

                                        Its neighbors 1 and 2 are visited next.

                                        Vertex 1 discovers vertices 3 and 4.

                                        Vertex 2 does not add any new vertex because 4 has already been discovered.

                                        Therefore, the BFS order is 0 1 2 3 4.
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

                                        Therefore, the traversal contains only the source vertex 0.
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
                                        The graph forms a path.

                                        Starting from 0, BFS visits each next vertex level by level.
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

                                        Vertices 3, 4, and 5 belong to disconnected components and are not visited.
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

                                        Because neighbors must be processed in increasing numerical order, they are visited as 1, 2, 3, and 4.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "5 5\n0 1\n0 2\n1 3\n1 4\n2 4\n0",
                                        "0 1 2 3 4",
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
                // 2. SHORTEST PATH IN UNWEIGHTED GRAPH
                // ==================================================

                new ProblemSeedData(

                        "Shortest Path in Unweighted Graph",

                        """
                        Given an undirected and unweighted graph containing n vertices and m edges, find the minimum number of edges required to travel from a source vertex to a destination vertex.

                        The vertices are numbered from 0 to n - 1.

                        Since every edge has equal cost, Breadth-First Search can be used to discover vertices in increasing order of their distance from the source.

                        If the destination cannot be reached from the source, print -1.
                        """,

                        Difficulty.EASY,

                        "breadth-first-search",

                        """
                        1 <= n <= 100000

                        0 <= m <= 200000

                        0 <= u, v < n

                        0 <= source, destination < n

                        The graph is undirected and unweighted.
                        """,

                        """
                        The first line contains two space-separated integers n and m.

                        The next m lines each contain two integers u and v representing an undirected edge.

                        The final line contains two space-separated integers source and destination.
                        """,

                        """
                        Print the minimum number of edges required to reach the destination from the source.

                        If the destination is unreachable, print -1.
                        """,

                        """
                        6 6
                        0 1
                        0 2
                        1 3
                        2 3
                        3 4
                        4 5
                        0 5
                        """,

                        "4",

                        List.of(
                                "Breadth-First Search",
                                "Graph",
                                "Shortest Path"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        6 6
                                        0 1
                                        0 2
                                        1 3
                                        2 3
                                        3 4
                                        4 5
                                        0 5
                                        """,
                                        "4",
                                        """
                                        One shortest path is 0 -> 1 -> 3 -> 4 -> 5.

                                        This path contains four edges.

                                        Therefore, the shortest distance is 4.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        3 2
                                        0 1
                                        1 2
                                        0 2
                                        """,
                                        "2",
                                        """
                                        The shortest path is 0 -> 1 -> 2.

                                        It contains two edges.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        4 1
                                        0 1
                                        0 3
                                        """,
                                        "-1",
                                        """
                                        Vertex 3 belongs to a different disconnected component.

                                        Therefore, it cannot be reached from vertex 0.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        5 4
                                        0 1
                                        1 2
                                        2 3
                                        3 4
                                        2 2
                                        """,
                                        "0",
                                        """
                                        The source and destination are the same vertex.

                                        Therefore, zero edges are required.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        5 5
                                        0 1
                                        1 2
                                        2 4
                                        0 3
                                        3 4
                                        0 4
                                        """,
                                        "2",
                                        """
                                        The path 0 -> 3 -> 4 reaches the destination using two edges.

                                        Therefore, the shortest distance is 2.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "6 6\n0 1\n0 2\n1 3\n2 3\n3 4\n4 5\n0 5",
                                        "4",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "3 2\n0 1\n1 2\n0 2",
                                        "2",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "4 1\n0 1\n0 3",
                                        "-1",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "5 4\n0 1\n1 2\n2 3\n3 4\n2 2",
                                        "0",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "5 5\n0 1\n1 2\n2 4\n0 3\n3 4\n0 4",
                                        "2",
                                        true
                                )
                        )
                ),

                // ==================================================
                // 3. NUMBER OF ISLANDS
                // ==================================================

                new ProblemSeedData(

                        "Number of Islands",

                        """
                        Given a rectangular grid containing only 0 and 1, count the number of islands.

                        A cell containing 1 represents land, while a cell containing 0 represents water.

                        An island is a group of land cells connected horizontally or vertically.

                        Diagonal connections do not belong to the same island.

                        Each land cell belongs to exactly one island.

                        Print the total number of islands in the grid.
                        """,

                        Difficulty.MEDIUM,

                        "breadth-first-search",

                        """
                        1 <= rows, columns <= 500

                        Each grid value is either 0 or 1.
                        """,

                        """
                        The first line contains two space-separated integers rows and columns.

                        The next rows lines each contain columns space-separated integers representing the grid.
                        """,

                        """
                        Print the total number of islands.
                        """,

                        """
                        4 5
                        1 1 0 0 0
                        1 1 0 0 0
                        0 0 1 0 0
                        0 0 0 1 1
                        """,

                        "3",

                        List.of(
                                "Breadth-First Search",
                                "Graph",
                                "Matrix"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        4 5
                                        1 1 0 0 0
                                        1 1 0 0 0
                                        0 0 1 0 0
                                        0 0 0 1 1
                                        """,
                                        "3",
                                        """
                                        The top-left group of four land cells forms the first island.

                                        The single center land cell forms the second island.

                                        The two connected bottom-right land cells form the third island.

                                        Therefore, the grid contains three islands.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        3 3
                                        0 0 0
                                        0 0 0
                                        0 0 0
                                        """,
                                        "0",
                                        """
                                        The grid contains no land cells.

                                        Therefore, there are no islands.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        2 3
                                        1 1 1
                                        1 1 1
                                        """,
                                        "1",
                                        """
                                        Every land cell is connected horizontally or vertically.

                                        Therefore, all land belongs to one island.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        3 3
                                        1 0 1
                                        0 1 0
                                        1 0 1
                                        """,
                                        "5",
                                        """
                                        None of the land cells are connected horizontally or vertically.

                                        Diagonal connections do not count.

                                        Therefore, each land cell forms a separate island.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        1 5
                                        1 0 1 0 1
                                        """,
                                        "3",
                                        """
                                        Three separate land cells are divided by water.

                                        Therefore, the grid contains three islands.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "4 5\n1 1 0 0 0\n1 1 0 0 0\n0 0 1 0 0\n0 0 0 1 1",
                                        "3",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "3 3\n0 0 0\n0 0 0\n0 0 0",
                                        "0",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "2 3\n1 1 1\n1 1 1",
                                        "1",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "3 3\n1 0 1\n0 1 0\n1 0 1",
                                        "5",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "1 5\n1 0 1 0 1",
                                        "3",
                                        true
                                )
                        )
                ),

                // ==================================================
                // 4. ROTTING ORANGES
                // ==================================================

                new ProblemSeedData(

                        "Rotting Oranges",

                        """
                        Given a rectangular grid representing oranges, determine the minimum number of minutes required until no fresh orange remains.

                        Each cell contains one of three values:

                        0 represents an empty cell.

                        1 represents a fresh orange.

                        2 represents a rotten orange.

                        Every minute, a fresh orange becomes rotten if it is directly adjacent horizontally or vertically to a rotten orange.

                        Multiple oranges can become rotten during the same minute.

                        If all fresh oranges cannot become rotten, print -1.

                        If no fresh orange exists initially, print 0.
                        """,

                        Difficulty.MEDIUM,

                        "breadth-first-search",

                        """
                        1 <= rows, columns <= 500

                        Each grid value is 0, 1, or 2.
                        """,

                        """
                        The first line contains two space-separated integers rows and columns.

                        The next rows lines each contain columns space-separated integers representing the grid.
                        """,

                        """
                        Print the minimum number of minutes required until no fresh orange remains.

                        If this is impossible, print -1.
                        """,

                        """
                        3 3
                        2 1 1
                        1 1 0
                        0 1 1
                        """,

                        "4",

                        List.of(
                                "Breadth-First Search",
                                "Graph",
                                "Matrix",
                                "Multi-Source BFS"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        3 3
                                        2 1 1
                                        1 1 0
                                        0 1 1
                                        """,
                                        "4",
                                        """
                                        The infection spreads outward from the initially rotten orange.

                                        The final fresh orange becomes rotten after four minutes.

                                        Therefore, the answer is 4.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        3 3
                                        2 1 1
                                        0 1 1
                                        1 0 1
                                        """,
                                        "-1",
                                        """
                                        The fresh orange in the bottom-left corner cannot be reached from any rotten orange.

                                        Therefore, it is impossible to rot every orange.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        1 2
                                        0 2
                                        """,
                                        "0",
                                        """
                                        No fresh oranges exist initially.

                                        Therefore, zero minutes are required.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        2 2
                                        2 1
                                        1 1
                                        """,
                                        "2",
                                        """
                                        After one minute, the two fresh oranges adjacent to the rotten orange become rotten.

                                        After the second minute, the final fresh orange becomes rotten.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        2 3
                                        2 2 2
                                        2 2 2
                                        """,
                                        "0",
                                        """
                                        Every orange is already rotten.

                                        Therefore, no additional time is required.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "3 3\n2 1 1\n1 1 0\n0 1 1",
                                        "4",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "3 3\n2 1 1\n0 1 1\n1 0 1",
                                        "-1",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "1 2\n0 2",
                                        "0",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "2 2\n2 1\n1 1",
                                        "2",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "2 3\n2 2 2\n2 2 2",
                                        "0",
                                        true
                                )
                        )
                ),

                // ==================================================
                // 5. SHORTEST PATH WITH OBSTACLE ELIMINATION
                // ==================================================

                new ProblemSeedData(

                        "Shortest Path with Obstacle Elimination",

                        """
                        Given a rectangular grid containing 0 and 1, find the minimum number of moves required to travel from the top-left cell to the bottom-right cell.

                        A cell containing 0 is empty and can be entered normally.

                        A cell containing 1 is an obstacle.

                        You may eliminate at most k obstacles while traveling through the grid.

                        From each cell, you may move one position up, down, left, or right.

                        The starting cell and destination cell are empty.

                        If the destination cannot be reached even after eliminating at most k obstacles, print -1.

                        The state of the search must consider both the current cell and the number of obstacle eliminations already used.
                        """,

                        Difficulty.HARD,

                        "breadth-first-search",

                        """
                        1 <= rows, columns <= 40

                        0 <= k <= rows * columns

                        Each grid value is either 0 or 1.

                        The top-left and bottom-right cells contain 0.
                        """,

                        """
                        The first line contains three space-separated integers rows, columns, and k.

                        The next rows lines each contain columns space-separated integers representing the grid.
                        """,

                        """
                        Print the minimum number of moves required to reach the bottom-right cell.

                        If the destination cannot be reached, print -1.
                        """,

                        """
                        5 3 1
                        0 0 0
                        1 1 0
                        0 0 0
                        0 1 1
                        0 0 0
                        """,

                        "6",

                        List.of(
                                "Breadth-First Search",
                                "Graph",
                                "Matrix",
                                "Shortest Path"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        5 3 1
                                        0 0 0
                                        1 1 0
                                        0 0 0
                                        0 1 1
                                        0 0 0
                                        """,
                                        "6",
                                        """
                                        By eliminating at most one obstacle, the destination can be reached in six moves.

                                        Breadth-First Search explores paths in increasing order of their length, so the first valid arrival gives the shortest distance.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        3 3 0
                                        0 0 0
                                        0 0 0
                                        0 0 0
                                        """,
                                        "4",
                                        """
                                        No obstacles exist.

                                        The shortest route from the top-left corner to the bottom-right corner requires four moves.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        3 3 0
                                        0 1 0
                                        1 1 0
                                        0 0 0
                                        """,
                                        "-1",
                                        """
                                        The starting cell is blocked from all possible routes by obstacles.

                                        Since no obstacle may be eliminated, the destination is unreachable.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        1 1 0
                                        0
                                        """,
                                        "0",
                                        """
                                        The starting cell is also the destination.

                                        Therefore, zero moves are required.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        3 4 2
                                        0 1 1 0
                                        0 1 1 0
                                        0 0 0 0
                                        """,
                                        "5",
                                        """
                                        A valid shortest route reaches the destination in five moves.

                                        The available obstacle eliminations allow blocked cells to be crossed when useful.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "5 3 1\n0 0 0\n1 1 0\n0 0 0\n0 1 1\n0 0 0",
                                        "6",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "3 3 0\n0 0 0\n0 0 0\n0 0 0",
                                        "4",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "3 3 0\n0 1 0\n1 1 0\n0 0 0",
                                        "-1",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "1 1 0\n0",
                                        "0",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "3 4 2\n0 1 1 0\n0 1 1 0\n0 0 0 0",
                                        "5",
                                        true
                                )
                        )
                )
        );
    }
}