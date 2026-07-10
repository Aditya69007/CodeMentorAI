package com.codementor.backend.seed.problems;

import com.codementor.backend.entity.Difficulty;
import com.codementor.backend.seed.ProblemExampleSeedData;
import com.codementor.backend.seed.ProblemSeedData;
import com.codementor.backend.seed.TestCaseSeedData;

import java.util.List;

public final class TreeProblemSeeds {

    private TreeProblemSeeds() {
    }

    public static List<ProblemSeedData> getProblems() {

        return List.of(

                // ==================================================
                // 1. MAXIMUM DEPTH OF BINARY TREE
                // ==================================================

                new ProblemSeedData(

                        "Maximum Depth of Binary Tree",

                        """
                        Given the level-order traversal of a binary tree, find the maximum depth of the tree.

                        The maximum depth is the number of nodes along the longest path from the root node down to the farthest leaf node.

                        The input represents the binary tree in level-order traversal.

                        The value null represents a missing child.

                        Print the maximum depth of the binary tree.
                        """,

                        Difficulty.EASY,

                        "tree",

                        """
                        1 <= number of input values <= 100000

                        -1000000000 <= node value <= 1000000000

                        Each input value is either an integer or null.

                        The first value represents the root of the tree.
                        """,

                        """
                        The first line contains an integer n representing the number of values in the level-order representation.

                        The second line contains n space-separated values.

                        Each value is either an integer or null.
                        """,

                        """
                        Print the maximum depth of the binary tree.
                        """,

                        """
                        7
                        3 9 20 null null 15 7
                        """,

                        "3",

                        List.of(
                                "Tree",
                                "Binary Tree",
                                "Depth-First Search",
                                "Breadth-First Search"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        7
                                        3 9 20 null null 15 7
                                        """,
                                        "3",
                                        """
                                        The root node 3 is at depth 1.

                                        Nodes 9 and 20 are at depth 2.

                                        Nodes 15 and 7 are at depth 3.

                                        Therefore, the maximum depth is 3.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        2
                                        1 2
                                        """,
                                        "2",
                                        """
                                        The root node is at depth 1.

                                        Its child is at depth 2.

                                        Therefore, the maximum depth is 2.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        1
                                        10
                                        """,
                                        "1",
                                        """
                                        The tree contains only the root node.

                                        Therefore, the maximum depth is 1.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        5
                                        1 2 null 3 null
                                        """,
                                        "3",
                                        """
                                        The longest root-to-leaf path contains the nodes 1, 2, and 3.

                                        Therefore, the maximum depth is 3.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        7
                                        1 2 3 4 5 6 7
                                        """,
                                        "3",
                                        """
                                        The binary tree contains three levels.

                                        Therefore, the maximum depth is 3.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "7\n3 9 20 null null 15 7",
                                        "3",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "2\n1 2",
                                        "2",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "1\n10",
                                        "1",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "5\n1 2 null 3 null",
                                        "3",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "7\n1 2 3 4 5 6 7",
                                        "3",
                                        true
                                )
                        )
                ),

                // ==================================================
                // 2. COUNT LEAF NODES
                // ==================================================

                new ProblemSeedData(

                        "Count Leaf Nodes",

                        """
                        Given the level-order traversal of a binary tree, count the number of leaf nodes.

                        A leaf node is a node that does not have a left child or a right child.

                        The input represents the binary tree using level-order traversal.

                        The value null represents a missing child.

                        Print the total number of leaf nodes in the tree.
                        """,

                        Difficulty.EASY,

                        "tree",

                        """
                        1 <= number of input values <= 100000

                        -1000000000 <= node value <= 1000000000

                        Each input value is either an integer or null.
                        """,

                        """
                        The first line contains an integer n.

                        The second line contains n space-separated values representing the tree in level-order traversal.

                        Each value is either an integer or null.
                        """,

                        """
                        Print the total number of leaf nodes.
                        """,

                        """
                        7
                        1 2 3 4 5 null 6
                        """,

                        "3",

                        List.of(
                                "Tree",
                                "Binary Tree",
                                "Traversal"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        7
                                        1 2 3 4 5 null 6
                                        """,
                                        "3",
                                        """
                                        Nodes 4, 5, and 6 do not have any children.

                                        Therefore, the tree contains 3 leaf nodes.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        1
                                        10
                                        """,
                                        "1",
                                        """
                                        The root has no children.

                                        Therefore, the root itself is a leaf node.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        3
                                        1 2 3
                                        """,
                                        "2",
                                        """
                                        Nodes 2 and 3 have no children.

                                        Therefore, the answer is 2.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        5
                                        1 2 null 3 null
                                        """,
                                        "1",
                                        """
                                        Only node 3 has no children.

                                        Therefore, the tree contains one leaf node.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        7
                                        1 2 3 4 5 6 7
                                        """,
                                        "4",
                                        """
                                        Nodes 4, 5, 6, and 7 are leaf nodes.

                                        Therefore, the answer is 4.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "7\n1 2 3 4 5 null 6",
                                        "3",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "1\n10",
                                        "1",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "3\n1 2 3",
                                        "2",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "5\n1 2 null 3 null",
                                        "1",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "7\n1 2 3 4 5 6 7",
                                        "4",
                                        true
                                )
                        )
                ),

