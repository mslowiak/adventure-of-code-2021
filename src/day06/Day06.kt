package day06

import readInput


fun main() {

    fun toLanterfishByOccurrences(input: String): LongArray {
        val array = LongArray(9)
        input.split(",")
                .map { it.toInt() }
                .forEach { array[it] += 1L }
        return array
    }

    fun calculateLanterfish(input: String, numberOfDays: Int): Long {
        var lanterfishOccurrences = toLanterfishByOccurrences(input)

        for (day in 1..numberOfDays) {
            val newOccurrences = LongArray(9)
            for (index in 0..8) {
                val occurrencesForIndex = lanterfishOccurrences[index]
                if (index == 0) {
                    newOccurrences[6] = occurrencesForIndex
                    newOccurrences[8] = occurrencesForIndex
                } else {
                    newOccurrences[index - 1] += occurrencesForIndex
                }
            }
            lanterfishOccurrences = newOccurrences
        }

        return lanterfishOccurrences.sum()
    }

    fun part1(input: String) = calculateLanterfish(input, 80)
    fun part2(input: String) = calculateLanterfish(input, 256)

    val testInput = readInput("day06/Day06_test")
    check(part1(testInput[0]) == 5934L)
    check(part2(testInput[0]) == 26984457539L)

    val input = readInput("day06/Day06")
    println(part1(input[0]))
    println(part2(input[0]))
}