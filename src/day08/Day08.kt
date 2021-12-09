package day08

import readInput


fun main() {

    fun toSignalsAndOutputs(input: List<String>): List<Pair<List<String>, List<String>>> {
        return input.map { line ->
            line.split(" | ").let { (signals, output) ->
                signals.split(" ") to output.split(" ")
            }
        }
    }

    fun part1(input: List<String>): Int {
        val signalsWithOutputs = toSignalsAndOutputs(input)
        return signalsWithOutputs
                .map { it.second }
                .flatten()
                .count { it.length == 2 || it.length == 3 || it.length == 4 || it.length == 7 }
    }

    fun getNumberRepresentation(numbersAsLetters: List<String>, number: String): String {
        numbersAsLetters.mapIndexed { index, value ->
            if (value.length == number.length && value.toSet().containsAll(number.toSet())) {
                return index.toString()
            }
        }
        return ""
    }

    fun getNumberFromOutput(pair: Pair<List<String>, List<String>>, numbersAsLetters: List<String>): Int {
        var textNumber = ""
        pair.second.forEach {
            val partOfNumberRepresentation = getNumberRepresentation(numbersAsLetters, it)
            textNumber += partOfNumberRepresentation
        }
        return textNumber.toInt()
    }

    fun part2(input: List<String>): Int {
        val signalsAndOutputs = toSignalsAndOutputs(input)
        var sumOfNumbers = 0
        
        signalsAndOutputs.forEach { pair ->
            val signals = pair.first

            // 1,4,7,8 has one representation
            val one = requireNotNull(signals.find { it.length == 2 }).asIterable()
            val four = requireNotNull(signals.find { it.length == 4 }).asIterable()
            val seven = requireNotNull(signals.find { it.length == 3 }).asIterable()
            val eight = requireNotNull(signals.find { it.length == 7 }).asIterable()

            // 3, 5, 2 - has exactly 5 parts
            val three = requireNotNull(signals.find { it.length == 5 && one.intersect(it.toSet()).size == 2 }).asIterable()
            val five = requireNotNull(signals.find { it.length == 5 && four.subtract(one).intersect(it.toSet()).size == 2 }).asIterable()
            val two = requireNotNull(signals.find { it.length == 5 && it != three.joinToString("") && it != five.joinToString("") }).asIterable()

            // 6, 9, 0 - has exactly 6 parts
            val six = requireNotNull(signals.find { it.length == 6 && one.intersect(it.toSet()).size == 1 }).asIterable()
            val nine = requireNotNull(signals.find { it.length == 6 && it.toSet().minus(three).size == 1 }).asIterable()
            val zero = requireNotNull(signals.find { it.length == 6 && it != nine.joinToString("") && it != six.joinToString("")}).asIterable()

            val numbersAsLetters = listOf(
                    zero.joinToString(""),
                    one.joinToString(""),
                    two.joinToString(""),
                    three.joinToString(""),
                    four.joinToString(""),
                    five.joinToString(""),
                    six.joinToString(""),
                    seven.joinToString(""),
                    eight.joinToString(""),
                    nine.joinToString(""),
            )

            sumOfNumbers += getNumberFromOutput(pair, numbersAsLetters)
        }
        return sumOfNumbers
    }

    val testInput = readInput("day08/Day08_test")
    check(part1(testInput) == 26)
    check(part2(testInput) == 61229)

    val input = readInput("day08/Day08")
    println(part1(input))
    println(part2(input))

}