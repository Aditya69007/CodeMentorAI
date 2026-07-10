package com.codementor.backend.seed.problems;

import com.codementor.backend.entity.Difficulty;
import com.codementor.backend.seed.ProblemExampleSeedData;
import com.codementor.backend.seed.ProblemSeedData;
import com.codementor.backend.seed.TestCaseSeedData;

import java.util.List;

public final class BinarySearchTreeProblemSeeds {

    private BinarySearchTreeProblemSeeds() {
    }

    public static List<ProblemSeedData> getProblems() {

        return List.of(

                // ==================================================
                // 1. SEARCH IN A BINARY SEARCH TREE
                // ==================================================

                new ProblemSeedData(

                        "Search in a Binary Search Tree",

                        """
                        Given a Binary Search Tree containing n distinct integers and a target value, determine whether the target exists in the tree.

                        In a Binary Search Tree, every value in the left subtree of a node is smaller than the node's value, and every value in the right subtree is greater than the node's value.

                        The tree is created by inserting the given values from left to right into an initially empty Binary Search Tree.

                        Print true if the target exists in the tree.

                        Otherwise, print false.
                        """,

                        Difficulty.EASY,

                        "binary-search-tree",

                        """
                        1 <= n <= 100000

                        -1000000000 <= value[i] <= 1000000000

                        -1000000000 <= target <= 1000000000

                        All tree values are distinct.
                        """,

                        """
                        The first line contains an integer n representing the number of nodes.

                        The second line contains n space-separated distinct integers in insertion order.

                        The third line contains the target value.
                        """,

                        """
                        Print true if the target exists in the Binary Search Tree.

                        Otherwise, print false.
                        """,

                        """
                        7
                        8 3 10 1 6 14 4
                        6
                        """,

                        "true",

                        List.of(
                                "Binary Search Tree",
                                "Tree",
                                "Searching"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        7
                                        8 3 10 1 6 14 4
                                        6
                                        """,
                                        "true",
                                        """
                                        Starting at 8, the target 6 is smaller, so the search moves left to 3.

                                        Since 6 is greater than 3, the search moves right and finds node 6.

                                        Therefore, the answer is true.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        7
                                        8 3 10 1 6 14 4
                                        7
                                        """,
                                        "false",
                                        """
                                        Following the Binary Search Tree ordering rules does not lead to a node containing 7.

                                        Therefore, the target does not exist.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        1
                                        5
                                        5
                                        """,
                                        "true",
                                        """
                                        The tree contains only the root node.

                                        Its value is equal to the target.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        5
                                        10 5 15 2 7
                                        2
                                        """,
                                        "true",
                                        """
                                        The target 2 is smaller than 10 and smaller than 5.

                                        Following the left child links reaches node 2.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        5
                                        -5 -10 0 -20 10
                                        8
                                        """,
                                        "false",
                                        """
                                        The value 8 was never inserted into the tree.

                                        Therefore, the answer is false.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "7\n8 3 10 1 6 14 4\n6",
                                        "true",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "7\n8 3 10 1 6 14 4\n7",
                                        "false",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "1\n5\n5",
                                        "true",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "5\n10 5 15 2 7\n2",
                                        "true",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "5\n-5 -10 0 -20 10\n8",
                                        "false",
                                        true
                                )
                        )
                ),

                // ==================================================
                // 2. MINIMUM VALUE IN A BINARY SEARCH TREE
                // ==================================================

                new ProblemSeedData(

                        "Minimum Value in a Binary Search Tree",

                        """
                        Given a Binary Search Tree containing n distinct integers, find the minimum value stored in the tree.

                        The tree is created by inserting the given values from left to right into an initially empty Binary Search Tree.

                        In a Binary Search Tree, values smaller than a node are stored in its left subtree.

                        Therefore, the minimum value can be found by repeatedly moving to the left child until no further left child exists.

                        Print the minimum value.
                        """,

                        Difficulty.EASY,

                        "binary-search-tree",

                        """
                        1 <= n <= 100000

                        -1000000000 <= value[i] <= 1000000000

                        All tree values are distinct.
                        """,

                        """
                        The first line contains an integer n.

                        The second line contains n space-separated distinct integers in insertion order.
                        """,

                        """
                        Print the minimum value stored in the Binary Search Tree.
                        """,

                        """
                        7
                        8 3 10 1 6 14 4
                        """,

                        "1",

                        List.of(
                                "Binary Search Tree",
                                "Tree"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        7
                                        8 3 10 1 6 14 4
                                        """,
                                        "1",
                                        """
                                        Starting from the root 8, moving left reaches 3 and then 1.

                                        Node 1 has no left child.

                                        Therefore, 1 is the minimum value.
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
                                        The tree contains only one node.

                                        Therefore, 10 is the minimum value.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        5
                                        5 4 3 2 1
                                        """,
                                        "1",
                                        """
                                        Every inserted value is smaller than the previous value.

                                        The leftmost node contains 1.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        5
                                        -10 -20 0 -30 5
                                        """,
                                        "-30",
                                        """
                                        The smallest inserted value is -30.

                                        It becomes the leftmost node of the tree.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        6
                                        20 10 30 5 15 25
                                        """,
                                        "5",
                                        """
                                        Moving left from 20 reaches 10 and then 5.

                                        Therefore, the minimum value is 5.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "7\n8 3 10 1 6 14 4",
                                        "1",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "1\n10",
                                        "10",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "5\n5 4 3 2 1",
                                        "1",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "5\n-10 -20 0 -30 5",
                                        "-30",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "6\n20 10 30 5 15 25",
                                        "5",
                                        true
                                )
                        )
                ),

