package com.codementor.backend.seed.problems;

import com.codementor.backend.entity.Difficulty;
import com.codementor.backend.seed.ProblemExampleSeedData;
import com.codementor.backend.seed.ProblemSeedData;
import com.codementor.backend.seed.TestCaseSeedData;

import java.util.List;

public final class LinkedListProblemSeeds {

    private LinkedListProblemSeeds() {
    }

    public static List<ProblemSeedData> getProblems() {

        return List.of(

                // ==================================================
                // REVERSE LINKED LIST
                // ==================================================

                new ProblemSeedData(

                        "Reverse Linked List",

                        """
                        Given a singly linked list containing n nodes, reverse the order of all nodes and print the resulting list.

                        In a singly linked list, every node stores a value and a reference to the next node.

                        Reversing the list means that the first node becomes the last node, the second node becomes the second-last node, and so on.

                        For example, the linked list 1 -> 2 -> 3 -> 4 -> 5 becomes 5 -> 4 -> 3 -> 2 -> 1 after reversal.
                        """,

                        Difficulty.EASY,

                        "linked-list",

                        """
                        1 <= n <= 100000

                        -1000000000 <= value[i] <= 1000000000
                        """,

                        """
                        The first line contains an integer n representing the number of nodes.

                        The second line contains n space-separated integers representing the linked list from head to tail.
                        """,

                        """
                        Print n space-separated integers representing the reversed linked list from head to tail.
                        """,

                        """
                        5
                        1 2 3 4 5
                        """,

                        "5 4 3 2 1",

                        List.of(
                                "Linked List",
                                "Pointers"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        5
                                        1 2 3 4 5
                                        """,
                                        "5 4 3 2 1",
                                        """
                                        The original list is 1 -> 2 -> 3 -> 4 -> 5.

                                        After reversing every link, node 5 becomes the new head and node 1 becomes the final node.

                                        Therefore, the result is 5 4 3 2 1.
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
                                        The linked list contains only one node.

                                        Reversing a single-node list does not change it.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        4
                                        -1 -2 -3 -4
                                        """,
                                        "-4 -3 -2 -1",
                                        """
                                        Reversing the node order places -4 first, followed by -3, -2, and -1.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        5
                                        7 7 7 7 7
                                        """,
                                        "7 7 7 7 7",
                                        """
                                        The positions of the nodes are reversed.

                                        Since every node stores the same value, the printed result appears unchanged.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        6
                                        10 20 30 40 50 60
                                        """,
                                        "60 50 40 30 20 10",
                                        """
                                        The last node becomes the new head, and every remaining node follows in reverse order.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "5\n1 2 3 4 5",
                                        "5 4 3 2 1",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "1\n10",
                                        "10",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "4\n-1 -2 -3 -4",
                                        "-4 -3 -2 -1",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "5\n7 7 7 7 7",
                                        "7 7 7 7 7",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "6\n10 20 30 40 50 60",
                                        "60 50 40 30 20 10",
                                        true
                                )
                        )
                ),

                // ==================================================
                // MIDDLE OF THE LINKED LIST
                // ==================================================

                new ProblemSeedData(

                        "Middle of the Linked List",

                        """
                        Given a singly linked list containing n nodes, find and print the value stored in the middle node.

                        If the linked list contains an odd number of nodes, exactly one middle node exists.

                        If the linked list contains an even number of nodes, two middle nodes exist. In this case, print the value of the second middle node.

                        For example, the list 1 -> 2 -> 3 -> 4 -> 5 has middle node 3.

                        The list 1 -> 2 -> 3 -> 4 -> 5 -> 6 has two middle nodes, 3 and 4, so the required answer is 4.
                        """,

                        Difficulty.EASY,

                        "linked-list",

                        """
                        1 <= n <= 100000

                        -1000000000 <= value[i] <= 1000000000
                        """,

                        """
                        The first line contains an integer n representing the number of nodes.

                        The second line contains n space-separated integers representing the linked list from head to tail.
                        """,

                        """
                        Print the value stored in the middle node.

                        If two middle nodes exist, print the value of the second middle node.
                        """,

                        """
                        5
                        1 2 3 4 5
                        """,

                        "3",

                        List.of(
                                "Linked List",
                                "Two Pointers",
                                "Fast and Slow Pointers"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        5
                                        1 2 3 4 5
                                        """,
                                        "3",
                                        """
                                        The list contains five nodes.

                                        The third node is exactly in the middle and stores the value 3.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        6
                                        1 2 3 4 5 6
                                        """,
                                        "4",
                                        """
                                        The list contains an even number of nodes.

                                        The two middle nodes store 3 and 4.

                                        The second middle value, 4, must be printed.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        1
                                        100
                                        """,
                                        "100",
                                        """
                                        A single-node list has that node as its middle.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        2
                                        -5 10
                                        """,
                                        "10",
                                        """
                                        The list has two middle nodes.

                                        The second middle node stores 10.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        7
                                        10 20 30 40 50 60 70
                                        """,
                                        "40",
                                        """
                                        The fourth node has three nodes before it and three nodes after it.

                                        Therefore, 40 is the middle value.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "5\n1 2 3 4 5",
                                        "3",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "6\n1 2 3 4 5 6",
                                        "4",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "1\n100",
                                        "100",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "2\n-5 10",
                                        "10",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "7\n10 20 30 40 50 60 70",
                                        "40",
                                        true
                                )
                        )
                ),

