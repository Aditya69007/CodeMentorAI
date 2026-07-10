package com.codementor.backend.seed.problems;

import com.codementor.backend.entity.Difficulty;
import com.codementor.backend.seed.ProblemExampleSeedData;
import com.codementor.backend.seed.ProblemSeedData;
import com.codementor.backend.seed.TestCaseSeedData;

import java.util.List;

public final class GraphProblemSeeds {

    private GraphProblemSeeds() {
    }

    public static List<ProblemSeedData> getProblems() {

        return List.of(

                // ==================================================
                // 1. FIND DEGREE OF EACH VERTEX
                // ==================================================

                new ProblemSeedData(

                        "Find Degree of Each Vertex",

                        """
                        Given an undirected graph containing n vertices and m edges, find the degree of every vertex.

                        The vertices are numbered from 0 to n - 1.

                        The degree of a vertex is the number of edges connected to that vertex.

                        For every undirected edge connecting vertices u and v, the degree of both u and v increases by one.

                        An isolated vertex with no connected edges has degree 0.

                        Print the degree of every vertex from vertex 0 to vertex n - 1.
                        """,

                        Difficulty.EASY,

                        "graph",

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
                        Print n space-separated integers.

                        The value at position i represents the degree of vertex i.
                        """,

                        """
                        5 4
                        0 1
                        0 2
                        1 2
                        3 4
                        """,

                        "2 2 2 1 1",

                        List.of(
                                "Graph",
                                "Degree",
                                "Adjacency List"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        5 4
                                        0 1
                                        0 2
                                        1 2
                                        3 4
                                        """,
                                        "2 2 2 1 1",
                                        """
                                        Vertex 0 is connected to vertices 1 and 2, so its degree is 2.

                                        Vertex 1 is connected to vertices 0 and 2, so its degree is 2.

                                        Vertex 2 is connected to vertices 0 and 1, so its degree is 2.

                                        Vertices 3 and 4 are connected only to each other, so both have degree 1.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        4 0
                                        """,
                                        "0 0 0 0",
                                        """
                                        The graph contains no edges.

                                        Therefore, every vertex has degree 0.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        2 1
                                        0 1
                                        """,
                                        "1 1",
                                        """
                                        One edge connects the two vertices.

                                        Therefore, both vertices have degree 1.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        5 4
                                        0 1
                                        0 2
                                        0 3
                                        0 4
                                        """,
                                        "4 1 1 1 1",
                                        """
                                        Vertex 0 is connected to every other vertex and has degree 4.

                                        Every remaining vertex is connected only to vertex 0 and has degree 1.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        6 3
                                        0 1
                                        2 3
                                        4 5
                                        """,
                                        "1 1 1 1 1 1",
                                        """
                                        The graph contains three separate edges.

                                        Every vertex belongs to exactly one edge and therefore has degree 1.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "5 4\n0 1\n0 2\n1 2\n3 4",
                                        "2 2 2 1 1",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "4 0",
                                        "0 0 0 0",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "2 1\n0 1",
                                        "1 1",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "5 4\n0 1\n0 2\n0 3\n0 4",
                                        "4 1 1 1 1",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "6 3\n0 1\n2 3\n4 5",
                                        "1 1 1 1 1 1",
                                        true
                                )
                        )
                ),

                // ==================================================
                // 2. CHECK PATH BETWEEN TWO VERTICES
                // ==================================================

                new ProblemSeedData(

                        "Check Path Between Two Vertices",

                        """
                        Given an undirected graph containing n vertices and m edges, determine whether a path exists between a source vertex and a destination vertex.

                        The vertices are numbered from 0 to n - 1.

                        A path exists when the destination can be reached from the source by following zero or more graph edges.

                        You may use Breadth-First Search or Depth-First Search to explore the graph.

                        If the source and destination are the same vertex, a path always exists.

                        Print true if the destination is reachable from the source.

                        Otherwise, print false.
                        """,

                        Difficulty.EASY,

                        "graph",

                        """
                        1 <= n <= 100000

                        0 <= m <= 200000

                        0 <= u, v < n

                        0 <= source, destination < n

                        The graph is undirected.
                        """,

                        """
                        The first line contains two space-separated integers n and m.

                        The next m lines each contain two space-separated integers u and v representing an undirected edge.

                        The final line contains two space-separated integers source and destination.
                        """,

                        """
                        Print true if a path exists from the source to the destination.

                        Otherwise, print false.
                        """,

                        """
                        5 4
                        0 1
                        1 2
                        2 3
                        3 4
                        0 4
                        """,

                        "true",

                        List.of(
                                "Graph",
                                "Breadth-First Search",
                                "Depth-First Search"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        5 4
                                        0 1
                                        1 2
                                        2 3
                                        3 4
                                        0 4
                                        """,
                                        "true",
                                        """
                                        Starting from vertex 0, the path 0 -> 1 -> 2 -> 3 -> 4 reaches the destination.

                                        Therefore, the answer is true.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        5 2
                                        0 1
                                        3 4
                                        0 4
                                        """,
                                        "false",
                                        """
                                        Vertices 0 and 4 belong to different connected components.

                                        Therefore, no path exists between them.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        1 0
                                        0 0
                                        """,
                                        "true",
                                        """
                                        The source and destination are the same vertex.

                                        Therefore, a path exists without using any edges.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        6 5
                                        0 1
                                        1 2
                                        2 0
                                        3 4
                                        4 5
                                        3 5
                                        """,
                                        "true",
                                        """
                                        Vertices 3 and 5 belong to the same connected component.

                                        The path 3 -> 4 -> 5 reaches the destination.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        4 1
                                        1 2
                                        0 3
                                        """,
                                        "false",
                                        """
                                        Vertices 0 and 3 are isolated from each other.

                                        Therefore, the destination cannot be reached.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "5 4\n0 1\n1 2\n2 3\n3 4\n0 4",
                                        "true",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "5 2\n0 1\n3 4\n0 4",
                                        "false",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "1 0\n0 0",
                                        "true",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "6 5\n0 1\n1 2\n2 0\n3 4\n4 5\n3 5",
                                        "true",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "4 1\n1 2\n0 3",
                                        "false",
                                        true
                                )
                        )
                ),

                // ==================================================
                // 3. COURSE SCHEDULE
                // ==================================================

                new ProblemSeedData(

                        "Course Schedule",

                        """
                        You are given n courses numbered from 0 to n - 1 and a list of prerequisite relationships.

                        Each prerequisite relationship a b means that course b must be completed before course a can be taken.

                        Determine whether it is possible to complete all courses.

                        The prerequisite relationships form a directed graph.

                        If the graph contains a directed cycle, then the courses involved in that cycle depend on each other and it is impossible to complete all courses.

                        If the graph does not contain a directed cycle, all courses can be completed.

                        Print true if every course can be completed.

                        Otherwise, print false.
                        """,

                        Difficulty.MEDIUM,

                        "graph",

                        """
                        1 <= n <= 100000

                        0 <= m <= 200000

                        0 <= a, b < n

                        No prerequisite relationship appears more than once.
                        """,

                        """
                        The first line contains two space-separated integers n and m.

                        The next m lines each contain two space-separated integers a and b, meaning course b must be completed before course a.
                        """,

                        """
                        Print true if all courses can be completed.

                        Otherwise, print false.
                        """,

                        """
                        2 1
                        1 0
                        """,

                        "true",

                        List.of(
                                "Graph",
                                "Topological Sort",
                                "Cycle Detection",
                                "Directed Graph"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        2 1
                                        1 0
                                        """,
                                        "true",
                                        """
                                        Course 0 can be completed first.

                                        After completing course 0, course 1 can be completed.

                                        Therefore, all courses can be finished.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        2 2
                                        1 0
                                        0 1
                                        """,
                                        "false",
                                        """
                                        Course 1 requires course 0.

                                        Course 0 also requires course 1.

                                        This creates a directed cycle, so the courses cannot be completed.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        4 3
                                        1 0
                                        2 1
                                        3 2
                                        """,
                                        "true",
                                        """
                                        The courses can be completed in the order 0, 1, 2, 3.

                                        Therefore, no prerequisite cycle exists.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        3 3
                                        1 0
                                        2 1
                                        0 2
                                        """,
                                        "false",
                                        """
                                        The dependencies form the cycle 0 -> 1 -> 2 -> 0.

                                        Therefore, completing every course is impossible.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        5 0
                                        """,
                                        "true",
                                        """
                                        No prerequisite relationships exist.

                                        Therefore, every course can be completed independently.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "2 1\n1 0",
                                        "true",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "2 2\n1 0\n0 1",
                                        "false",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "4 3\n1 0\n2 1\n3 2",
                                        "true",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "3 3\n1 0\n2 1\n0 2",
                                        "false",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "5 0",
                                        "true",
                                        true
                                )
                        )
                ),

                // ==================================================
                // 4. NETWORK DELAY TIME
                // ==================================================

                new ProblemSeedData(

                        "Network Delay Time",

                        """
                        A network contains n nodes numbered from 1 to n.

                        You are given m directed connections.

                        Each connection contains three values u, v, and time, representing a directed edge from node u to node v that requires the given amount of time to travel.

                        A signal is sent from a starting node k.

                        Determine the minimum amount of time required for every node to receive the signal.

                        If some node cannot receive the signal, print -1.

                        The answer is the maximum shortest-path distance from the starting node to any other node.

                        All edge weights are positive.
                        """,

                        Difficulty.MEDIUM,

                        "graph",

                        """
                        1 <= n <= 10000

                        0 <= m <= 50000

                        1 <= u, v, k <= n

                        1 <= time <= 100000

                        The graph is directed.

                        Multiple edges between the same pair of vertices may exist.
                        """,

                        """
                        The first line contains two space-separated integers n and m.

                        The next m lines each contain three space-separated integers u, v, and time.

                        The final line contains the starting node k.
                        """,

                        """
                        Print the minimum time required for every node to receive the signal.

                        If any node is unreachable, print -1.
                        """,

                        """
                        4 3
                        2 1 1
                        2 3 1
                        3 4 1
                        2
                        """,

                        "2",

                        List.of(
                                "Graph",
                                "Shortest Path",
                                "Dijkstra Algorithm",
                                "Priority Queue"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        4 3
                                        2 1 1
                                        2 3 1
                                        3 4 1
                                        2
                                        """,
                                        "2",
                                        """
                                        Nodes 1 and 3 receive the signal after one unit of time.

                                        Node 4 receives the signal through node 3 after two units of time.

                                        Therefore, every node has received the signal after 2 units.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        2 1
                                        1 2 1
                                        1
                                        """,
                                        "1",
                                        """
                                        The signal travels directly from node 1 to node 2 in one unit of time.

                                        Therefore, the answer is 1.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        2 1
                                        1 2 1
                                        2
                                        """,
                                        "-1",
                                        """
                                        The directed edge allows travel from node 1 to node 2, but not from node 2 to node 1.

                                        Therefore, node 1 cannot receive the signal.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        1 0
                                        1
                                        """,
                                        "0",
                                        """
                                        The network contains only the starting node.

                                        Therefore, every node has already received the signal at time 0.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        3 3
                                        1 2 5
                                        1 3 2
                                        3 2 1
                                        1
                                        """,
                                        "3",
                                        """
                                        The direct path from node 1 to node 2 takes 5 units.

                                        A shorter path is 1 -> 3 -> 2, which takes 3 units.

                                        Node 3 receives the signal after 2 units.

                                        Therefore, every node has received the signal after 3 units.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "4 3\n2 1 1\n2 3 1\n3 4 1\n2",
                                        "2",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "2 1\n1 2 1\n1",
                                        "1",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "2 1\n1 2 1\n2",
                                        "-1",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "1 0\n1",
                                        "0",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "3 3\n1 2 5\n1 3 2\n3 2 1\n1",
                                        "3",
                                        true
                                )
                        )
                ),

                // ==================================================
                // 5. FIND CRITICAL CONNECTIONS
                // ==================================================

                new ProblemSeedData(

                        "Find Critical Connections",

                        """
                        Given a connected undirected graph containing n vertices and m edges, find every critical connection.

                        The vertices are numbered from 0 to n - 1.

                        A critical connection, also called a bridge, is an edge whose removal increases the number of connected components in the graph.

                        In other words, removing a critical connection disconnects some vertices that were previously reachable from each other.

                        Print all critical connections.

                        For each connection, print the smaller endpoint first.

                        Sort the final list of critical connections first by the first endpoint and then by the second endpoint.

                        An efficient solution can use Depth-First Search with discovery times and low-link values.
                        """,

                        Difficulty.HARD,

                        "graph",

                        """
                        2 <= n <= 100000

                        1 <= m <= 200000

                        0 <= u, v < n

                        The graph is connected and undirected.

                        The graph does not contain self-loops or duplicate edges.
                        """,

                        """
                        The first line contains two space-separated integers n and m.

                        The next m lines each contain two space-separated integers u and v representing an undirected edge.
                        """,

                        """
                        First print the number of critical connections.

                        Then print each critical connection on a separate line.

                        For each connection, print the smaller endpoint first.

                        Print the connections in sorted order.
                        """,

                        """
                        4 4
                        0 1
                        1 2
                        2 0
                        1 3
                        """,

                        """
                        1
                        1 3
                        """,

                        List.of(
                                "Graph",
                                "Depth-First Search",
                                "Bridge",
                                "Tarjan Algorithm"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        4 4
                                        0 1
                                        1 2
                                        2 0
                                        1 3
                                        """,
                                        """
                                        1
                                        1 3
                                        """,
                                        """
                                        Vertices 0, 1, and 2 form a cycle.

                                        Removing any edge from that cycle does not disconnect the graph.

                                        However, removing edge 1-3 separates vertex 3 from the remaining graph.

                                        Therefore, 1-3 is the only critical connection.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        3 3
                                        0 1
                                        1 2
                                        2 0
                                        """,
                                        "0",
                                        """
                                        Every edge belongs to a cycle.

                                        Removing any single edge leaves all vertices connected.

                                        Therefore, there are no critical connections.
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
                                        """
                                        3
                                        0 1
                                        1 2
                                        2 3
                                        """,
                                        """
                                        The graph forms a simple path.

                                        Removing any edge disconnects the graph.

                                        Therefore, all three edges are critical connections.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        2 1
                                        0 1
                                        """,
                                        """
                                        1
                                        0 1
                                        """,
                                        """
                                        The graph contains only one edge.

                                        Removing that edge disconnects the two vertices.

                                        Therefore, it is a critical connection.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        5 5
                                        0 1
                                        1 2
                                        2 0
                                        1 3
                                        3 4
                                        """,
                                        """
                                        2
                                        1 3
                                        3 4
                                        """,
                                        """
                                        Vertices 0, 1, and 2 form a cycle.

                                        Edge 1-3 is required to connect vertex 3 to that cycle.

                                        Edge 3-4 is required to connect vertex 4.

                                        Therefore, both edges are critical connections.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "4 4\n0 1\n1 2\n2 0\n1 3",
                                        "1\n1 3",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "3 3\n0 1\n1 2\n2 0",
                                        "0",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "4 3\n0 1\n1 2\n2 3",
                                        "3\n0 1\n1 2\n2 3",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "2 1\n0 1",
                                        "1\n0 1",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "5 5\n0 1\n1 2\n2 0\n1 3\n3 4",
                                        "2\n1 3\n3 4",
                                        true
                                )
                        )
                )
        );
    }
}