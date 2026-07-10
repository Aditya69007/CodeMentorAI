package com.codementor.backend.seed.problems;

import com.codementor.backend.entity.Difficulty;
import com.codementor.backend.seed.ProblemExampleSeedData;
import com.codementor.backend.seed.ProblemSeedData;
import com.codementor.backend.seed.TestCaseSeedData;

import java.util.List;

public final class QueueProblemSeeds {

    private QueueProblemSeeds() {
    }

    public static List<ProblemSeedData> getProblems() {

        return List.of(

                // ==================================================
                // IMPLEMENT QUEUE USING ARRAY
                // ==================================================

                new ProblemSeedData(

                        "Implement Queue Using Array",

                        """
                        Implement a queue data structure using an array and process a sequence of operations.

                        A queue follows the First In, First Out principle. This means the first element inserted into the queue is also the first element removed.

                        You must support the following operations:

                        ENQUEUE x — Insert the integer x at the rear of the queue.

                        DEQUEUE — Remove the element at the front of the queue and print it. If the queue is empty, print EMPTY.

                        FRONT — Print the element currently at the front of the queue without removing it. If the queue is empty, print EMPTY.

                        SIZE — Print the current number of elements in the queue.

                        For every DEQUEUE, FRONT, and SIZE operation, print the required result on a separate line.
                        """,

                        Difficulty.EASY,

                        "queue",

                        """
                        1 <= q <= 100000

                        -1000000000 <= x <= 1000000000

                        q represents the number of operations.

                        Operation names are written using uppercase English letters.
                        """,

                        """
                        The first line contains an integer q representing the number of operations.

                        Each of the next q lines contains one operation.

                        An ENQUEUE operation is followed by one integer x.
                        """,

                        """
                        For every DEQUEUE operation, print the removed value or EMPTY if the queue is empty.

                        For every FRONT operation, print the front value or EMPTY if the queue is empty.

                        For every SIZE operation, print the current number of elements.
                        """,

                        """
                        8
                        ENQUEUE 10
                        ENQUEUE 20
                        FRONT
                        SIZE
                        DEQUEUE
                        FRONT
                        DEQUEUE
                        DEQUEUE
                        """,

                        """
                        10
                        2
                        10
                        20
                        20
                        EMPTY
                        """,

                        List.of(
                                "Queue",
                                "Array",
                                "Data Structure Design"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        8
                                        ENQUEUE 10
                                        ENQUEUE 20
                                        FRONT
                                        SIZE
                                        DEQUEUE
                                        FRONT
                                        DEQUEUE
                                        DEQUEUE
                                        """,
                                        """
                                        10
                                        2
                                        10
                                        20
                                        20
                                        EMPTY
                                        """,
                                        """
                                        After inserting 10 and 20, the front value is 10 and the queue size is 2.

                                        The first DEQUEUE removes 10.

                                        The new front value is 20, and the next DEQUEUE removes 20.

                                        The final DEQUEUE is performed on an empty queue, so EMPTY is printed.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        4
                                        DEQUEUE
                                        FRONT
                                        SIZE
                                        ENQUEUE 5
                                        """,
                                        """
                                        EMPTY
                                        EMPTY
                                        0
                                        """,
                                        """
                                        The queue is initially empty.

                                        Therefore, DEQUEUE and FRONT both print EMPTY.

                                        The SIZE operation prints 0.

                                        ENQUEUE does not produce output.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        5
                                        ENQUEUE -3
                                        FRONT
                                        SIZE
                                        DEQUEUE
                                        SIZE
                                        """,
                                        """
                                        -3
                                        1
                                        -3
                                        0
                                        """,
                                        """
                                        The value -3 is inserted into the queue.

                                        It becomes the front element and the queue size becomes 1.

                                        Removing it makes the queue empty again.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        7
                                        ENQUEUE 1
                                        ENQUEUE 2
                                        ENQUEUE 3
                                        DEQUEUE
                                        DEQUEUE
                                        FRONT
                                        SIZE
                                        """,
                                        """
                                        1
                                        2
                                        3
                                        1
                                        """,
                                        """
                                        The values are removed in the same order in which they were inserted.

                                        After removing 1 and 2, only 3 remains.

                                        Therefore, FRONT prints 3 and SIZE prints 1.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        6
                                        ENQUEUE 100
                                        DEQUEUE
                                        ENQUEUE 200
                                        FRONT
                                        SIZE
                                        DEQUEUE
                                        """,
                                        """
                                        100
                                        200
                                        1
                                        200
                                        """,
                                        """
                                        The value 100 is inserted and removed.

                                        The value 200 is then inserted into the empty queue.

                                        It becomes the front element and the only element in the queue.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "8\nENQUEUE 10\nENQUEUE 20\nFRONT\nSIZE\nDEQUEUE\nFRONT\nDEQUEUE\nDEQUEUE",
                                        "10\n2\n10\n20\n20\nEMPTY",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "4\nDEQUEUE\nFRONT\nSIZE\nENQUEUE 5",
                                        "EMPTY\nEMPTY\n0",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "5\nENQUEUE -3\nFRONT\nSIZE\nDEQUEUE\nSIZE",
                                        "-3\n1\n-3\n0",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "7\nENQUEUE 1\nENQUEUE 2\nENQUEUE 3\nDEQUEUE\nDEQUEUE\nFRONT\nSIZE",
                                        "1\n2\n3\n1",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "6\nENQUEUE 100\nDEQUEUE\nENQUEUE 200\nFRONT\nSIZE\nDEQUEUE",
                                        "100\n200\n1\n200",
                                        true
                                )
                        )
                ),

                // ==================================================
                // FIRST NON-REPEATING CHARACTER IN A STREAM
                // ==================================================

                new ProblemSeedData(

                        "First Non-Repeating Character in a Stream",

                        """
                        Given a stream of lowercase English characters, process the characters from left to right.

                        After receiving each character, determine the first character among all characters seen so far that has appeared exactly once.

                        If no non-repeating character exists at the current position, use the character #.

                        Print the result produced after processing every character.

                        For example, consider the stream "aabc".

                        After reading the first 'a', the first non-repeating character is 'a'.

                        After reading the second 'a', no non-repeating character exists, so the result is '#'.

                        After reading 'b', the first non-repeating character becomes 'b'.

                        After reading 'c', 'b' remains the first non-repeating character.

                        Therefore, the final result is "a#bb".
                        """,

                        Difficulty.EASY,

                        "queue",

                        """
                        1 <= length of stream <= 100000

                        The stream contains only lowercase English letters.
                        """,

                        """
                        A single string representing the stream of lowercase English characters.
                        """,

                        """
                        Print a string where the character at each position represents the first non-repeating character after processing that position.

                        Print # when no non-repeating character exists.
                        """,

                        "aabc",

                        "a#bb",

                        List.of(
                                "Queue",
                                "String",
                                "Hashing",
                                "Frequency Counting"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        "aabc",
                                        "a#bb",
                                        """
                                        After the first 'a', the answer is 'a'.

                                        After the second 'a', no unique character remains, so the answer is '#'.

                                        After 'b' arrives, 'b' becomes the first unique character.

                                        After 'c' arrives, 'b' is still the earliest unique character.

                                        Therefore, the result is "a#bb".
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        "abc",
                                        "aaa",
                                        """
                                        After reading 'a', the first unique character is 'a'.

                                        When 'b' and 'c' arrive, 'a' still appears exactly once and remains before them.

                                        Therefore, the result is "aaa".
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        "aabb",
                                        "a#b#",
                                        """
                                        The first 'a' produces 'a'.

                                        The second 'a' removes it from consideration, producing '#'.

                                        The first 'b' then becomes unique.

                                        After the second 'b', no unique character remains.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        "z",
                                        "z",
                                        """
                                        The only character has appeared exactly once.

                                        Therefore, it is the first non-repeating character.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        "abadbc",
                                        "aabbdd",
                                        """
                                        The earliest character with frequency one is tracked after every new character.

                                        The result changes as previously unique characters become repeated.

                                        Processing the complete stream produces "aabbdd".
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "aabc",
                                        "a#bb",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "abc",
                                        "aaa",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "aabb",
                                        "a#b#",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "z",
                                        "z",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "abadbc",
                                        "aabbdd",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "zzxyyx",
                                        "z#xxxx",
                                        true
                                )
                        )
                ),

                // ==================================================
                // DESIGN CIRCULAR QUEUE
                // ==================================================

                new ProblemSeedData(

                        "Design Circular Queue",

                        """
                        Implement a circular queue with a fixed capacity and process a sequence of operations.

                        A circular queue reuses positions that become available after elements are removed.

                        You must support the following operations:

                        ENQUEUE x — Insert x at the rear of the queue. Print true if the insertion succeeds or false if the queue is full.

                        DEQUEUE — Remove the front element. Print true if the removal succeeds or false if the queue is empty.

                        FRONT — Print the front element, or -1 if the queue is empty.

                        REAR — Print the rear element, or -1 if the queue is empty.

                        EMPTY — Print true if the queue is empty, otherwise print false.

                        FULL — Print true if the queue is full, otherwise print false.

                        Every operation produces exactly one output line.
                        """,

                        Difficulty.MEDIUM,

                        "queue",

                        """
                        1 <= capacity <= 100000

                        1 <= q <= 100000

                        -1000000000 <= x <= 1000000000
                        """,

                        """
                        The first line contains two space-separated integers capacity and q.

                        Each of the next q lines contains one operation.
                        """,

                        """
                        Print the result of every operation on a separate line according to the operation rules.
                        """,

                        """
                        3 8
                        ENQUEUE 1
                        ENQUEUE 2
                        ENQUEUE 3
                        ENQUEUE 4
                        REAR
                        FULL
                        DEQUEUE
                        ENQUEUE 4
                        """,

                        """
                        true
                        true
                        true
                        false
                        3
                        true
                        true
                        true
                        """,

                        List.of(
                                "Queue",
                                "Circular Queue",
                                "Array",
                                "Data Structure Design"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        3 8
                                        ENQUEUE 1
                                        ENQUEUE 2
                                        ENQUEUE 3
                                        ENQUEUE 4
                                        REAR
                                        FULL
                                        DEQUEUE
                                        ENQUEUE 4
                                        """,
                                        """
                                        true
                                        true
                                        true
                                        false
                                        3
                                        true
                                        true
                                        true
                                        """,
                                        """
                                        The first three insertions fill the queue.

                                        Inserting 4 fails because the queue is full.

                                        The rear value is 3, and FULL returns true.

                                        After removing the front element, one position becomes available and 4 can be inserted successfully.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        2 5
                                        EMPTY
                                        FRONT
                                        REAR
                                        DEQUEUE
                                        FULL
                                        """,
                                        """
                                        true
                                        -1
                                        -1
                                        false
                                        false
                                        """,
                                        """
                                        The queue starts empty.

                                        Therefore, EMPTY returns true, FRONT and REAR return -1, and DEQUEUE fails.

                                        The queue is not full.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        1 6
                                        ENQUEUE 10
                                        FULL
                                        FRONT
                                        REAR
                                        DEQUEUE
                                        EMPTY
                                        """,
                                        """
                                        true
                                        true
                                        10
                                        10
                                        true
                                        true
                                        """,
                                        """
                                        A queue with capacity 1 becomes full after one insertion.

                                        The same value is both the front and rear element.

                                        Removing it makes the queue empty.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        3 7
                                        ENQUEUE 5
                                        ENQUEUE 10
                                        DEQUEUE
                                        ENQUEUE 15
                                        FRONT
                                        REAR
                                        FULL
                                        """,
                                        """
                                        true
                                        true
                                        true
                                        true
                                        10
                                        15
                                        false
                                        """,
                                        """
                                        After removing 5, the front becomes 10.

                                        Inserting 15 succeeds.

                                        The queue contains 10 and 15, so it is not full.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        2 7
                                        ENQUEUE -5
                                        ENQUEUE -10
                                        FULL
                                        DEQUEUE
                                        ENQUEUE 20
                                        FRONT
                                        REAR
                                        """,
                                        """
                                        true
                                        true
                                        true
                                        true
                                        true
                                        -10
                                        20
                                        """,
                                        """
                                        The first two insertions fill the queue.

                                        After removing -5, the freed circular position can be reused to insert 20.

                                        The front is then -10 and the rear is 20.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "3 8\nENQUEUE 1\nENQUEUE 2\nENQUEUE 3\nENQUEUE 4\nREAR\nFULL\nDEQUEUE\nENQUEUE 4",
                                        "true\ntrue\ntrue\nfalse\n3\ntrue\ntrue\ntrue",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "2 5\nEMPTY\nFRONT\nREAR\nDEQUEUE\nFULL",
                                        "true\n-1\n-1\nfalse\nfalse",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "1 6\nENQUEUE 10\nFULL\nFRONT\nREAR\nDEQUEUE\nEMPTY",
                                        "true\ntrue\n10\n10\ntrue\ntrue",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "3 7\nENQUEUE 5\nENQUEUE 10\nDEQUEUE\nENQUEUE 15\nFRONT\nREAR\nFULL",
                                        "true\ntrue\ntrue\ntrue\n10\n15\nfalse",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "2 7\nENQUEUE -5\nENQUEUE -10\nFULL\nDEQUEUE\nENQUEUE 20\nFRONT\nREAR",
                                        "true\ntrue\ntrue\ntrue\ntrue\n-10\n20",
                                        true
                                )
                        )
                ),

                // ==================================================
                // REVEAL CARDS IN INCREASING ORDER
                // ==================================================

                new ProblemSeedData(

                        "Reveal Cards in Increasing Order",

                        """
                        Given n distinct integers representing cards, arrange the cards in a deck so that performing a specific reveal process produces the values in increasing order.

                        The reveal process works as follows:

                        Reveal and remove the card at the top of the deck.

                        If cards remain, move the next card from the top of the deck to the bottom.

                        Repeat these steps until every card has been revealed.

                        Find the initial deck arrangement that causes the cards to be revealed in strictly increasing order.

                        Print the deck from top to bottom.

                        For example, for cards [17, 13, 11, 2, 3, 5, 7], one valid initial arrangement is [2, 13, 3, 11, 5, 17, 7].
                        """,

                        Difficulty.MEDIUM,

                        "queue",

                        """
                        1 <= n <= 100000

                        -1000000000 <= card[i] <= 1000000000

                        All card values are distinct.
                        """,

                        """
                        The first line contains an integer n representing the number of cards.

                        The second line contains n space-separated distinct integers.
                        """,

                        """
                        Print n space-separated integers representing the required initial deck arrangement from top to bottom.
                        """,

                        """
                        7
                        17 13 11 2 3 5 7
                        """,

                        "2 13 3 11 5 17 7",

                        List.of(
                                "Queue",
                                "Sorting",
                                "Simulation"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        7
                                        17 13 11 2 3 5 7
                                        """,
                                        "2 13 3 11 5 17 7",
                                        """
                                        Revealing the top card and repeatedly moving the next card to the bottom produces the order 2, 3, 5, 7, 11, 13, 17.

                                        This is the increasing order of all input cards.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        2
                                        1 1000
                                        """,
                                        "1 1000",
                                        """
                                        The card 1 is revealed first.

                                        The remaining card 1000 is then revealed.

                                        Therefore, the original sorted arrangement already works.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        1
                                        42
                                        """,
                                        "42",
                                        """
                                        A deck containing one card reveals that card immediately.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        4
                                        4 1 3 2
                                        """,
                                        "1 3 2 4",
                                        """
                                        Starting with 1 3 2 4 causes the reveal process to produce 1, 2, 3, and 4.

                                        Therefore, the required arrangement is 1 3 2 4.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        5
                                        10 50 30 20 40
                                        """,
                                        "10 50 20 40 30",
                                        """
                                        The reveal process applied to this arrangement produces 10, 20, 30, 40, and 50.

                                        Therefore, all cards are revealed in increasing order.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "7\n17 13 11 2 3 5 7",
                                        "2 13 3 11 5 17 7",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "2\n1 1000",
                                        "1 1000",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "1\n42",
                                        "42",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "4\n4 1 3 2",
                                        "1 3 2 4",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "5\n10 50 30 20 40",
                                        "10 50 20 40 30",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "6\n6 5 4 3 2 1",
                                        "1 4 2 6 3 5",
                                        true
                                )
                        )
                ),

                // ==================================================
                // SLIDING WINDOW MAXIMUM
                // ==================================================

                new ProblemSeedData(

                        "Sliding Window Maximum",

                        """
                        Given an integer array containing n elements and an integer k, consider every contiguous subarray containing exactly k elements.

                        For each window, find its maximum value.

                        Move the window from left to right by one position at a time and print the maximum value of every window.

                        For example, for the array [1, 3, -1, -3, 5, 3, 6, 7] and k = 3, the windows are:

                        [1, 3, -1]
                        [3, -1, -3]
                        [-1, -3, 5]
                        [-3, 5, 3]
                        [5, 3, 6]
                        [3, 6, 7]

                        Their maximum values are 3, 3, 5, 5, 6, and 7.
                        """,

                        Difficulty.HARD,

                        "queue",

                        """
                        1 <= n <= 100000

                        1 <= k <= n

                        -1000000000 <= array[i] <= 1000000000
                        """,

                        """
                        The first line contains two space-separated integers n and k.

                        The second line contains n space-separated integers.
                        """,

                        """
                        Print n - k + 1 space-separated integers representing the maximum value of every contiguous window of size k.
                        """,

                        """
                        8 3
                        1 3 -1 -3 5 3 6 7
                        """,

                        "3 3 5 5 6 7",

                        List.of(
                                "Queue",
                                "Deque",
                                "Sliding Window",
                                "Array",
                                "Monotonic Queue"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        8 3
                                        1 3 -1 -3 5 3 6 7
                                        """,
                                        "3 3 5 5 6 7",
                                        """
                                        There are six windows of size 3.

                                        Their maximum values are 3, 3, 5, 5, 6, and 7.

                                        Therefore, these values are printed in order.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        1 1
                                        1
                                        """,
                                        "1",
                                        """
                                        The only window contains the single value 1.

                                        Therefore, its maximum is 1.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        5 2
                                        1 2 3 4 5
                                        """,
                                        "2 3 4 5",
                                        """
                                        The windows are [1, 2], [2, 3], [3, 4], and [4, 5].

                                        Their maximum values are 2, 3, 4, and 5.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        5 3
                                        5 4 3 2 1
                                        """,
                                        "5 4 3",
                                        """
                                        The windows are [5, 4, 3], [4, 3, 2], and [3, 2, 1].

                                        Their maximum values are 5, 4, and 3.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        6 6
                                        4 2 12 3 8 7
                                        """,
                                        "12",
                                        """
                                        Since k equals n, the complete array forms the only window.

                                        The maximum value in that window is 12.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "8 3\n1 3 -1 -3 5 3 6 7",
                                        "3 3 5 5 6 7",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "1 1\n1",
                                        "1",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "5 2\n1 2 3 4 5",
                                        "2 3 4 5",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "5 3\n5 4 3 2 1",
                                        "5 4 3",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "6 6\n4 2 12 3 8 7",
                                        "12",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "7 3\n9 9 9 1 8 8 10",
                                        "9 9 9 8 10",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "6 2\n-5 -2 -8 -1 -7 -3",
                                        "-2 -2 -1 -1 -3",
                                        true
                                )
                        )
                )
        );
    }
}