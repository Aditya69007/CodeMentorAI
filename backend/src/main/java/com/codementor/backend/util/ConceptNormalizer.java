package com.codementor.backend.util;

import java.util.Locale;
import java.util.Map;

public final class ConceptNormalizer {

    private static final Map<String, String> CONCEPT_ALIASES =
            Map.ofEntries(

                    // =====================================================
                    // ARRAY
                    // =====================================================
                    Map.entry("array", "Array"),
                    Map.entry("arrays", "Array"),
                    Map.entry("arrays and iteration", "Array"),
                    Map.entry("array traversal", "Array"),
                    Map.entry("array manipulation", "Array"),
                    Map.entry("iteration", "Array"),

                    // =====================================================
                    // STRING
                    // =====================================================
                    Map.entry("string", "String"),
                    Map.entry("strings", "String"),

                    // =====================================================
                    // STACK
                    // =====================================================
                    Map.entry("stack", "Stack"),
                    Map.entry("stacks", "Stack"),
                    Map.entry("stack data structure", "Stack"),
                    Map.entry("parentheses matching", "Stack"),
                    Map.entry("bracket matching", "Stack"),
                    Map.entry("balanced parentheses", "Stack"),

                    // =====================================================
                    // MONOTONIC STACK
                    // =====================================================
                    Map.entry("monotonic stack", "Monotonic Stack"),
                    Map.entry("monotonic stacks", "Monotonic Stack"),
                    Map.entry("next greater element", "Monotonic Stack"),
                    Map.entry("next greater element algorithm", "Monotonic Stack"),

                    // =====================================================
                    // QUEUE
                    // =====================================================
                    Map.entry("queue", "Queue"),
                    Map.entry("queues", "Queue"),
                    Map.entry("queue data structure", "Queue"),

                    // =====================================================
                    // HASHING
                    // =====================================================
                    Map.entry("hashing", "Hashing"),
                    Map.entry("hash map", "Hashing"),
                    Map.entry("hashmap", "Hashing"),
                    Map.entry("hash table", "Hashing"),
                    Map.entry("hash table lookup", "Hashing"),

                    // =====================================================
                    // TWO POINTERS
                    // =====================================================
                    Map.entry("two pointer", "Two Pointers"),
                    Map.entry("two pointers", "Two Pointers"),
                    Map.entry("two pointer technique", "Two Pointers"),

                    // =====================================================
                    // SLIDING WINDOW
                    // =====================================================
                    Map.entry("sliding window", "Sliding Window"),
                    Map.entry("sliding window technique", "Sliding Window"),

                    // =====================================================
                    // GREEDY
                    // =====================================================
                    Map.entry("greedy", "Greedy"),
                    Map.entry("greedy algorithm", "Greedy"),

                    // =====================================================
                    // BINARY SEARCH
                    // =====================================================
                    Map.entry("binary search", "Binary Search"),
                    Map.entry("binary search algorithm", "Binary Search"),

                    // =====================================================
                    // LINKED LIST
                    // =====================================================
                    Map.entry("linked list", "Linked List"),
                    Map.entry("linked lists", "Linked List"),

                    // =====================================================
                    // RECURSION
                    // =====================================================
                    Map.entry("recursion", "Recursion"),
                    Map.entry("recursive algorithm", "Recursion"),

                    // =====================================================
                    // DYNAMIC PROGRAMMING
                    // =====================================================
                    Map.entry("dynamic programming", "Dynamic Programming"),
                    Map.entry("dp", "Dynamic Programming"),
                    Map.entry("kadane", "Dynamic Programming"),
                    Map.entry("kadane's algorithm", "Dynamic Programming"),

                    // =====================================================
                    // GRAPH
                    // =====================================================
                    Map.entry("graph", "Graph"),
                    Map.entry("graphs", "Graph"),
                    Map.entry("graph traversal", "Graph"),
                    Map.entry("bfs", "BFS"),
                    Map.entry("breadth first search", "BFS"),
                    Map.entry("dfs", "DFS"),
                    Map.entry("depth first search", "DFS"),

                    // =====================================================
                    // TREE
                    // =====================================================
                    Map.entry("tree", "Tree"),
                    Map.entry("trees", "Tree"),
                    Map.entry("binary tree", "Binary Tree"),
                    Map.entry("binary search tree", "Binary Search Tree"),

                    // =====================================================
                    // HEAP
                    // =====================================================
                    Map.entry("heap", "Heap"),
                    Map.entry("priority queue", "Heap"),

                    // =====================================================
                    // TRIE
                    // =====================================================
                    Map.entry("trie", "Trie"),
                    Map.entry("prefix tree", "Trie"),

                    // =====================================================
                    // BIT MANIPULATION
                    // =====================================================
                    Map.entry("bit manipulation", "Bit Manipulation"),
                    Map.entry("bitmask", "Bit Manipulation"),

                    // =====================================================
                    // MATH
                    // =====================================================
                    Map.entry("math", "Math"),
                    Map.entry("mathematics", "Math"),

                    // =====================================================
                    // PREFIX SUM
                    // =====================================================
                    Map.entry("prefix sum", "Prefix Sum"),
                    Map.entry("prefix sums", "Prefix Sum"),

                    // =====================================================
                    // UNION FIND
                    // =====================================================
                    Map.entry("union find", "Union Find"),
                    Map.entry("disjoint set", "Union Find"),
                    Map.entry("disjoint set union", "Union Find"),

                    // =====================================================
                    // INTERVALS
                    // =====================================================
                    Map.entry("interval", "Intervals"),
                    Map.entry("intervals", "Intervals"),

                    // =====================================================
                    // INPUT / OUTPUT
                    // =====================================================
                    Map.entry("input/output", "Input/Output"),
                    Map.entry("input output", "Input/Output"),
                    Map.entry("standard input/output", "Input/Output"),
                    Map.entry("standard input output", "Input/Output"),
                    Map.entry("input/output operations", "Input/Output"),
                    Map.entry("input output operations", "Input/Output"),
                    Map.entry("input handling", "Input/Output"),
                    Map.entry("output formatting", "Input/Output"),
                    Map.entry("io", "Input/Output")
            );

    private ConceptNormalizer() {
    }

    public static String normalize(String concept) {

        if (concept == null) {
            return "Other";
        }

        String cleanedConcept =
                concept
                        .trim()
                        .replaceAll("\\s+", " ");

        if (cleanedConcept.isBlank()) {
            return "Other";
        }

        String normalizedKey =
                cleanedConcept.toLowerCase(Locale.ROOT);

        String normalized =
                CONCEPT_ALIASES.get(normalizedKey);

        if (normalized != null) {
            return normalized;
        }

        // Partial matching fallback
        for (Map.Entry<String, String> entry : CONCEPT_ALIASES.entrySet()) {

            if (
                    normalizedKey.contains(entry.getKey())
                            || entry.getKey().contains(normalizedKey)
            ) {
                return entry.getValue();
            }
        }

        return cleanedConcept;
    }
}