                // ==================================================
                // 3. BINARY TREE LEVEL ORDER TRAVERSAL
                // ==================================================

                new ProblemSeedData(

                        "Binary Tree Level Order Traversal",

                        """
                        Given the level-order representation of a binary tree, print the nodes level by level.

                        Nodes belonging to the same level must be printed on the same line.

                        Begin with the root level and continue downward until every node has been visited.

                        The value null represents a missing child and must not appear in the output.

                        Use Breadth-First Search to process the tree one level at a time.
                        """,

                        Difficulty.MEDIUM,

                        "tree",

                        """
                        1 <= number of input values <= 100000

                        -1000000000 <= node value <= 1000000000

                        Each input value is either an integer or null.
                        """,

                        """
                        The first line contains an integer n.

                        The second line contains n space-separated values representing the binary tree in level-order traversal.
                        """,

                        """
                        Print the nodes of each tree level on a separate line.

                        Values on the same level must be separated by spaces.
                        """,

                        """
                        7
                        3 9 20 null null 15 7
                        """,

                        """
                        3
                        9 20
                        15 7
                        """,

                        List.of(
                                "Tree",
                                "Binary Tree",
                                "Breadth-First Search",
                                "Queue"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        7
                                        3 9 20 null null 15 7
                                        """,
                                        """
                                        3
                                        9 20
                                        15 7
                                        """,
                                        """
                                        The root forms the first level.

                                        Nodes 9 and 20 form the second level.

                                        Nodes 15 and 7 form the third level.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        1
                                        1
                                        """,
                                        "1",
                                        """
                                        The tree contains only one node.

                                        Therefore, only one level is printed.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        3
                                        1 2 3
                                        """,
                                        """
                                        1
                                        2 3
                                        """,
                                        """
                                        Node 1 forms the first level.

                                        Nodes 2 and 3 form the second level.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        7
                                        1 2 3 4 5 6 7
                                        """,
                                        """
                                        1
                                        2 3
                                        4 5 6 7
                                        """,
                                        """
                                        The complete binary tree contains three levels.

                                        Each level is printed on a separate line.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        5
                                        1 2 null 3 null
                                        """,
                                        """
                                        1
                                        2
                                        3
                                        """,
                                        """
                                        The tree contains one node at each level along its longest branch.

                                        Therefore, the values are printed on three separate lines.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "7\n3 9 20 null null 15 7",
                                        "3\n9 20\n15 7",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "1\n1",
                                        "1",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "3\n1 2 3",
                                        "1\n2 3",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "7\n1 2 3 4 5 6 7",
                                        "1\n2 3\n4 5 6 7",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "5\n1 2 null 3 null",
                                        "1\n2\n3",
                                        true
                                )
                        )
                ),

                // ==================================================
                // 4. LOWEST COMMON ANCESTOR OF BINARY TREE
                // ==================================================

                new ProblemSeedData(

                        "Lowest Common Ancestor of Binary Tree",

                        """
                        Given a binary tree and two different node values p and q, find their lowest common ancestor.

                        The lowest common ancestor is the lowest node in the tree that has both p and q as descendants.

                        A node is allowed to be a descendant of itself.

                        All node values in the tree are unique.

                        Both p and q are guaranteed to exist in the tree.

                        Print the value of the lowest common ancestor.
                        """,

                        Difficulty.MEDIUM,

                        "tree",

                        """
                        2 <= number of non-null nodes <= 100000

                        -1000000000 <= node value <= 1000000000

                        All node values are unique.

                        p != q

                        Both p and q exist in the tree.
                        """,

                        """
                        The first line contains an integer n.

                        The second line contains n space-separated values representing the binary tree in level-order traversal.

                        The third line contains two space-separated integers p and q.
                        """,

                        """
                        Print the value of the lowest common ancestor of p and q.
                        """,

                        """
                        11
                        3 5 1 6 2 0 8 null null 7 4
                        5 1
                        """,

                        "3",

                        List.of(
                                "Tree",
                                "Binary Tree",
                                "Depth-First Search",
                                "Lowest Common Ancestor"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        11
                                        3 5 1 6 2 0 8 null null 7 4
                                        5 1
                                        """,
                                        "3",
                                        """
                                        Node 5 belongs to the left subtree of node 3.

                                        Node 1 belongs to the right subtree of node 3.

                                        Therefore, node 3 is their lowest common ancestor.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        11
                                        3 5 1 6 2 0 8 null null 7 4
                                        5 4
                                        """,
                                        "5",
                                        """
                                        Node 4 is a descendant of node 5.

                                        Since a node may be a descendant of itself, node 5 is the lowest common ancestor.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        3
                                        1 2 3
                                        2 3
                                        """,
                                        "1",
                                        """
                                        Nodes 2 and 3 are children of the root.

                                        Therefore, the root node 1 is their lowest common ancestor.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        7
                                        1 2 3 4 5 6 7
                                        4 5
                                        """,
                                        "2",
                                        """
                                        Nodes 4 and 5 are both children of node 2.

                                        Therefore, their lowest common ancestor is 2.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        7
                                        1 2 3 4 5 6 7
                                        4 7
                                        """,
                                        "1",
                                        """
                                        Node 4 belongs to the left subtree.

                                        Node 7 belongs to the right subtree.

                                        Therefore, the root node 1 is their lowest common ancestor.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "11\n3 5 1 6 2 0 8 null null 7 4\n5 1",
                                        "3",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "11\n3 5 1 6 2 0 8 null null 7 4\n5 4",
                                        "5",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "3\n1 2 3\n2 3",
                                        "1",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "7\n1 2 3 4 5 6 7\n4 5",
                                        "2",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "7\n1 2 3 4 5 6 7\n4 7",
                                        "1",
                                        true
                                )
                        )
                ),

                // ==================================================
                // 5. BINARY TREE MAXIMUM PATH SUM
                // ==================================================

                new ProblemSeedData(

                        "Binary Tree Maximum Path Sum",

                        """
                        Given a non-empty binary tree, find the maximum possible sum of values along any path.

                        A path is a sequence of connected nodes where every pair of consecutive nodes is connected by an edge.

                        A node may appear at most once in a path.

                        The path does not need to pass through the root.

                        The path may begin and end at any nodes in the tree.

                        Node values may be negative.

                        Print the maximum path sum.
                        """,

                        Difficulty.HARD,

                        "tree",

                        """
                        1 <= number of non-null nodes <= 100000

                        -1000000000 <= node value <= 1000000000

                        The tree contains at least one node.
                        """,

                        """
                        The first line contains an integer n.

                        The second line contains n space-separated values representing the binary tree in level-order traversal.

                        Each value is either an integer or null.
                        """,

                        """
                        Print the maximum possible path sum.
                        """,

                        """
                        7
                        -10 9 20 null null 15 7
                        """,

                        "42",

                        List.of(
                                "Tree",
                                "Binary Tree",
                                "Depth-First Search",
                                "Dynamic Programming"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        7
                                        -10 9 20 null null 15 7
                                        """,
                                        "42",
                                        """
                                        The maximum-sum path is 15 -> 20 -> 7.

                                        The total sum is 15 + 20 + 7 = 42.

                                        Therefore, the maximum path sum is 42.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        3
                                        1 2 3
                                        """,
                                        "6",
                                        """
                                        The maximum path is 2 -> 1 -> 3.

                                        Its total sum is 6.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        1
                                        -3
                                        """,
                                        "-3",
                                        """
                                        The tree contains only one node.

                                        Therefore, the only possible path has sum -3.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        3
                                        -2 -1 -3
                                        """,
                                        "-1",
                                        """
                                        Every node value is negative.

                                        The best path contains only the node with the greatest value, which is -1.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        7
                                        5 4 8 11 null 13 4
                                        """,
                                        "48",
                                        """
                                        One maximum path is 11 -> 4 -> 5 -> 8 -> 13.

                                        Its total sum is 48.

                                        Therefore, the maximum path sum is 48.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "7\n-10 9 20 null null 15 7",
                                        "42",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "3\n1 2 3",
                                        "6",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "1\n-3",
                                        "-3",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "3\n-2 -1 -3",
                                        "-1",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "7\n5 4 8 11 null 13 4",
                                        "48",
                                        true
                                )
                        )
                )
        );
    }
}