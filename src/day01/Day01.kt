package day01

import readInput

const val SLIDING_WINDOW_LENGTH = 3

fun main() {

    fun mapToInts(input: List<String>) = input.map { it.toInt() }

    fun part1(input: List<Int>) = input.zipWithNext().count { (previousNumber, nextNumber) -> previousNumber < nextNumber }

    fun part2(input: List<Int>) = part1(input.windowed(SLIDING_WINDOW_LENGTH).map { it.sum() })

    // test if implementation meets criteria from the description, like:
    val testInput = mapToInts(readInput("day01/Day01_test"))
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = mapToInts(readInput("day01/Day01"))
    println(part1(input))
    println(part2(input))
}
