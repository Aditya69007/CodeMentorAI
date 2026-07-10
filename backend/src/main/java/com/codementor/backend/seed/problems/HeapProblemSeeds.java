package com.codementor.backend.seed.problems;

import com.codementor.backend.entity.Difficulty;
import com.codementor.backend.seed.ProblemExampleSeedData;
import com.codementor.backend.seed.ProblemSeedData;
import com.codementor.backend.seed.TestCaseSeedData;

import java.util.List;

public final class HeapProblemSeeds {

    private HeapProblemSeeds() {
    }

    public static List<ProblemSeedData> getProblems() {

        return List.of(

                // ==================================================
                // 1. FIND KTH LARGEST ELEMENT
                // ==================================================

                new ProblemSeedData(

                        "Find Kth Largest Element",

                        """
                        Given an array containing n integers and an integer k, find the kth largest element in the array.

                        The kth largest element is determined by the sorted order of the values, not by the number of distinct values.

                        Duplicate values must be considered separately.

                        For example, in the array [3, 2, 3, 1, 2, 4, 5, 5, 6], the fourth largest element is 4.

                        Use an efficient approach such as maintaining a min-heap containing at most k elements.

                        Print the kth largest element.
                        """,

                        Difficulty.EASY,

                        "heap",

                        """
                        1 <= k <= n <= 100000

                        -1000000000 <= array[i] <= 1000000000
                        """,

                        """
                        The first line contains two space-separated integers n and k.

                        The second line contains n space-separated integers representing the array.
                        """,

                        """
                        Print the kth largest element in the array.
                        """,

                        """
                        6 2
                        3 2 1 5 6 4
                        """,

                        "5",

                        List.of(
                                "Heap",
                                "Priority Queue",
                                "Array"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        6 2
                                        3 2 1 5 6 4
                                        """,
                                        "5",
                                        """
                                        Sorting the values in decreasing order gives 6, 5, 4, 3, 2, 1.

                                        The second largest element is 5.

                                        Therefore, the answer is 5.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        9 4
                                        3 2 3 1 2 4 5 5 6
                                        """,
                                        "4",
                                        """
                                        Sorting in decreasing order gives 6, 5, 5, 4, 3, 3, 2, 2, 1.

                                        The fourth largest element is 4.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        1 1
                                        10
                                        """,
                                        "10",
                                        """
                                        The array contains only one element.

                                        Therefore, the first largest element is 10.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        5 5
                                        10 20 30 40 50
                                        """,
                                        "10",
                                        """
                                        The fifth largest element is the smallest value in an array of five elements.

                                        Therefore, the answer is 10.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        5 3
                                        7 7 7 7 7
                                        """,
                                        "7",
                                        """
                                        Every array element has the same value.

                                        Therefore, the third largest element is also 7.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "6 2\n3 2 1 5 6 4",
                                        "5",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "9 4\n3 2 3 1 2 4 5 5 6",
                                        "4",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "1 1\n10",
                                        "10",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "5 5\n10 20 30 40 50",
                                        "10",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "5 3\n7 7 7 7 7",
                                        "7",
                                        true
                                )
                        )
                ),

                // ==================================================
                // 2. LAST STONE WEIGHT
                // ==================================================

                new ProblemSeedData(

                        "Last Stone Weight",

                        """
                        You are given n stones.

                        Each stone has a positive integer weight.

                        Repeatedly choose the two heaviest stones and smash them together.

                        Suppose the two selected stones have weights x and y, where x is less than or equal to y.

                        If x equals y, both stones are destroyed.

                        Otherwise, the stone with weight x is destroyed and the stone with weight y receives the new weight y - x.

                        Continue this process until at most one stone remains.

                        Print the weight of the final remaining stone.

                        If no stone remains, print 0.
                        """,

                        Difficulty.EASY,

                        "heap",

                        """
                        1 <= n <= 100000

                        1 <= weight[i] <= 1000000000
                        """,

                        """
                        The first line contains an integer n.

                        The second line contains n space-separated integers representing the stone weights.
                        """,

                        """
                        Print the weight of the final stone.

                        If no stone remains, print 0.
                        """,

                        """
                        6
                        2 7 4 1 8 1
                        """,

                        "1",

                        List.of(
                                "Heap",
                                "Priority Queue",
                                "Simulation"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        6
                                        2 7 4 1 8 1
                                        """,
                                        "1",
                                        """
                                        Smash stones 8 and 7 to create a stone of weight 1.

                                        Then smash 4 and 2 to create a stone of weight 2.

                                        Continue selecting the two heaviest stones.

                                        Eventually, one stone of weight 1 remains.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        1
                                        5
                                        """,
                                        "5",
                                        """
                                        Only one stone exists.

                                        Therefore, no smashing operation is required and the answer is 5.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        2
                                        10 10
                                        """,
                                        "0",
                                        """
                                        The two stones have equal weights.

                                        Both are destroyed, so no stone remains.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        2
                                        3 8
                                        """,
                                        "5",
                                        """
                                        Smashing stones of weights 8 and 3 creates a new stone of weight 5.

                                        Therefore, the answer is 5.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        4
                                        1 1 1 1
                                        """,
                                        "0",
                                        """
                                        Equal pairs of stones destroy each other.

                                        Eventually, no stone remains.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "6\n2 7 4 1 8 1",
                                        "1",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "1\n5",
                                        "5",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "2\n10 10",
                                        "0",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "2\n3 8",
                                        "5",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "4\n1 1 1 1",
                                        "0",
                                        true
                                )
                        )
                ),

