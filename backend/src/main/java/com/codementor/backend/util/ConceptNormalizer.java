package com.codementor.backend.util;

import java.util.Locale;
import java.util.Map;

public final class ConceptNormalizer {

    private static final Map<String, String> CONCEPT_ALIASES =
            Map.ofEntries(

                    // STACK
                    Map.entry("stack", "Stack"),
                    Map.entry("stack data structure", "Stack"),
                    Map.entry("stacks", "Stack"),
                    Map.entry("parentheses matching", "Stack"),
                    Map.entry("bracket matching", "Stack"),
                    Map.entry("balanced parentheses", "Stack"),

                    // QUEUE
                    Map.entry("queue", "Queue"),
                    Map.entry("queue data structure", "Queue"),
                    Map.entry("queues", "Queue"),

                    // HASHING
                    Map.entry("hash map", "Hashing"),
                    Map.entry("hashmap", "Hashing"),
                    Map.entry("hash table", "Hashing"),
                    Map.entry("hashing", "Hashing"),

                    // BINARY SEARCH
                    Map.entry("binary search", "Binary Search"),
                    Map.entry(
                            "binary search algorithm",
                            "Binary Search"
                    ),

                    // LINKED LIST
                    Map.entry("linked list", "Linked List"),
                    Map.entry("linked lists", "Linked List"),

                    // TWO POINTERS
                    Map.entry("two pointer", "Two Pointers"),
                    Map.entry("two pointers", "Two Pointers"),
                    Map.entry(
                            "two pointer technique",
                            "Two Pointers"
                    ),

                    // SLIDING WINDOW
                    Map.entry(
                            "sliding window",
                            "Sliding Window"
                    ),
                    Map.entry(
                            "sliding window technique",
                            "Sliding Window"
                    ),

                    // RECURSION
                    Map.entry("recursion", "Recursion"),
                    Map.entry(
                            "recursive algorithm",
                            "Recursion"
                    ),

                    // DYNAMIC PROGRAMMING
                    Map.entry(
                            "dynamic programming",
                            "Dynamic Programming"
                    ),
                    Map.entry("dp", "Dynamic Programming"),

                    // GRAPH
                    Map.entry("graph", "Graph"),
                    Map.entry("graphs", "Graph"),
                    Map.entry(
                            "graph traversal",
                            "Graph Traversal"
                    ),

                    // TREE
                    Map.entry("tree", "Tree"),
                    Map.entry("trees", "Tree"),
                    Map.entry(
                            "binary tree",
                            "Binary Tree"
                    ),

                    // INPUT OUTPUT
                    Map.entry(
                            "standard input/output",
                            "Input/Output"
                    ),
                    Map.entry(
                            "standard input output",
                            "Input/Output"
                    ),
                    Map.entry(
                            "input/output",
                            "Input/Output"
                    ),
                    Map.entry(
                            "input output",
                            "Input/Output"
                    )
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
                cleanedConcept.toLowerCase(
                        Locale.ROOT
                );


        return CONCEPT_ALIASES.getOrDefault(
                normalizedKey,
                cleanedConcept
        );
    }
}