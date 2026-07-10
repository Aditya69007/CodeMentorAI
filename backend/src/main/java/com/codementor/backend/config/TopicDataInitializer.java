package com.codementor.backend.config;

import com.codementor.backend.entity.Topic;
import com.codementor.backend.repository.TopicRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TopicDataInitializer
        implements CommandLineRunner {

    private final TopicRepository topicRepository;


    @Override
    public void run(String... args) {

        List<TopicSeed> topics =
                List.of(

                        new TopicSeed(
                                "Array",
                                "array",
                                "Practice array manipulation, traversal, prefix sums, and common array algorithms."
                        ),

                        new TopicSeed(
                                "String",
                                "string",
                                "Practice string processing, matching, manipulation, and common string algorithms."
                        ),

                        new TopicSeed(
                                "Hashing",
                                "hashing",
                                "Practice hash maps, hash sets, frequency counting, and lookup techniques."
                        ),

                        new TopicSeed(
                                "Two Pointers",
                                "two-pointers",
                                "Practice efficient two-pointer techniques for arrays and strings."
                        ),

                        new TopicSeed(
                                "Sliding Window",
                                "sliding-window",
                                "Practice fixed and variable-size sliding window techniques."
                        ),

                        new TopicSeed(
                                "Stack",
                                "stack",
                                "Practice stack problems, parentheses matching, monotonic stacks, and expression processing."
                        ),

                        new TopicSeed(
                                "Queue",
                                "queue",
                                "Practice queues, deques, circular queues, and queue-based algorithms."
                        ),

                        new TopicSeed(
                                "Linked List",
                                "linked-list",
                                "Practice singly linked lists, doubly linked lists, fast-slow pointers, and list manipulation."
                        ),

                        new TopicSeed(
                                "Binary Search",
                                "binary-search",
                                "Practice binary search, search spaces, boundaries, and optimization problems."
                        ),

                        new TopicSeed(
                                "Sorting",
                                "sorting",
                                "Practice sorting algorithms, custom ordering, and sorting-based problem solving."
                        ),

                        new TopicSeed(
                                "Recursion",
                                "recursion",
                                "Practice recursive thinking, base cases, and recursive problem solving."
                        ),

                        new TopicSeed(
                                "Backtracking",
                                "backtracking",
                                "Practice subsets, permutations, combinations, and search-space exploration."
                        ),

                        new TopicSeed(
                                "Tree",
                                "tree",
                                "Practice tree traversal, tree properties, and recursive tree algorithms."
                        ),

                        new TopicSeed(
                                "Binary Search Tree",
                                "binary-search-tree",
                                "Practice BST searching, insertion, deletion, validation, and traversal."
                        ),

                        new TopicSeed(
                                "Heap",
                                "heap",
                                "Practice priority queues, heaps, top-k problems, and heap-based algorithms."
                        ),

                        new TopicSeed(
                                "Graph",
                                "graph",
                                "Practice graph representation, traversal, connectivity, and graph algorithms."
                        ),

                        new TopicSeed(
                                "Breadth-First Search",
                                "breadth-first-search",
                                "Practice BFS traversal, shortest paths, and level-based exploration."
                        ),

                        new TopicSeed(
                                "Depth-First Search",
                                "depth-first-search",
                                "Practice DFS traversal, connected components, cycles, and recursive graph exploration."
                        ),

                        new TopicSeed(
                                "Greedy",
                                "greedy",
                                "Practice greedy choices, intervals, scheduling, and optimization strategies."
                        ),

                        new TopicSeed(
                                "Dynamic Programming",
                                "dynamic-programming",
                                "Practice memoization, tabulation, state transitions, and optimization problems."
                        ),

                        new TopicSeed(
                                "Trie",
                                "trie",
                                "Practice prefix trees, dictionary search, and string lookup problems."
                        ),

                        new TopicSeed(
                                "Bit Manipulation",
                                "bit-manipulation",
                                "Practice bitwise operators, masks, XOR techniques, and binary representations."
                        ),

                        new TopicSeed(
                                "Math",
                                "math",
                                "Practice number theory, arithmetic, combinatorics, and mathematical algorithms."
                        ),

                        new TopicSeed(
                                "Matrix",
                                "matrix",
                                "Practice two-dimensional arrays, grid traversal, and matrix algorithms."
                        ),

                        new TopicSeed(
                                "Disjoint Set",
                                "disjoint-set",
                                "Practice Union-Find, connected components, and dynamic connectivity."
                        )
                );


        for (TopicSeed topicSeed : topics) {

            if (
                    !topicRepository.existsBySlug(
                            topicSeed.slug()
                    )
            ) {

                Topic topic =
                        Topic
                                .builder()
                                .name(topicSeed.name())
                                .slug(topicSeed.slug())
                                .description(
                                        topicSeed.description()
                                )
                                .active(true)
                                .build();

                topicRepository.save(topic);
            }
        }
    }


    private record TopicSeed(
            String name,
            String slug,
            String description
    ) {
    }
}