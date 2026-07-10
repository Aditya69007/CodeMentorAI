package com.codementor.backend.seed.problems;

import com.codementor.backend.entity.Difficulty;
import com.codementor.backend.seed.ProblemExampleSeedData;
import com.codementor.backend.seed.ProblemSeedData;
import com.codementor.backend.seed.TestCaseSeedData;

import java.util.List;

public final class StringProblemSeeds {

    private StringProblemSeeds() {
    }

    public static List<ProblemSeedData> getProblems() {

        return List.of(

                // ==================================================
                // PALINDROME STRING
                // ==================================================

                new ProblemSeedData(

                        "Palindrome String",

                        """
                        Given a string s containing lowercase English letters, determine whether the string is a palindrome.

                        A palindrome is a sequence of characters that reads exactly the same from left to right and from right to left.

                        For example, "racecar" is a palindrome because reversing the string produces the same string.

                        However, "hello" is not a palindrome because reading it from right to left produces "olleh", which is different from the original string.

                        Print true if the given string is a palindrome. Otherwise, print false.
                        """,

                        Difficulty.EASY,

                        "string",

                        """
                        1 <= length of s <= 100000

                        s contains only lowercase English letters.
                        """,

                        """
                        A single string s containing lowercase English letters.
                        """,

                        """
                        Print true if the given string is a palindrome.

                        Otherwise, print false.
                        """,

                        "racecar",

                        "true",

                        List.of(
                                "String",
                                "Two Pointers"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        "racecar",
                                        "true",
                                        """
                                        Reading "racecar" from left to right gives the same sequence as reading it from right to left.

                                        Therefore, the string is a palindrome.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        "hello",
                                        "false",
                                        """
                                        Reversing "hello" produces "olleh".

                                        Since the reversed string is different from the original string, it is not a palindrome.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        "a",
                                        "true",
                                        """
                                        A string containing only one character is always a palindrome.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        "abba",
                                        "true",
                                        """
                                        The first and last characters match, and the two middle characters also match.

                                        Therefore, the string reads the same in both directions.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        "abcba",
                                        "true",
                                        """
                                        Reading the string from either direction produces "abcba".

                                        Therefore, it is a palindrome.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData("racecar", "true", false),
                                new TestCaseSeedData("hello", "false", false),
                                new TestCaseSeedData("a", "true", true),
                                new TestCaseSeedData("abba", "true", true),
                                new TestCaseSeedData("abcba", "true", true),
                                new TestCaseSeedData("abcdef", "false", true),
                                new TestCaseSeedData("aa", "true", true)
                        )
                ),

                // ==================================================
                // LONGEST COMMON PREFIX
                // ==================================================

                new ProblemSeedData(

                        "Longest Common Prefix",

                        """
                        Given n strings containing lowercase English letters, find the longest prefix shared by every string.

                        A prefix is a sequence of characters that appears at the beginning of a string.

                        For example, the strings "flower", "flow", and "flight" all begin with the characters "fl". Therefore, their longest common prefix is "fl".

                        The prefix must be shared by every input string.

                        If the strings do not have any common prefix, print -1.
                        """,

                        Difficulty.EASY,

                        "string",

                        """
                        1 <= n <= 10000

                        1 <= length of each string <= 1000

                        Each string contains only lowercase English letters.

                        The total number of characters across all strings does not exceed 1000000.
                        """,

                        """
                        The first line contains an integer n representing the number of strings.

                        Each of the next n lines contains one string.
                        """,

                        """
                        Print the longest prefix shared by all strings.

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
                                "String",
                                "Prefix"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        "3\nflower\nflow\nflight",
                                        "fl",
                                        """
                                        All three strings begin with "f" followed by "l".

                                        The next characters are different. Therefore, "fl" is the longest common prefix.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        "3\ndog\nracecar\ncar",
                                        "-1",
                                        """
                                        The first characters of the strings are different.

                                        Therefore, there is no prefix shared by every string.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        "4\ncoding\ncoding\ncoding\ncoding",
                                        "coding",
                                        """
                                        Every string is identical.

                                        Therefore, the entire string "coding" is the longest common prefix.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        "3\ninterstellar\ninternet\ninternal",
                                        "inter",
                                        """
                                        All strings begin with "inter".

                                        The characters immediately after this prefix are different.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        "1\nalgorithm",
                                        "algorithm",
                                        """
                                        There is only one string.

                                        Therefore, the complete string is its longest common prefix.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData("3\nflower\nflow\nflight", "fl", false),
                                new TestCaseSeedData("3\ndog\nracecar\ncar", "-1", false),
                                new TestCaseSeedData("4\ncoding\ncoding\ncoding\ncoding", "coding", true),
                                new TestCaseSeedData("3\ninterstellar\ninternet\ninternal", "inter", true),
                                new TestCaseSeedData("1\nalgorithm", "algorithm", true),
                                new TestCaseSeedData("4\napple\napplication\napply\napp", "app", true)
                        )
                ),

                // ==================================================
                // VALID ANAGRAM
                // ==================================================

                new ProblemSeedData(

                        "Valid Anagram",

                        """
                        Given two strings s and t, determine whether t is an anagram of s.

                        Two strings are anagrams when they contain exactly the same characters with exactly the same frequencies, although the characters may appear in a different order.

                        For example, "anagram" and "nagaram" are anagrams because both strings contain the same letters the same number of times.

                        Print true if the two strings are anagrams. Otherwise, print false.
                        """,

                        Difficulty.EASY,

                        "string",

                        """
                        1 <= length of s, t <= 100000

                        s and t contain only lowercase English letters.
                        """,

                        """
                        The first line contains string s.

                        The second line contains string t.
                        """,

                        """
                        Print true if t is an anagram of s.

                        Otherwise, print false.
                        """,

                        "anagram\nnagaram",

                        "true",

                        List.of(
                                "String",
                                "Hashing",
                                "Frequency Counting"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        "anagram\nnagaram",
                                        "true",
                                        """
                                        Both strings contain exactly the same characters with the same frequencies.

                                        Therefore, they are anagrams.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        "rat\ncar",
                                        "false",
                                        """
                                        The character frequencies are different.

                                        Therefore, the strings are not anagrams.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        "listen\nsilent",
                                        "true",
                                        """
                                        Both strings contain exactly the same six characters.

                                        Only their order is different.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        "aacc\nccac",
                                        "false",
                                        """
                                        The first string contains two 'a' characters, while the second contains only one.

                                        Therefore, they are not anagrams.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        "a\na",
                                        "true",
                                        """
                                        Both strings contain the same single character.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData("anagram\nnagaram", "true", false),
                                new TestCaseSeedData("rat\ncar", "false", false),
                                new TestCaseSeedData("listen\nsilent", "true", true),
                                new TestCaseSeedData("aacc\nccac", "false", true),
                                new TestCaseSeedData("a\na", "true", true),
                                new TestCaseSeedData("abc\nabcd", "false", true),
                                new TestCaseSeedData("zzzza\nazzzz", "true", true)
                        )
                ),

                // ==================================================
                // FIRST NON-REPEATING CHARACTER
                // ==================================================

                new ProblemSeedData(

                        "First Non-Repeating Character",

                        """
                        Given a string s containing lowercase English letters, find the first character that appears exactly once in the entire string.

                        The answer must be determined according to the original left-to-right order of the string.

                        For example, in "leetcode", the character 'l' appears exactly once and occurs before every other non-repeating character.

                        If every character appears more than once, print -1.
                        """,

                        Difficulty.EASY,

                        "string",

                        """
                        1 <= length of s <= 100000

                        s contains only lowercase English letters.
                        """,

                        "A single string s containing lowercase English letters.",

                        """
                        Print the first character that occurs exactly once.

                        If no non-repeating character exists, print -1.
                        """,

                        "leetcode",

                        "l",

                        List.of(
                                "String",
                                "Hashing",
                                "Frequency Counting"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        "leetcode",
                                        "l",
                                        """
                                        The character 'l' appears exactly once and is the first unique character from left to right.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        "loveleetcode",
                                        "v",
                                        """
                                        The characters 'l' and 'o' repeat.

                                        The character 'v' is the first character that appears exactly once.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        "aabb",
                                        "-1",
                                        """
                                        Every character appears twice.

                                        Therefore, no non-repeating character exists.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        "z",
                                        "z",
                                        """
                                        The only character appears exactly once.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        "aabbccd",
                                        "d",
                                        """
                                        The characters 'a', 'b', and 'c' repeat.

                                        The character 'd' appears exactly once.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData("leetcode", "l", false),
                                new TestCaseSeedData("loveleetcode", "v", false),
                                new TestCaseSeedData("aabb", "-1", true),
                                new TestCaseSeedData("z", "z", true),
                                new TestCaseSeedData("aabbccd", "d", true),
                                new TestCaseSeedData("abcabcde", "d", true),
                                new TestCaseSeedData("xxyyzzqrr", "q", true)
                        )
                ),

                // ==================================================
                // LONGEST SUBSTRING WITHOUT REPEATING CHARACTERS
                // ==================================================

                new ProblemSeedData(

                        "Longest Substring Without Repeating Characters",

                        """
                        Given a string s containing lowercase English letters, find the length of the longest contiguous substring that contains no repeated characters.

                        A substring is a continuous sequence of characters from the original string.

                        Every character inside the selected substring must be unique.

                        For example, in "abcabcbb", the substring "abc" contains no repeated characters and has length 3.

                        No longer valid substring exists, so the answer is 3.
                        """,

                        Difficulty.MEDIUM,

                        "string",

                        """
                        1 <= length of s <= 100000

                        s contains only lowercase English letters.
                        """,

                        "A single string s containing lowercase English letters.",

                        "Print the length of the longest substring containing no repeated characters.",

                        "abcabcbb",

                        "3",

                        List.of(
                                "String",
                                "Sliding Window",
                                "Hashing"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        "abcabcbb",
                                        "3",
                                        """
                                        The substring "abc" contains three unique characters.

                                        No longer substring without repetition exists.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        "bbbbb",
                                        "1",
                                        """
                                        Every character is 'b'.

                                        Therefore, a valid substring can contain only one character.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        "pwwkew",
                                        "3",
                                        """
                                        The substring "wke" contains three unique characters.

                                        Therefore, the maximum length is 3.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        "abcdef",
                                        "6",
                                        """
                                        Every character is unique.

                                        Therefore, the complete string is valid.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        "abba",
                                        "2",
                                        """
                                        Valid longest substrings include "ab" and "ba".

                                        Both have length 2.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData("abcabcbb", "3", false),
                                new TestCaseSeedData("bbbbb", "1", false),
                                new TestCaseSeedData("pwwkew", "3", true),
                                new TestCaseSeedData("abcdef", "6", true),
                                new TestCaseSeedData("abba", "2", true),
                                new TestCaseSeedData("a", "1", true),
                                new TestCaseSeedData("dvdf", "3", true),
                                new TestCaseSeedData("anviaj", "5", true)
                        )
                ),

                // ==================================================
                // STRING COMPRESSION
                // ==================================================

                new ProblemSeedData(

                        "String Compression",

                        """
                        Given a string s containing lowercase English letters, compress the string by replacing each group of consecutive identical characters with the character followed by the number of times it appears consecutively.

                        A group consists only of equal characters that appear next to each other.

                        For example, the string "aaabbc" contains three consecutive 'a' characters, two consecutive 'b' characters, and one 'c'.

                        Therefore, its compressed representation is "a3b2c1".

                        Even when a character appears only once in a group, its count must still be included.
                        """,

                        Difficulty.EASY,

                        "string",

                        """
                        1 <= length of s <= 100000

                        s contains only lowercase English letters.
                        """,

                        "A single string s containing lowercase English letters.",

                        "Print the compressed representation of the string.",

                        "aaabbc",

                        "a3b2c1",

                        List.of(
                                "String",
                                "Two Pointers",
                                "Simulation"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        "aaabbc",
                                        "a3b2c1",
                                        """
                                        The string contains three consecutive 'a' characters, followed by two 'b' characters and one 'c'.

                                        Therefore, the compressed result is "a3b2c1".
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        "abcd",
                                        "a1b1c1d1",
                                        """
                                        No character repeats consecutively.

                                        Therefore, every character receives a count of 1.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        "aaaaa",
                                        "a5",
                                        """
                                        All five characters belong to one consecutive group.

                                        Therefore, the result is "a5".
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        "aabbaa",
                                        "a2b2a2",
                                        """
                                        The two groups of 'a' characters are separated by 'b' characters.

                                        Therefore, they must be compressed as separate groups.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        "z",
                                        "z1",
                                        """
                                        The single character forms one group with a frequency of 1.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData("aaabbc", "a3b2c1", false),
                                new TestCaseSeedData("abcd", "a1b1c1d1", false),
                                new TestCaseSeedData("aaaaa", "a5", true),
                                new TestCaseSeedData("aabbaa", "a2b2a2", true),
                                new TestCaseSeedData("z", "z1", true),
                                new TestCaseSeedData("xxxyzzzz", "x3y1z4", true)
                        )
                ),

                // ==================================================
                // REVERSE WORDS IN A STRING
                // ==================================================

                new ProblemSeedData(

                        "Reverse Words in a String",

                        """
                        Given a line of text containing one or more words, reverse the order of the words.

                        A word is a sequence of non-space characters.

                        The input may contain multiple spaces between words and may also contain leading or trailing spaces.

                        The output must contain the words in reverse order separated by exactly one space.

                        The output must not contain leading or trailing spaces.

                        For example, reversing the words in "the sky is blue" produces "blue is sky the".
                        """,

                        Difficulty.MEDIUM,

                        "string",

                        """
                        1 <= length of input line <= 100000

                        The input contains English letters and spaces.

                        The input contains at least one word.
                        """,

                        "A single line containing one or more words.",

                        """
                        Print the words in reverse order.

                        Separate adjacent words using exactly one space.
                        """,

                        "the sky is blue",

                        "blue is sky the",

                        List.of(
                                "String",
                                "Two Pointers"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        "the sky is blue",
                                        "blue is sky the",
                                        """
                                        The original word order is "the", "sky", "is", "blue".

                                        Reversing this order produces "blue is sky the".
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        "  hello world  ",
                                        "world hello",
                                        """
                                        Leading and trailing spaces are removed.

                                        The two words are then printed in reverse order.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        "a good   example",
                                        "example good a",
                                        """
                                        Multiple spaces between words are treated as separators.

                                        The result uses exactly one space between words.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        "coding",
                                        "coding",
                                        """
                                        The input contains only one word, so its position does not change.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        "learn code solve grow",
                                        "grow solve code learn",
                                        """
                                        Reversing the four-word sequence places "grow" first and "learn" last.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData("the sky is blue", "blue is sky the", false),
                                new TestCaseSeedData("  hello world  ", "world hello", false),
                                new TestCaseSeedData("a good   example", "example good a", true),
                                new TestCaseSeedData("coding", "coding", true),
                                new TestCaseSeedData("learn code solve grow", "grow solve code learn", true),
                                new TestCaseSeedData("   one   two three   ", "three two one", true)
                        )
                ),

                // ==================================================
                // GROUP ANAGRAMS
                // ==================================================

                new ProblemSeedData(

                        "Group Anagrams",

                        """
                        Given n lowercase strings, group together all strings that are anagrams of each other.

                        Two strings are anagrams when they contain exactly the same characters with exactly the same frequencies.

                        The characters may appear in different orders.

                        Each group must be printed on a separate line.

                        Inside each group, preserve the original input order of the strings.

                        The groups themselves must be printed according to the first appearance of any member of that group in the input.

                        First print the total number of groups.
                        """,

                        Difficulty.MEDIUM,

                        "string",

                        """
                        1 <= n <= 10000

                        1 <= length of each string <= 1000

                        Every string contains only lowercase English letters.

                        The total number of characters does not exceed 1000000.
                        """,

                        """
                        The first line contains an integer n.

                        Each of the next n lines contains one lowercase string.
                        """,

                        """
                        Print the total number of anagram groups on the first line.

                        Then print each group on a separate line.

                        Separate strings inside a group using one space.

                        Preserve input order inside each group and preserve group creation order.
                        """,

                        "6\neat\ntea\ntan\nate\nnat\nbat",

                        "3\neat tea ate\ntan nat\nbat",

                        List.of(
                                "String",
                                "Hashing",
                                "Sorting"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        "6\neat\ntea\ntan\nate\nnat\nbat",
                                        "3\neat tea ate\ntan nat\nbat",
                                        """
                                        "eat", "tea", and "ate" contain the same characters.

                                        "tan" and "nat" form another group.

                                        "bat" has no matching anagram.

                                        Therefore, three groups are produced.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        "3\nabc\nbca\ncab",
                                        "1\nabc bca cab",
                                        """
                                        Every string contains one 'a', one 'b', and one 'c'.

                                        Therefore, all strings belong to one group.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        "4\na\nb\nc\nd",
                                        "4\na\nb\nc\nd",
                                        """
                                        None of the single-character strings are anagrams of each other.

                                        Therefore, four separate groups are created.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        "5\nlisten\nsilent\nenlist\ngoogle\ngoogol",
                                        "2\nlisten silent enlist\ngoogle googol",
                                        """
                                        The first three strings are anagrams.

                                        The final two strings are also anagrams.

                                        Therefore, two groups are produced.
                                        """,
                                        4
                                )
                        ),

                        List.of(
                                new TestCaseSeedData(
                                        "6\neat\ntea\ntan\nate\nnat\nbat",
                                        "3\neat tea ate\ntan nat\nbat",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "3\nabc\nbca\ncab",
                                        "1\nabc bca cab",
                                        false
                                ),
                                new TestCaseSeedData(
                                        "4\na\nb\nc\nd",
                                        "4\na\nb\nc\nd",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "5\nlisten\nsilent\nenlist\ngoogle\ngoogol",
                                        "2\nlisten silent enlist\ngoogle googol",
                                        true
                                ),
                                new TestCaseSeedData(
                                        "1\ncode",
                                        "1\ncode",
                                        true
                                )
                        )
                ),

                // ==================================================
                // LONGEST PALINDROMIC SUBSTRING
                // ==================================================

                new ProblemSeedData(

                        "Longest Palindromic Substring",

                        """
                        Given a string s containing lowercase English letters, find the longest contiguous substring that is a palindrome.

                        A palindrome reads exactly the same from left to right and from right to left.

                        A substring must contain consecutive characters from the original string.

                        If multiple longest palindromic substrings have the same maximum length, print the one that appears first in the original string.

                        For example, the string "babad" contains the palindromic substrings "bab" and "aba", both with length 3.

                        Since "bab" appears first, the answer is "bab".
                        """,

                        Difficulty.MEDIUM,

                        "string",

                        """
                        1 <= length of s <= 2000

                        s contains only lowercase English letters.
                        """,

                        "A single string s containing lowercase English letters.",

                        """
                        Print the longest palindromic substring.

                        If multiple answers have the same maximum length, print the one that appears first.
                        """,

                        "babad",

                        "bab",

                        List.of(
                                "String",
                                "Dynamic Programming",
                                "Two Pointers"
                        ),

                        List.of(

                                new ProblemExampleSeedData(
                                        "babad",
                                        "bab",
                                        """
                                        Both "bab" and "aba" are palindromes of length 3.

                                        No longer palindromic substring exists.

                                        Since "bab" appears first, it is the required answer.
                                        """,
                                        1
                                ),

                                new ProblemExampleSeedData(
                                        "cbbd",
                                        "bb",
                                        """
                                        The substring "bb" reads the same in both directions.

                                        It is the longest palindromic substring.
                                        """,
                                        2
                                ),

                                new ProblemExampleSeedData(
                                        "a",
                                        "a",
                                        """
                                        A single character is always a palindrome.
                                        """,
                                        3
                                ),

                                new ProblemExampleSeedData(
                                        "racecar",
                                        "racecar",
                                        """
                                        The complete string reads the same from left to right and right to left.

                                        Therefore, the entire string is the answer.
                                        """,
                                        4
                                ),

                                new ProblemExampleSeedData(
                                        "forgeeksskeegfor",
                                        "geeksskeeg",
                                        """
                                        The substring "geeksskeeg" is a palindrome.

                                        No longer palindromic substring exists in the input.
                                        """,
                                        5
                                )
                        ),

                        List.of(
                                new TestCaseSeedData("babad", "bab", false),
                                new TestCaseSeedData("cbbd", "bb", false),
                                new TestCaseSeedData("a", "a", true),
                                new TestCaseSeedData("racecar", "racecar", true),
                                new TestCaseSeedData("forgeeksskeegfor", "geeksskeeg", true),
                                new TestCaseSeedData("ac", "a", true),
                                new TestCaseSeedData("aaaa", "aaaa", true),
                                new TestCaseSeedData("abacdfgdcaba", "aba", true)
                        )
                )
        );
    }
}