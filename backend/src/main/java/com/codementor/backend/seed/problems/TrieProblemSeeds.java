package com.codementor.backend.seed.problems;

import com.codementor.backend.entity.Difficulty;
import com.codementor.backend.seed.ProblemExampleSeedData;
import com.codementor.backend.seed.ProblemSeedData;
import com.codementor.backend.seed.TestCaseSeedData;

import java.util.List;

public final class TrieProblemSeeds {

    private TrieProblemSeeds() {
    }

    public static List<ProblemSeedData> getProblems() {

        return List.of(

                // ==================================================
                // 1. LONGEST COMMON PREFIX
                // ==================================================

                new ProblemSeedData(

                        "Longest Common Prefix",

                        """
                        Given n lowercase English strings, find the longest prefix shared by every string.

                        A prefix is a sequence of characters that appears at the beginning of a string.

                        If no common prefix exists, print -1.

                        You may solve the problem by comparing the strings directly or by inserting the strings into a Trie.

                        Print the longest common prefix shared by all strings.
                        """,

                        Difficulty.EASY,

                        "trie",

                        """
                        1 <= n <= 100000

                        1 <= length of each string <= 1000

                        The total number of characters across all strings does not exceed 1000000.

                        Every string contains only lowercase English letters.
                        """,

                        """
                        The first line contains an integer n.

                        The next n lines each contain one lowercase English string.
                        """,

                        """
                        Print the longest common prefix shared by every string.

                        If no common prefix exists, print -1.
                        """,

                        """
                        3
                        flower
                        flow
                        flight
                        """,

                        "fl",

                        List.of(
                                "Trie",
                                "String",
                                "Prefix"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        3
                                        flower
                                        flow
                                        flight
                                        """,
                                        "fl",
                                        """
                                        Every string begins with the characters "fl".

                                        The next characters are different.

                                        Therefore, the longest common prefix is "fl".
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        3
                                        dog
                                        racecar
                                        car
                                        """,
                                        "-1",
                                        """
                                        The strings begin with different characters.

                                        Therefore, no common prefix exists.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        1
                                        coding
                                        """,
                                        "coding",
                                        """
                                        Only one string is provided.

                                        Therefore, the entire string is its longest common prefix.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        4
                                        apple
                                        app
                                        application
                                        apply
                                        """,
                                        "app",
                                        """
                                        Every string begins with "app".

                                        The strings differ after the third character.

                                        Therefore, the answer is "app".
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        3
                                        abc
                                        abc
                                        abc
                                        """,
                                        "abc",
                                        """
                                        All three strings are identical.

                                        Therefore, the complete string "abc" is the longest common prefix.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "3\nflower\nflow\nflight",
                                        "fl",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "3\ndog\nracecar\ncar",
                                        "-1",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "1\ncoding",
                                        "coding",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "4\napple\napp\napplication\napply",
                                        "app",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "3\nabc\nabc\nabc",
                                        "abc",
                                        true
                                )
                        )
                ),

                // ==================================================
                // 2. COUNT WORDS WITH PREFIX
                // ==================================================

                new ProblemSeedData(

                        "Count Words With Prefix",

                        """
                        Given n lowercase English words and a prefix, count how many words begin with that prefix.

                        A word begins with a prefix if all characters of the prefix match the beginning characters of the word.

                        The complete word may also be equal to the prefix.

                        Duplicate words must be counted separately.

                        An efficient solution can store the words in a Trie and maintain the number of words passing through each Trie node.

                        Print the number of words that begin with the given prefix.
                        """,

                        Difficulty.EASY,

                        "trie",

                        """
                        1 <= n <= 100000

                        1 <= length of each word, prefix <= 1000

                        The total number of characters across all words does not exceed 1000000.

                        All words and the prefix contain only lowercase English letters.
                        """,

                        """
                        The first line contains an integer n.

                        The next n lines each contain one lowercase English word.

                        The final line contains the prefix.
                        """,

                        """
                        Print the number of words that begin with the given prefix.
                        """,

                        """
                        5
                        apple
                        app
                        application
                        banana
                        apply
                        app
                        """,

                        "4",

                        List.of(
                                "Trie",
                                "String",
                                "Prefix Counting"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        5
                                        apple
                                        app
                                        application
                                        banana
                                        apply
                                        app
                                        """,
                                        "4",
                                        """
                                        The words apple, app, application, and apply begin with "app".

                                        Therefore, the answer is 4.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        3
                                        cat
                                        dog
                                        bird
                                        z
                                        """,
                                        "0",
                                        """
                                        None of the words begin with the prefix "z".

                                        Therefore, the answer is 0.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        4
                                        code
                                        coder
                                        coding
                                        codementor
                                        code
                                        """,
                                        "3",
                                        """
                                        The words code, coder, and codementor begin with "code".

                                        The word coding begins with "codi", so it does not match the complete prefix.

                                        Therefore, the answer is 3.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        4
                                        a
                                        ab
                                        abc
                                        abcd
                                        a
                                        """,
                                        "4",
                                        """
                                        Every word begins with the prefix "a".

                                        Therefore, the answer is 4.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        5
                                        test
                                        test
                                        testing
                                        team
                                        ten
                                        test
                                        """,
                                        "3",
                                        """
                                        Both copies of "test" and the word "testing" begin with the prefix "test".

                                        Duplicate words are counted separately.

                                        Therefore, the answer is 3.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "5\napple\napp\napplication\nbanana\napply\napp",
                                        "4",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "3\ncat\ndog\nbird\nz",
                                        "0",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "4\ncode\ncoder\ncoding\ncodementor\ncode",
                                        "3",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "4\na\nab\nabc\nabcd\na",
                                        "4",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "5\ntest\ntest\ntesting\nteam\nten\ntest",
                                        "3",
                                        true
                                )
                        )
                ),