                // ==================================================
                // REMOVE NTH NODE FROM END
                // ==================================================

                new ProblemSeedData(

                        "Remove Nth Node From End",

                        """
                        Given a singly linked list containing size nodes and an integer n, remove the nth node counted from the end of the list.

                        The final node is considered the first node from the end.

                        The node before the final node is considered the second node from the end, and so on.

                        After removing the required node, print the remaining linked list.

                        For example, in the list 1 -> 2 -> 3 -> 4 -> 5 with n = 2, the second node from the end is 4.

                        Removing it produces 1 -> 2 -> 3 -> 5.
                        """,

                        Difficulty.MEDIUM,

                        "linked-list",

                        """
                        1 <= size <= 100000

                        1 <= n <= size

                        -1000000000 <= value[i] <= 1000000000
                        """,

                        """
                        The first line contains two space-separated integers size and n.

                        The second line contains size space-separated integers representing the linked list.
                        """,

                        """
                        Print the remaining node values after removing the nth node from the end.

                        If removing the node makes the list empty, print EMPTY.
                        """,

                        """
                        5 2
                        1 2 3 4 5
                        """,

                        "1 2 3 5",

                        List.of(
                                "Linked List",
                                "Two Pointers"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        5 2
                                        1 2 3 4 5
                                        """,
                                        "1 2 3 5",
                                        """
                                        Counting from the end gives 5 as the first node and 4 as the second node.

                                        Therefore, node 4 is removed.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        1 1
                                        10
                                        """,
                                        "EMPTY",
                                        """
                                        The only node is also the first node from the end.

                                        Removing it makes the linked list empty.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        2 1
                                        10 20
                                        """,
                                        "10",
                                        """
                                        The first node from the end is the final node containing 20.

                                        Removing it leaves only node 10.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        4 4
                                        5 10 15 20
                                        """,
                                        "10 15 20",
                                        """
                                        The fourth node from the end is the head node containing 5.

                                        Removing the head leaves 10 15 20.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        6 3
                                        1 2 3 4 5 6
                                        """,
                                        "1 2 3 5 6",
                                        """
                                        Counting from the end gives 6, 5, and then 4.

                                        Therefore, the node containing 4 is removed.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "5 2\n1 2 3 4 5",
                                        "1 2 3 5",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "1 1\n10",
                                        "EMPTY",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "2 1\n10 20",
                                        "10",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "4 4\n5 10 15 20",
                                        "10 15 20",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "6 3\n1 2 3 4 5 6",
                                        "1 2 3 5 6",
                                        true
                                )
                        )
                ),

                // ==================================================
                // LINKED LIST CYCLE ENTRY
                // ==================================================

                new ProblemSeedData(

                        "Linked List Cycle Entry",

                        """
                        Given a singly linked list, determine whether the list contains a cycle.

                        The nodes are provided in their original order together with an integer position.

                        If position is -1, the final node points to null and the linked list contains no cycle.

                        Otherwise, the final node points back to the node at the given zero-based position, creating a cycle.

                        Print the value stored in the node where the cycle begins.

                        If the linked list does not contain a cycle, print -1.

                        For example, for values [3, 2, 0, -4] and position 1, the final node points back to the node containing 2.

                        Therefore, the cycle begins at the node containing 2.
                        """,

                        Difficulty.MEDIUM,

                        "linked-list",

                        """
                        1 <= n <= 100000

                        -1000000000 <= value[i] <= 1000000000

                        -1 <= position < n
                        """,

                        """
                        The first line contains two space-separated integers n and position.

                        The second line contains n space-separated integers representing the node values.

                        position is zero-based.

                        A value of -1 means the final node points to null.
                        """,

                        """
                        Print the value stored in the node where the cycle begins.

                        If no cycle exists, print -1.
                        """,

                        """
                        4 1
                        3 2 0 -4
                        """,

                        "2",

                        List.of(
                                "Linked List",
                                "Two Pointers",
                                "Fast and Slow Pointers",
                                "Cycle Detection"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        4 1
                                        3 2 0 -4
                                        """,
                                        "2",
                                        """
                                        The final node points back to the node at index 1.

                                        That node stores the value 2.

                                        Therefore, the cycle begins at value 2.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        2 0
                                        1 2
                                        """,
                                        "1",
                                        """
                                        The final node points back to the head at index 0.

                                        Therefore, the cycle begins at value 1.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        3 -1
                                        1 2 3
                                        """,
                                        "-1",
                                        """
                                        The position is -1, so the final node points to null.

                                        Therefore, no cycle exists.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        1 0
                                        7
                                        """,
                                        "7",
                                        """
                                        The only node points back to itself.

                                        Therefore, the cycle begins at value 7.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        5 3
                                        10 20 30 40 50
                                        """,
                                        "40",
                                        """
                                        The final node points back to the node at index 3.

                                        That node stores 40, so the cycle begins there.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "4 1\n3 2 0 -4",
                                        "2",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "2 0\n1 2",
                                        "1",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "3 -1\n1 2 3",
                                        "-1",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "1 0\n7",
                                        "7",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "5 3\n10 20 30 40 50",
                                        "40",
                                        true
                                )
                        )
                ),

                // ==================================================
                // MERGE K SORTED LINKED LISTS
                // ==================================================

                new ProblemSeedData(

                        "Merge K Sorted Linked Lists",

                        """
                        Given k singly linked lists, where every list is sorted in non-decreasing order, merge all nodes into one sorted linked list.

                        The resulting list must contain every value from every input list, including duplicate values.

                        Print the values of the merged linked list in non-decreasing order.

                        For example, merging the lists [1, 4, 5], [1, 3, 4], and [2, 6] produces [1, 1, 2, 3, 4, 4, 5, 6].
                        """,

                        Difficulty.HARD,

                        "linked-list",

                        """
                        1 <= k <= 10000

                        1 <= size of each list

                        The total number of nodes across all lists does not exceed 200000.

                        -1000000000 <= value <= 1000000000

                        Every input linked list is sorted in non-decreasing order.
                        """,

                        """
                        The first line contains an integer k representing the number of linked lists.

                        For each linked list:

                        The first line contains an integer size representing its number of nodes.

                        The next line contains size space-separated integers representing the sorted linked list.
                        """,

                        """
                        Print all node values from the merged linked list in non-decreasing order.
                        """,

                        """
                        3
                        3
                        1 4 5
                        3
                        1 3 4
                        2
                        2 6
                        """,

                        "1 1 2 3 4 4 5 6",

                        List.of(
                                "Linked List",
                                "Heap",
                                "Priority Queue",
                                "Divide and Conquer"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        3
                                        3
                                        1 4 5
                                        3
                                        1 3 4
                                        2
                                        2 6
                                        """,
                                        "1 1 2 3 4 4 5 6",
                                        """
                                        All nodes from the three lists are merged.

                                        Arranging them in non-decreasing order produces 1 1 2 3 4 4 5 6.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        1
                                        4
                                        1 2 3 4
                                        """,
                                        "1 2 3 4",
                                        """
                                        Only one linked list is provided.

                                        Therefore, the merged result is identical to the input list.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        2
                                        3
                                        -5 0 10
                                        3
                                        -4 2 8
                                        """,
                                        "-5 -4 0 2 8 10",
                                        """
                                        Values from both sorted lists are combined while maintaining non-decreasing order.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        3
                                        2
                                        1 1
                                        2
                                        1 1
                                        2
                                        1 1
                                        """,
                                        "1 1 1 1 1 1",
                                        """
                                        Duplicate values from every linked list are preserved.

                                        Therefore, all six values appear in the result.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        4
                                        1
                                        10
                                        1
                                        5
                                        1
                                        20
                                        1
                                        -5
                                        """,
                                        "-5 5 10 20",
                                        """
                                        Each linked list contains one node.

                                        Merging and ordering the four values produces -5 5 10 20.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "3\n3\n1 4 5\n3\n1 3 4\n2\n2 6",
                                        "1 1 2 3 4 4 5 6",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "1\n4\n1 2 3 4",
                                        "1 2 3 4",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "2\n3\n-5 0 10\n3\n-4 2 8",
                                        "-5 -4 0 2 8 10",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "3\n2\n1 1\n2\n1 1\n2\n1 1",
                                        "1 1 1 1 1 1",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "4\n1\n10\n1\n5\n1\n20\n1\n-5",
                                        "-5 5 10 20",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "3\n4\n-10 -3 5 20\n3\n-7 0 15\n5\n-5 1 2 8 30",
                                        "-10 -7 -5 -3 0 1 2 5 8 15 20 30",
                                        true
                                )
                        )
                )
        );
    }
}