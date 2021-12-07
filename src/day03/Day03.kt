package day03

import readInput


fun main() {

    fun part1(input: List<String>): Int {
        val lineLength = input[0].length
        
        var gammaRate = ""
        var epsilonRate = ""
        
        for (i in 0 until lineLength) {
            val zeroes = input.count { it[i] == '0' }
            val ones = input.size - zeroes
            if (zeroes > ones) {
                gammaRate += '1'
                epsilonRate += '0'
            } else {
                gammaRate += '0'
                epsilonRate += '1'
            }
        }

        return gammaRate.toInt(2) * epsilonRate.toInt(2)
    }

    fun countRating(input: List<String>, lineLength: Int, signForMoreZeros: Char, signForMoreOnes: Char): String {
        var selectedNumbers = input
        for (i in 0 until lineLength) {
            if (selectedNumbers.size == 1) {
                return selectedNumbers[0]
            }
            val zeroes = selectedNumbers.count { it[i] == '0' }
            val ones = selectedNumbers.size - zeroes
            selectedNumbers = if (zeroes > ones) {
                selectedNumbers.filter { it[i] == signForMoreZeros }
            } else {
                selectedNumbers.filter { it[i] == signForMoreOnes }
            }
        }
        return selectedNumbers[0]
    }

    fun searchForOxygenGeneratorRating(lineLength: Int, input: List<String>): String {
        return countRating(input, lineLength, '1', '0')
    }

    fun searchForScrubberRating(lineLength: Int, input: List<String>): String {
        return countRating(input, lineLength, '0', '1')
    }

    fun part2(input: List<String>): Int {
        val lineLength = input[0].length

        val numberForOxygenGeneratorRating = searchForOxygenGeneratorRating(lineLength, input)
        val numberForScrubberRating = searchForScrubberRating(lineLength, input)

        return numberForOxygenGeneratorRating.toInt(2) * numberForScrubberRating.toInt(2)
    }
    
    val testInput = readInput("day03/Day03_test")
    println(part1(testInput))
    check(part1(testInput) == 198)
    println(part2(testInput))
    check(part2(testInput) == 230)

    val testInput2 = readInput("day03/Day03")
    println(part1(testInput2))
    println(part2(testInput2))
}