                // ==================================================
                // 3. IMPLEMENT TRIE OPERATIONS
                // ==================================================

                new ProblemSeedData(

                        "Implement Trie Operations",

                        """
                        Implement a Trie that supports three operations on lowercase English words.

                        INSERT word adds the given word to the Trie.

                        SEARCH word checks whether the complete word exists in the Trie.

                        PREFIX prefix checks whether at least one inserted word begins with the given prefix.

                        For every SEARCH and PREFIX operation, print true or false.

                        INSERT operations do not produce output.

                        Words may be inserted more than once, but repeated insertion does not change the result of SEARCH or PREFIX operations.
                        """,

                        Difficulty.MEDIUM,

                        "trie",

                        """
                        1 <= number of operations <= 100000

                        1 <= length of each word or prefix <= 1000

                        The total number of characters across all operations does not exceed 1000000.

                        Every word and prefix contains only lowercase English letters.
                        """,

                        """
                        The first line contains an integer q representing the number of operations.

                        The next q lines each contain an operation followed by a lowercase English string.

                        The operation is one of INSERT, SEARCH, or PREFIX.
                        """,

                        """
                        For every SEARCH and PREFIX operation, print true or false on a separate line.
                        """,

                        """
                        6
                        INSERT apple
                        SEARCH apple
                        SEARCH app
                        PREFIX app
                        INSERT app
                        SEARCH app
                        """,

                        """
                        true
                        false
                        true
                        true
                        """,

                        List.of(
                                "Trie",
                                "String",
                                "Data Structure",
                                "Prefix Search"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        6
                                        INSERT apple
                                        SEARCH apple
                                        SEARCH app
                                        PREFIX app
                                        INSERT app
                                        SEARCH app
                                        """,
                                        """
                                        true
                                        false
                                        true
                                        true
                                        """,
                                        """
                                        After inserting "apple", searching for the complete word returns true.

                                        The complete word "app" has not yet been inserted, so SEARCH app returns false.

                                        However, "apple" begins with "app", so PREFIX app returns true.

                                        After inserting "app", SEARCH app returns true.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        4
                                        INSERT cat
                                        SEARCH dog
                                        PREFIX ca
                                        SEARCH cat
                                        """,
                                        """
                                        false
                                        true
                                        true
                                        """,
                                        """
                                        The word "dog" does not exist.

                                        The inserted word "cat" begins with "ca".

                                        Searching for the complete word "cat" returns true.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        3
                                        INSERT code
                                        PREFIX cod
                                        PREFIX coding
                                        """,
                                        """
                                        true
                                        false
                                        """,
                                        """
                                        The inserted word "code" begins with "cod".

                                        No inserted word begins with "coding".

                                        Therefore, the outputs are true and false.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        4
                                        INSERT a
                                        SEARCH a
                                        PREFIX a
                                        SEARCH ab
                                        """,
                                        """
                                        true
                                        true
                                        false
                                        """,
                                        """
                                        The word "a" exists and also begins with the prefix "a".

                                        The word "ab" has not been inserted.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        5
                                        INSERT hello
                                        INSERT hello
                                        SEARCH hello
                                        PREFIX hell
                                        SEARCH hell
                                        """,
                                        """
                                        true
                                        true
                                        false
                                        """,
                                        """
                                        Repeated insertion does not affect the behavior of the Trie.

                                        The complete word "hello" exists.

                                        At least one word begins with "hell".

                                        However, the complete word "hell" was never inserted.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "6\nINSERT apple\nSEARCH apple\nSEARCH app\nPREFIX app\nINSERT app\nSEARCH app",
                                        "true\nfalse\ntrue\ntrue",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "4\nINSERT cat\nSEARCH dog\nPREFIX ca\nSEARCH cat",
                                        "false\ntrue\ntrue",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "3\nINSERT code\nPREFIX cod\nPREFIX coding",
                                        "true\nfalse",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "4\nINSERT a\nSEARCH a\nPREFIX a\nSEARCH ab",
                                        "true\ntrue\nfalse",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "5\nINSERT hello\nINSERT hello\nSEARCH hello\nPREFIX hell\nSEARCH hell",
                                        "true\ntrue\nfalse",
                                        true
                                )
                        )
                ),

                // ==================================================
                // 4. REPLACE WORDS USING DICTIONARY ROOTS
                // ==================================================

                new ProblemSeedData(

                        "Replace Words Using Dictionary Roots",

                        """
                        You are given a dictionary containing root words and a sentence containing lowercase English words.

                        A root word can replace a word in the sentence when the sentence word begins with that root.

                        If more than one root can replace the same word, use the shortest matching root.

                        Words that do not begin with any dictionary root must remain unchanged.

                        Replace every possible word and print the resulting sentence.

                        An efficient solution can store all dictionary roots in a Trie.
                        """,

                        Difficulty.MEDIUM,

                        "trie",

                        """
                        1 <= number of roots <= 100000

                        1 <= length of each root <= 100

                        1 <= number of words in the sentence <= 100000

                        The total number of characters does not exceed 1000000.

                        All roots and sentence words contain only lowercase English letters.
                        """,

                        """
                        The first line contains an integer n representing the number of dictionary roots.

                        The second line contains n space-separated root words.

                        The third line contains the sentence.
                        """,

                        """
                        Print the sentence after replacing every possible word with its shortest matching root.
                        """,

                        """
                        3
                        cat bat rat
                        the cattle was rattled by the battery
                        """,

                        "the cat was rat by the bat",

                        List.of(
                                "Trie",
                                "String",
                                "Prefix"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        3
                                        cat bat rat
                                        the cattle was rattled by the battery
                                        """,
                                        "the cat was rat by the bat",
                                        """
                                        "cattle" begins with "cat", so it becomes "cat".

                                        "rattled" begins with "rat", so it becomes "rat".

                                        "battery" begins with "bat", so it becomes "bat".

                                        The remaining words do not match any dictionary root.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        2
                                        a ab
                                        abc abcd xyz
                                        """,
                                        "a a xyz",
                                        """
                                        Both "a" and "ab" can replace words beginning with "ab".

                                        The shortest matching root is "a".

                                        Therefore, both abc and abcd become "a".
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        1
                                        code
                                        coding coder hello
                                        """,
                                        "code code hello",
                                        """
                                        The words coding and coder begin with the root "code".

                                        The word hello does not match the root and remains unchanged.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        2
                                        cat dog
                                        bird fish
                                        """,
                                        "bird fish",
                                        """
                                        Neither sentence word begins with a dictionary root.

                                        Therefore, the sentence remains unchanged.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        3
                                        pro program programmer
                                        programming programmer project
                                        """,
                                        "pro pro pro",
                                        """
                                        Every sentence word begins with the root "pro".

                                        Since "pro" is the shortest matching root, every word is replaced by "pro".
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "3\ncat bat rat\nthe cattle was rattled by the battery",
                                        "the cat was rat by the bat",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "2\na ab\nabc abcd xyz",
                                        "a a xyz",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "1\ncode\ncoding coder hello",
                                        "code code hello",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "2\ncat dog\nbird fish",
                                        "bird fish",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "3\npro program programmer\nprogramming programmer project",
                                        "pro pro pro",
                                        true
                                )
                        )
                ),

                // ==================================================
                // 5. MAXIMUM XOR OF TWO NUMBERS
                // ==================================================

                new ProblemSeedData(

                        "Maximum XOR of Two Numbers",

                        """
                        Given an array containing n non-negative integers, find the maximum XOR value obtainable by choosing two different elements from the array.

                        The XOR operation compares the binary representations of two numbers.

                        A result bit is 1 when the corresponding bits of the two numbers are different.

                        You must select two different array positions.

                        An efficient solution can insert the binary representation of each number into a binary Trie.

                        For every number, try to follow the opposite bit at each Trie level whenever possible to maximize the resulting XOR value.

                        Print the maximum XOR value.
                        """,

                        Difficulty.HARD,

                        "trie",

                        """
                        2 <= n <= 100000

                        0 <= array[i] <= 2147483647
                        """,

                        """
                        The first line contains an integer n.

                        The second line contains n space-separated non-negative integers.
                        """,

                        """
                        Print the maximum XOR value obtainable from two different array elements.
                        """,

                        """
                        6
                        3 10 5 25 2 8
                        """,

                        "28",

                        List.of(
                                "Trie",
                                "Binary Trie",
                                "Bit Manipulation",
                                "XOR"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        """
                                        6
                                        3 10 5 25 2 8
                                        """,
                                        "28",
                                        """
                                        Choosing the values 5 and 25 produces the maximum XOR.

                                        5 XOR 25 equals 28.

                                        Therefore, the answer is 28.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        2
                                        0 1
                                        """,
                                        "1",
                                        """
                                        The only possible pair contains 0 and 1.

                                        Their XOR value is 1.

                                        Therefore, the answer is 1.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        3
                                        7 7 7
                                        """,
                                        "0",
                                        """
                                        XOR of two equal values is 0.

                                        Since every array element equals 7, the maximum XOR is 0.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        4
                                        1 2 4 8
                                        """,
                                        "12",
                                        """
                                        Choosing 4 and 8 produces 4 XOR 8 = 12.

                                        No other pair produces a larger XOR value.

                                        Therefore, the answer is 12.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        """
                                        5
                                        14 70 53 83 49
                                        """,
                                        "119",
                                        """
                                        Choosing the values 70 and 49 produces the maximum XOR.

                                        70 XOR 49 equals 119.

                                        No other pair of values in the array produces a larger XOR value.

                                        Therefore, the maximum XOR is 119.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "6\n3 10 5 25 2 8",
                                        "28",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "2\n0 1",
                                        "1",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "3\n7 7 7",
                                        "0",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "4\n1 2 4 8",
                                        "12",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "5\n14 70 53 83 49",
                                        "119",
                                        true
                                )
                        )
                )
        );
    }
}