                // ==================================================
                // 3. TOP K FREQUENT ELEMENTS
                // ==================================================

                new ProblemSeedData(

                        "Top K Frequent Elements",

                        """
                        Given an array containing n integers and an integer k, find the k values that appear most frequently.

                        The answer is guaranteed to be unique.

                        Print the selected values in decreasing order of frequency.

                        If two selected values have the same frequency, print the smaller value first.

                        An efficient solution can count the frequency of each value using a hash map and maintain candidates using a heap.

                        Print the k most frequent values.
                        """,

                        Difficulty.MEDIUM,

                        "heap",

                        """
                        1 <= k <= number of distinct values <= n <= 100000

                        -1000000000 <= array[i] <= 1000000000

                        The answer is guaranteed to be unique.
                        """,

                        """
                        The first line contains two space-separated integers n and k.

                        The second line contains n space-separated integers representing the array.
                        """,

                        """
                        Print the k most frequent values as space-separated integers.

                        Values with greater frequency must appear first.

                        If frequencies are equal, print the smaller value first.
                        """,

                        """
                        6 2
                        1 1 1 2 2 3
                        """,

                        "1 2",

                        List.of(
                                "Heap",
                                "Priority Queue",
                                "Hashing",
                                "Frequency Counting"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        6 2
                                        1 1 1 2 2 3
                                        """,
                                        "1 2",
                                        """
                                        Value 1 appears three times.

                                        Value 2 appears twice.

                                        Value 3 appears once.

                                        Therefore, the two most frequent values are 1 and 2.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        1 1
                                        5
                                        """,
                                        "5",
                                        """
                                        The array contains only one distinct value.

                                        Therefore, the answer is 5.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        8 2
                                        4 4 4 6 6 6 6 8
                                        """,
                                        "6 4",
                                        """
                                        Value 6 appears four times and value 4 appears three times.

                                        Therefore, the answer is 6 4.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        6 3
                                        3 3 2 2 1 1
                                        """,
                                        "1 2 3",
                                        """
                                        All three values appear twice.

                                        Since their frequencies are equal, they are printed in increasing numerical order.

                                        Therefore, the answer is 1 2 3.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        10 2
                                        -1 -1 -1 2 2 3 3 3 3 4
                                        """,
                                        "3 -1",
                                        """
                                        Value 3 appears four times.

                                        Value -1 appears three times.

                                        Therefore, the answer is 3 -1.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "6 2\n1 1 1 2 2 3",
                                        "1 2",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "1 1\n5",
                                        "5",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "8 2\n4 4 4 6 6 6 6 8",
                                        "6 4",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "6 3\n3 3 2 2 1 1",
                                        "1 2 3",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "10 2\n-1 -1 -1 2 2 3 3 3 3 4",
                                        "3 -1",
                                        true
                                )
                        )
                ),

                // ==================================================
                // 4. MERGE K SORTED ARRAYS
                // ==================================================

                new ProblemSeedData(

                        "Merge K Sorted Arrays",

                        """
                        You are given k arrays sorted in non-decreasing order.

                        Merge all elements from all arrays into one sorted array.

                        The final array must also be sorted in non-decreasing order.

                        An efficient solution can use a min-heap containing the smallest unprocessed element from each array.

                        Whenever the smallest element is removed from the heap, insert the next element from the same array if one exists.

                        Print the complete merged array.
                        """,

                        Difficulty.MEDIUM,

                        "heap",

                        """
                        1 <= k <= 10000

                        1 <= total number of elements <= 200000

                        -1000000000 <= array[i][j] <= 1000000000

                        Every input array is sorted in non-decreasing order.
                        """,

                        """
                        The first line contains an integer k representing the number of arrays.

                        For each array, one line is provided.

                        Each line begins with an integer size representing the number of elements in that array, followed by size space-separated integers.
                        """,

                        """
                        Print all elements from all arrays in non-decreasing order as space-separated integers.
                        """,

                        """
                        3
                        4 1 4 7 10
                        3 2 5 8
                        3 3 6 9
                        """,

                        "1 2 3 4 5 6 7 8 9 10",

                        List.of(
                                "Heap",
                                "Priority Queue",
                                "Array",
                                "Merge"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        3
                                        4 1 4 7 10
                                        3 2 5 8
                                        3 3 6 9
                                        """,
                                        "1 2 3 4 5 6 7 8 9 10",
                                        """
                                        The smallest available values are repeatedly removed from the heap.

                                        Processing all three arrays produces the sorted sequence from 1 through 10.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        1
                                        5 1 2 3 4 5
                                        """,
                                        "1 2 3 4 5",
                                        """
                                        Only one sorted array exists.

                                        Therefore, the merged result is the original array.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        2
                                        3 1 3 5
                                        3 2 4 6
                                        """,
                                        "1 2 3 4 5 6",
                                        """
                                        Combining both sorted arrays in order produces 1 2 3 4 5 6.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        3
                                        2 -5 -1
                                        3 -4 0 2
                                        2 -3 10
                                        """,
                                        "-5 -4 -3 -1 0 2 10",
                                        """
                                        The heap always selects the smallest remaining element.

                                        Therefore, the final merged order is -5 -4 -3 -1 0 2 10.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        2
                                        3 1 1 1
                                        2 1 1
                                        """,
                                        "1 1 1 1 1",
                                        """
                                        Duplicate values are preserved.

                                        Therefore, all five values appear in the merged result.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "3\n4 1 4 7 10\n3 2 5 8\n3 3 6 9",
                                        "1 2 3 4 5 6 7 8 9 10",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "1\n5 1 2 3 4 5",
                                        "1 2 3 4 5",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "2\n3 1 3 5\n3 2 4 6",
                                        "1 2 3 4 5 6",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "3\n2 -5 -1\n3 -4 0 2\n2 -3 10",
                                        "-5 -4 -3 -1 0 2 10",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "2\n3 1 1 1\n2 1 1",
                                        "1 1 1 1 1",
                                        true
                                )
                        )
                ),

                // ==================================================
                // 5. FIND MEDIAN FROM DATA STREAM
                // ==================================================

                new ProblemSeedData(

                        "Find Median from Data Stream",

                        """
                        You are given a sequence of n integers arriving one at a time.

                        After inserting each integer, determine the median of all values received so far.

                        If the number of values is odd, the median is the middle value after sorting.

                        If the number of values is even, the median is the average of the two middle values.

                        Print every median using exactly one digit after the decimal point.

                        An efficient solution can maintain two heaps.

                        A max-heap stores the smaller half of the values.

                        A min-heap stores the larger half of the values.

                        Keep the heap sizes balanced so that the median can be found efficiently after every insertion.
                        """,

                        Difficulty.HARD,

                        "heap",

                        """
                        1 <= n <= 100000

                        -1000000000 <= value[i] <= 1000000000
                        """,

                        """
                        The first line contains an integer n.

                        The second line contains n space-separated integers representing the values in arrival order.
                        """,

                        """
                        Print n space-separated median values.

                        Print every value using exactly one digit after the decimal point.
                        """,

                        """
                        4
                        5 15 1 3
                        """,

                        "5.0 10.0 5.0 4.0",

                        List.of(
                                "Heap",
                                "Priority Queue",
                                "Data Stream",
                                "Two Heaps"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        4
                                        5 15 1 3
                                        """,
                                        "5.0 10.0 5.0 4.0",
                                        """
                                        After inserting 5, the median is 5.0.

                                        After inserting 15, the median is 10.0.

                                        After inserting 1, the sorted values are 1, 5, 15 and the median is 5.0.

                                        After inserting 3, the sorted values are 1, 3, 5, 15 and the median is 4.0.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        1
                                        10
                                        """,
                                        "10.0",
                                        """
                                        Only one value has arrived.

                                        Therefore, the median is 10.0.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        3
                                        1 2 3
                                        """,
                                        "1.0 1.5 2.0",
                                        """
                                        The medians after each insertion are 1.0, 1.5, and 2.0.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        4
                                        4 3 2 1
                                        """,
                                        "4.0 3.5 3.0 2.5",
                                        """
                                        Recalculating the median after every insertion gives 4.0, 3.5, 3.0, and 2.5.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        5
                                        7 7 7 7 7
                                        """,
                                        "7.0 7.0 7.0 7.0 7.0",
                                        """
                                        Every inserted value is 7.

                                        Therefore, the median remains 7.0 after every insertion.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "4\n5 15 1 3",
                                        "5.0 10.0 5.0 4.0",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "1\n10",
                                        "10.0",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "3\n1 2 3",
                                        "1.0 1.5 2.0",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "4\n4 3 2 1",
                                        "4.0 3.5 3.0 2.5",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "5\n7 7 7 7 7",
                                        "7.0 7.0 7.0 7.0 7.0",
                                        true
                                )
                        )
                )
        );
    }
}