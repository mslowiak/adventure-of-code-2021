package day07

import readInput
import kotlin.math.abs


fun main() {

    fun toSortedNumbers(input: String) = input.split(",")
            .map { it.toInt() }
            .sorted()

    fun calculateMedian(numbers: List<Int>): Int {
        val middle = numbers.size / 2
        return if (numbers.size % 2 == 0) {
            (numbers[middle] + numbers[middle - 1]) / 2
        } else {
            numbers[middle]
        }
    }

    fun part1(input: String): Int {
        val numbers = toSortedNumbers(input)
        val median = calculateMedian(numbers)
        return numbers.sumOf { abs(it - median) }
    }

    fun part2(input: String): Int {
        val numbers = toSortedNumbers(input)
        val min = numbers.first()
        val max = numbers.last()
        return (min..max).minOf { point ->
            numbers.sumOf { ((1 + abs(point - it)) * abs(point - it)) / 2 }
        }
    }

    val testInput = readInput("day07/Day07_test")
    check(part1(testInput[0]) == 37)
    check(part2(testInput[0]) == 168)

    val input = readInput("day07/Day07")
    println(part1(input[0]))
    println(part2(input[0]))

}