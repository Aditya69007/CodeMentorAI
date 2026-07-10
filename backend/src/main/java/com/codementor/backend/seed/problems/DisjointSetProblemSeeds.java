package com.codementor.backend.seed.problems;

import com.codementor.backend.entity.Difficulty;
import com.codementor.backend.seed.ProblemExampleSeedData;
import com.codementor.backend.seed.ProblemSeedData;
import com.codementor.backend.seed.TestCaseSeedData;

import java.util.List;

public final class DisjointSetProblemSeeds {

    private DisjointSetProblemSeeds() {
    }

    public static List<ProblemSeedData> getProblems() {

        return List.of(

                // ==================================================
                // 1. COUNT CONNECTED COMPONENTS USING UNION-FIND
                // ==================================================

                new ProblemSeedData(

                        "Count Components Using Union-Find",

                        """
                        Given an undirected graph containing n vertices and m edges, find the total number of connected components.

                        The vertices are numbered from 0 to n - 1.

                        A connected component is a maximal group of vertices where every vertex can be reached from every other vertex in the same group.

                        Use the Disjoint Set Union data structure to process the edges.

                        Initially, every vertex belongs to its own separate set.

                        For every edge connecting vertices u and v, merge the sets containing u and v.

                        After processing all edges, print the total number of distinct connected components.

                        An isolated vertex with no edges forms its own connected component.
                        """,

                        Difficulty.EASY,

                        "disjoint-set",

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
                                "Disjoint Set",
                                "Union-Find",
                                "Graph"
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
                                        Vertices 0, 1, and 2 become part of one set.

                                        Vertices 3 and 4 become part of another set.

                                        Therefore, two connected components remain.
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

                                        Therefore, every vertex remains in its own set and four components exist.
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
                                        Processing the edges merges all four vertices into one set.

                                        Therefore, the graph contains one connected component.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        1 0
                                        """,
                                        "1",
                                        """
                                        The graph contains one isolated vertex.

                                        Therefore, exactly one component exists.
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

                                        Vertex 6 remains isolated and forms the fourth component.
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
                // 2. CHECK GRAPH CONNECTIVITY
                // ==================================================

                new ProblemSeedData(

                        "Check Graph Connectivity",

                        """
                        Given an undirected graph containing n vertices and m edges, determine whether every vertex belongs to the same connected component.

                        The vertices are numbered from 0 to n - 1.

                        Use the Disjoint Set Union data structure to process the graph.

                        Initially, every vertex belongs to its own separate set.

                        For every edge, merge the sets containing its two endpoints.

                        After processing all edges, print true if all vertices belong to one set.

                        Otherwise, print false.
                        """,

                        Difficulty.EASY,

                        "disjoint-set",

                        """
                        1 <= n <= 100000

                        0 <= m <= 200000

                        0 <= u, v < n

                        The graph is undirected.
                        """,

                        """
                        The first line contains two space-separated integers n and m.

                        The next m lines each contain two space-separated integers u and v representing an undirected edge.
                        """,

                        """
                        Print true if the entire graph is connected.

                        Otherwise, print false.
                        """,

                        """
                        4 3
                        0 1
                        1 2
                        2 3
                        """,

                        "true",

                        List.of(
                                "Disjoint Set",
                                "Union-Find",
                                "Graph"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        4 3
                                        0 1
                                        1 2
                                        2 3
                                        """,
                                        "true",
                                        """
                                        Every vertex becomes part of the same set after processing the edges.

                                        Therefore, the graph is connected.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        5 2
                                        0 1
                                        3 4
                                        """,
                                        "false",
                                        """
                                        The graph contains multiple separate components.

                                        Therefore, not every vertex is connected.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        1 0
                                        """,
                                        "true",
                                        """
                                        A graph containing only one vertex is connected.
                                        """,
                                        3
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
                                        All three vertices belong to the same connected component.

                                        Therefore, the answer is true.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        6 4
                                        0 1
                                        1 2
                                        2 3
                                        4 5
                                        """,
                                        "false",
                                        """
                                        Vertices 0, 1, 2, and 3 form one component.

                                        Vertices 4 and 5 form another component.

                                        Therefore, the graph is not connected.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "4 3\n0 1\n1 2\n2 3",
                                        "true",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "5 2\n0 1\n3 4",
                                        "false",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "1 0",
                                        "true",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "3 3\n0 1\n1 2\n0 2",
                                        "true",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "6 4\n0 1\n1 2\n2 3\n4 5",
                                        "false",
                                        true
                                )
                        )
                ),

                // ==================================================
                // 3. REDUNDANT CONNECTION
                // ==================================================

                new ProblemSeedData(

                        "Redundant Connection",

                        """
                        An undirected graph originally formed a tree containing n vertices.

                        One additional edge was then added to the tree, creating exactly one cycle.

                        The vertices are numbered from 1 to n.

                        Find the additional edge that creates the cycle.

                        Process the edges in the order they are given.

                        Using Disjoint Set Union, an edge is redundant when both of its endpoints already belong to the same connected component before the edge is processed.

                        Print the two endpoints of the redundant edge.
                        """,

                        Difficulty.MEDIUM,

                        "disjoint-set",

                        """
                        3 <= n <= 100000

                        Exactly n edges are provided.

                        1 <= u, v <= n

                        The graph contains exactly one cycle.
                        """,

                        """
                        The first line contains an integer n representing the number of vertices and edges.

                        The next n lines each contain two space-separated integers u and v representing an undirected edge.
                        """,

                        """
                        Print the two endpoints of the redundant edge.
                        """,

                        """
                        3
                        1 2
                        1 3
                        2 3
                        """,

                        "2 3",

                        List.of(
                                "Disjoint Set",
                                "Union-Find",
                                "Graph",
                                "Cycle Detection"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        3
                                        1 2
                                        1 3
                                        2 3
                                        """,
                                        "2 3",
                                        """
                                        After processing the first two edges, vertices 2 and 3 are already connected through vertex 1.

                                        Therefore, adding edge 2-3 creates a cycle.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        5
                                        1 2
                                        2 3
                                        3 4
                                        1 4
                                        1 5
                                        """,
                                        "1 4",
                                        """
                                        Before edge 1-4 is processed, vertices 1 and 4 are already connected through 2 and 3.

                                        Therefore, edge 1-4 is redundant.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        4
                                        1 2
                                        2 3
                                        3 4
                                        4 1
                                        """,
                                        "4 1",
                                        """
                                        The first three edges form a path connecting all vertices.

                                        Adding edge 4-1 closes the cycle.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        4
                                        1 2
                                        1 3
                                        1 4
                                        3 4
                                        """,
                                        "3 4",
                                        """
                                        Vertices 3 and 4 are already connected through vertex 1.

                                        Therefore, edge 3-4 creates the cycle.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        6
                                        1 2
                                        2 3
                                        3 4
                                        4 5
                                        5 6
                                        2 6
                                        """,
                                        "2 6",
                                        """
                                        Before processing the final edge, vertices 2 and 6 are already connected.

                                        Therefore, edge 2-6 is redundant.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "3\n1 2\n1 3\n2 3",
                                        "2 3",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "5\n1 2\n2 3\n3 4\n1 4\n1 5",
                                        "1 4",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "4\n1 2\n2 3\n3 4\n4 1",
                                        "4 1",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "4\n1 2\n1 3\n1 4\n3 4",
                                        "3 4",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "6\n1 2\n2 3\n3 4\n4 5\n5 6\n2 6",
                                        "2 6",
                                        true
                                )
                        )
                ),

                // ==================================================
                // 4. NUMBER OF PROVINCES
                // ==================================================

                new ProblemSeedData(

                        "Number of Provinces",

                        """
                        There are n cities.

                        Some cities are directly connected to other cities.

                        Two cities belong to the same province if they are directly or indirectly connected.

                        You are given an n by n connection matrix.

                        If matrix[i][j] is 1, city i and city j are directly connected.

                        If matrix[i][j] is 0, no direct connection exists between them.

                        Use Disjoint Set Union to merge directly connected cities.

                        Print the total number of provinces.
                        """,

                        Difficulty.MEDIUM,

                        "disjoint-set",

                        """
                        1 <= n <= 500

                        matrix[i][j] is either 0 or 1.

                        matrix[i][i] = 1.

                        matrix[i][j] = matrix[j][i].
                        """,

                        """
                        The first line contains an integer n.

                        The next n lines each contain n space-separated integers representing the connection matrix.
                        """,

                        """
                        Print the total number of provinces.
                        """,

                        """
                        3
                        1 1 0
                        1 1 0
                        0 0 1
                        """,

                        "2",

                        List.of(
                                "Disjoint Set",
                                "Union-Find",
                                "Graph",
                                "Matrix"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        3
                                        1 1 0
                                        1 1 0
                                        0 0 1
                                        """,
                                        "2",
                                        """
                                        Cities 0 and 1 are connected and belong to one province.

                                        City 2 forms another province.

                                        Therefore, two provinces exist.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        3
                                        1 0 0
                                        0 1 0
                                        0 0 1
                                        """,
                                        "3",
                                        """
                                        No city is connected to a different city.

                                        Therefore, each city forms its own province.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        4
                                        1 1 0 0
                                        1 1 1 0
                                        0 1 1 1
                                        0 0 1 1
                                        """,
                                        "1",
                                        """
                                        Every city is directly or indirectly connected.

                                        Therefore, all cities belong to one province.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        1
                                        1
                                        """,
                                        "1",
                                        """
                                        A single city forms exactly one province.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        5
                                        1 1 0 0 0
                                        1 1 0 0 0
                                        0 0 1 1 0
                                        0 0 1 1 0
                                        0 0 0 0 1
                                        """,
                                        "3",
                                        """
                                        Cities 0 and 1 form the first province.

                                        Cities 2 and 3 form the second province.

                                        City 4 forms the third province.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "3\n1 1 0\n1 1 0\n0 0 1",
                                        "2",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "3\n1 0 0\n0 1 0\n0 0 1",
                                        "3",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "4\n1 1 0 0\n1 1 1 0\n0 1 1 1\n0 0 1 1",
                                        "1",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "1\n1",
                                        "1",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "5\n1 1 0 0 0\n1 1 0 0 0\n0 0 1 1 0\n0 0 1 1 0\n0 0 0 0 1",
                                        "3",
                                        true
                                )
                        )
                ),

                // ==================================================
                // 5. MINIMUM COST TO CONNECT ALL POINTS
                // ==================================================

                new ProblemSeedData(

                        "Minimum Cost to Connect All Points",

                        """
                        Given n points on a two-dimensional plane, connect all points so that every point can reach every other point.

                        The cost of connecting two points is their Manhattan distance.

                        For points (x1, y1) and (x2, y2), the Manhattan distance is:

                        |x1 - x2| + |y1 - y2|

                        Find the minimum total cost required to connect all points.

                        A valid solution forms a Minimum Spanning Tree.

                        One possible approach is to generate edges between points, process them in increasing order of cost, and use Disjoint Set Union to determine whether an edge should be included.

                        Print the minimum total connection cost.
                        """,

                        Difficulty.HARD,

                        "disjoint-set",

                        """
                        1 <= n <= 1000

                        -1000000 <= x[i], y[i] <= 1000000

                        All points are distinct.
                        """,

                        """
                        The first line contains an integer n.

                        The next n lines each contain two space-separated integers x and y representing one point.
                        """,

                        """
                        Print the minimum total cost required to connect all points.
                        """,

                        """
                        5
                        0 0
                        2 2
                        3 10
                        5 2
                        7 0
                        """,

                        "20",

                        List.of(
                                "Disjoint Set",
                                "Union-Find",
                                "Graph",
                                "Minimum Spanning Tree",
                                "Kruskal Algorithm"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        5
                                        0 0
                                        2 2
                                        3 10
                                        5 2
                                        7 0
                                        """,
                                        "20",
                                        """
                                        The points can be connected using edges whose total Manhattan distance is 20.

                                        No spanning tree with a smaller total cost exists.

                                        Therefore, the minimum cost is 20.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        3
                                        3 12
                                        -2 5
                                        -4 1
                                        """,
                                        "18",
                                        """
                                        Connecting all three points with minimum total Manhattan distance requires cost 18.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        1
                                        10 20
                                        """,
                                        "0",
                                        """
                                        Only one point exists.

                                        No edges are required, so the total cost is 0.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        2
                                        0 0
                                        5 5
                                        """,
                                        "10",
                                        """
                                        The Manhattan distance between the two points is 10.

                                        Since only one connection is required, the minimum total cost is 10.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        4
                                        0 0
                                        0 1
                                        1 0
                                        1 1
                                        """,
                                        "3",
                                        """
                                        Three edges of cost 1 are sufficient to connect all four points.

                                        Therefore, the minimum total cost is 3.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "5\n0 0\n2 2\n3 10\n5 2\n7 0",
                                        "20",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "3\n3 12\n-2 5\n-4 1",
                                        "18",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "1\n10 20",
                                        "0",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "2\n0 0\n5 5",
                                        "10",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "4\n0 0\n0 1\n1 0\n1 1",
                                        "3",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "3\n0 0\n10 0\n20 0",
                                        "20",
                                        true
                                )
                        )
                )
        );
    }
}