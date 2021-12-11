package day10

import readInput
import java.util.*


fun main() {
    fun Char.isOpeningBracket(): Boolean {
        return this == '(' || this == '[' || this == '{' || this == '<'
    }

    fun isRoundBracketPair(openBracket: Char, closeBracket: Char): Boolean {
        return openBracket == '(' && closeBracket == ')'
    }

    fun isSquareBracketPair(openBracket: Char, closeBracket: Char): Boolean {
        return openBracket == '[' && closeBracket == ']'
    }

    fun isCurlyBracketPair(openBracket: Char, closeBracket: Char): Boolean {
        return openBracket == '{' && closeBracket == '}'
    }

    fun isAngleBracketPair(openBracket: Char, closeBracket: Char): Boolean {
        return openBracket == '<' && closeBracket == '>'
    }

    fun Char.isMatchingWith(closingBracket: Char): Boolean {
        return isRoundBracketPair(this, closingBracket)
                || isSquareBracketPair(this, closingBracket)
                || isCurlyBracketPair(this, closingBracket)
                || isAngleBracketPair(this, closingBracket)
    }

    fun findFirstIncorrectBracketIgnoringIncomplete(charArray: CharArray): Optional<Char> {
        val stack = Stack<Char>()
        for (bracket in charArray) {
            if (bracket.isOpeningBracket()) {
                stack.push(bracket)
            } else {
                val topBracket = stack.peek()
                if (topBracket.isMatchingWith(bracket)) {
                    stack.pop()
                } else {
                    return Optional.of(bracket)
                }
            }
        }
        return Optional.empty()
    }

    fun findIncompleteCharacters(charArray: CharArray): Optional<Stack<Char>> {
        val stack = Stack<Char>()
        for (bracket in charArray) {
            if (bracket.isOpeningBracket()) {
                stack.push(bracket)
            } else {
                val topBracket = stack.peek()
                if (topBracket.isMatchingWith(bracket)) {
                    stack.pop()
                } else {
                    return Optional.empty()
                }
            }
        }
        return Optional.of(stack)
    }

    val pointsPerBracket = mapOf(
            Pair(')', 3),
            Pair(']', 57),
            Pair('}', 1197),
            Pair('>', 25137)
    )

    fun part1(input: List<String>): Int {
        val corruptedBrackets = mutableMapOf<Char, Int>()
        corruptedBrackets[')'] = 0
        corruptedBrackets[']'] = 0
        corruptedBrackets['}'] = 0
        corruptedBrackets['>'] = 0
        for (line in input) {
            val charArray = line.toCharArray()
            findFirstIncorrectBracketIgnoringIncomplete(charArray)
                    .ifPresent { corruptedBrackets[it] = corruptedBrackets[it]!! + 1 }
        }
        return corruptedBrackets.keys.sumOf { corruptedBrackets[it]!! * pointsPerBracket[it]!! }
    }

    val matchingClosingBrackets = mapOf(
            Pair('(', ')'),
            Pair('[', ']'),
            Pair('{', '}'),
            Pair('<', '>')
    )

    val scoreOfClosingCharacter = mapOf(
            Pair(')', 1L),
            Pair(']', 2L),
            Pair('}', 3L),
            Pair('>', 4L)
    )

    fun getCharactersToFill(stack: Stack<Char>): List<Char> {
        val listOfChars = mutableListOf<Char>()
        while (!stack.isEmpty()) {
            val openBracket = stack.pop()
            val closingBracket = matchingClosingBrackets[openBracket]
            listOfChars.add(closingBracket!!)
        }
        return listOfChars
    }

    fun countScore(listOfChars: List<Char>): Long {
        return listOfChars
                .map { scoreOfClosingCharacter[it] }
                .reduce { acc, characterValue -> acc!! * 5 + characterValue!! }!!
    }

    fun part2(input: List<String>): Long {
        val scores = mutableListOf<Long>()
        for (line in input) {
            val charArray = line.toCharArray()
            findIncompleteCharacters(charArray)
                    .map { getCharactersToFill(it) }
                    .map { countScore(it) }
                    .ifPresent { scores.add(it) }
        }
        scores.sort()
        return scores[scores.size / 2]
    }


    val testInput = readInput("day10/Day10_test")
    check(part1(testInput) == 26397)
    check(part2(testInput) == 288957L)

    val input = readInput("day10/Day10")
    println(part1(input))
    println(part2(input))
}