                // ==================================================
                // 3. VALIDATE BINARY SEARCH TREE
                // ==================================================

                new ProblemSeedData(

                        "Validate Binary Search Tree",

                        """
                        Given the preorder traversal of a binary tree where null children are represented by the word null, determine whether the tree is a valid Binary Search Tree.

                        A valid Binary Search Tree must satisfy the following conditions:

                        Every value in the left subtree of a node must be strictly smaller than the node's value.

                        Every value in the right subtree of a node must be strictly greater than the node's value.

                        Both the left and right subtrees must also satisfy the Binary Search Tree property.

                        Duplicate values are not allowed in a valid Binary Search Tree.

                        Print true if the tree is valid.

                        Otherwise, print false.
                        """,

                        Difficulty.MEDIUM,

                        "binary-search-tree",

                        """
                        1 <= number of tokens <= 200001

                        -1000000000 <= node value <= 1000000000

                        Each token is either an integer or the word null.

                        The input represents a valid preorder serialization of a binary tree.
                        """,

                        """
                        A single line contains the preorder serialization of the binary tree.

                        Each node value or null marker is separated by one space.
                        """,

                        """
                        Print true if the binary tree is a valid Binary Search Tree.

                        Otherwise, print false.
                        """,

                        "2 1 null null 3 null null",

                        "true",

                        List.of(
                                "Binary Search Tree",
                                "Tree",
                                "Depth-First Search",
                                "Recursion"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        "2 1 null null 3 null null",
                                        "true",
                                        """
                                        Node 1 is smaller than root 2 and node 3 is greater than root 2.

                                        Both subtrees also satisfy the required ordering.

                                        Therefore, the tree is a valid Binary Search Tree.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        "5 1 null null 4 3 null null 6 null null",
                                        "false",
                                        """
                                        Node 4 is in the right subtree of root 5 but is smaller than 5.

                                        Therefore, the Binary Search Tree property is violated.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        "10 null null",
                                        "true",
                                        """
                                        A tree containing one node always satisfies the Binary Search Tree property.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        "2 2 null null 3 null null",
                                        "false",
                                        """
                                        The left child has the same value as the root.

                                        Duplicate values are not allowed.

                                        Therefore, the tree is invalid.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        "10 5 2 null null 7 null null 15 12 null null 20 null null",
                                        "true",
                                        """
                                        Every value in the left subtree of 10 is smaller than 10.

                                        Every value in the right subtree is greater than 10.

                                        All internal subtrees also satisfy the ordering rules.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "2 1 null null 3 null null",
                                        "true",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "5 1 null null 4 3 null null 6 null null",
                                        "false",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "10 null null",
                                        "true",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "2 2 null null 3 null null",
                                        "false",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "10 5 2 null null 7 null null 15 12 null null 20 null null",
                                        "true",
                                        true
                                )
                        )
                ),

                // ==================================================
                // 4. KTH SMALLEST ELEMENT IN A BST
                // ==================================================

                new ProblemSeedData(

                        "Kth Smallest Element in a BST",

                        """
                        Given a Binary Search Tree containing n distinct integers and a positive integer k, find the kth smallest value stored in the tree.

                        The tree is created by inserting the given values from left to right into an initially empty Binary Search Tree.

                        The smallest value is considered the first smallest element, the next larger value is the second smallest element, and so on.

                        An inorder traversal of a Binary Search Tree visits values in ascending order.

                        Print the kth smallest value.
                        """,

                        Difficulty.MEDIUM,

                        "binary-search-tree",

                        """
                        1 <= n <= 100000

                        1 <= k <= n

                        -1000000000 <= value[i] <= 1000000000

                        All tree values are distinct.
                        """,

                        """
                        The first line contains two space-separated integers n and k.

                        The second line contains n space-separated distinct integers in insertion order.
                        """,

                        """
                        Print the kth smallest value stored in the Binary Search Tree.
                        """,

                        """
                        6 3
                        5 3 6 2 4 1
                        """,

                        "3",

                        List.of(
                                "Binary Search Tree",
                                "Tree",
                                "Inorder Traversal"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        6 3
                                        5 3 6 2 4 1
                                        """,
                                        "3",
                                        """
                                        The values in ascending order are 1, 2, 3, 4, 5, and 6.

                                        The third smallest value is 3.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        4 1
                                        3 1 4 2
                                        """,
                                        "1",
                                        """
                                        The smallest value in the tree is 1.

                                        Therefore, the first smallest value is 1.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        5 5
                                        10 5 15 2 20
                                        """,
                                        "20",
                                        """
                                        The sorted values are 2, 5, 10, 15, and 20.

                                        Therefore, the fifth smallest value is 20.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        7 4
                                        8 3 10 1 6 14 4
                                        """,
                                        "6",
                                        """
                                        The sorted values are 1, 3, 4, 6, 8, 10, and 14.

                                        The fourth smallest value is 6.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        5 2
                                        -5 -10 0 -20 10
                                        """,
                                        "-10",
                                        """
                                        The sorted values are -20, -10, -5, 0, and 10.

                                        The second smallest value is -10.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "6 3\n5 3 6 2 4 1",
                                        "3",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "4 1\n3 1 4 2",
                                        "1",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "5 5\n10 5 15 2 20",
                                        "20",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "7 4\n8 3 10 1 6 14 4",
                                        "6",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "5 2\n-5 -10 0 -20 10",
                                        "-10",
                                        true
                                )
                        )
                ),

                // ==================================================
                // 5. RECOVER BINARY SEARCH TREE
                // ==================================================

                new ProblemSeedData(

                        "Recover Binary Search Tree",

                        """
                        A Binary Search Tree originally contained n distinct integers, but the values of exactly two nodes were accidentally swapped.

                        You are given the inorder traversal of the corrupted tree.

                        Recover the Binary Search Tree by identifying the two swapped values and restoring their correct positions.

                        Print the corrected inorder traversal.

                        A valid Binary Search Tree has an inorder traversal whose values appear in strictly increasing order.

                        You should recover the ordering without changing the number of elements.

                        For example, the corrupted inorder traversal [1, 3, 2, 4] was created by swapping the values 2 and 3.

                        Restoring them produces [1, 2, 3, 4].
                        """,

                        Difficulty.HARD,

                        "binary-search-tree",

                        """
                        2 <= n <= 100000

                        -1000000000 <= value[i] <= 1000000000

                        All values are distinct.

                        The given sequence was created from a strictly increasing sequence by swapping exactly two values.
                        """,

                        """
                        The first line contains an integer n.

                        The second line contains n space-separated integers representing the corrupted inorder traversal.
                        """,

                        """
                        Print n space-separated integers representing the corrected inorder traversal.
                        """,

                        """
                        4
                        1 3 2 4
                        """,

                        "1 2 3 4",

                        List.of(
                                "Binary Search Tree",
                                "Tree",
                                "Inorder Traversal"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        4
                                        1 3 2 4
                                        """,
                                        "1 2 3 4",
                                        """
                                        The values 3 and 2 were swapped.

                                        Exchanging them restores the strictly increasing inorder traversal.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        3
                                        3 2 1
                                        """,
                                        "1 2 3",
                                        """
                                        The first and final values were swapped.

                                        Restoring them produces the valid inorder traversal 1 2 3.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        5
                                        1 5 3 4 2
                                        """,
                                        "1 2 3 4 5",
                                        """
                                        The values 5 and 2 were swapped.

                                        Restoring their positions produces increasing order.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        6
                                        -10 -5 5 0 10 20
                                        """,
                                        "-10 -5 0 5 10 20",
                                        """
                                        The values 5 and 0 were swapped.

                                        Exchanging them restores the valid inorder traversal.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        7
                                        1 6 3 4 5 2 7
                                        """,
                                        "1 2 3 4 5 6 7",
                                        """
                                        The values 6 and 2 were swapped.

                                        Restoring them produces a strictly increasing sequence.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "4\n1 3 2 4",
                                        "1 2 3 4",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "3\n3 2 1",
                                        "1 2 3",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "5\n1 5 3 4 2",
                                        "1 2 3 4 5",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "6\n-10 -5 5 0 10 20",
                                        "-10 -5 0 5 10 20",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "7\n1 6 3 4 5 2 7",
                                        "1 2 3 4 5 6 7",
                                        true
                                )
                        )
                )
        );
    }
}