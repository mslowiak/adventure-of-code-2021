package day04

import readInput


fun main() {

    fun playBingoPart1(bingoNumbers: List<Int>, boards: MutableList<Board>): Int {
        for (number in bingoNumbers) {
            boards.forEach {
                it.addNumber(number)
                if (it.isSolved()) {
                    return it.score()
                }
            }
        }
        throw IllegalStateException("no result for bingo")
    }

    fun playBingoPart2(bingoNumbers: List<Int>, boards: MutableList<Board>): Int {
        val bingoBoard = mutableSetOf<Board>()
        for (number in bingoNumbers) {
            boards.forEach {
                it.addNumber(number)
                if (it.isSolved()) {
                    bingoBoard.add(it)
                }
            }
        }
        return bingoBoard.last().score()
    }

    fun loadBoards(input: List<String>): MutableList<Board> {
        val boards = mutableListOf<Board>()

        var temp = mutableListOf<String>()
        for (i in 2 until input.size) {
            if (input[i] != "") {
                temp.add(input[i])
            } else {
                boards.add(Board(temp))
                temp = mutableListOf()
            }
        }
        boards.add(Board(temp))
        return boards
    }

    fun part1(input: List<String>): Int {
        val bingoNumbers = input[0].split(",").map { it.toInt() }
        val boards = loadBoards(input)

        return playBingoPart1(bingoNumbers, boards)
    }

    fun part2(input: List<String>): Int {
        val bingoNumbers = input[0].split(",").map { it.toInt() }
        val boards = loadBoards(input)

        return playBingoPart2(bingoNumbers, boards)
    }

    val testInput = readInput("day04/Day04_test")
    check(part1(testInput) == 4512)
    check(part2(testInput) == 1924)

    val input = readInput("day04/Day04")
    println(part1(input))
    println(part2(input))
}

class Board(lines: List<String>) {
    private var table = mutableListOf<List<Int>>()
    private var numbers = mutableListOf<Int>()

    init {
        lines.forEach { line ->
            table.add(line.trim().replace("  ", " ").split(" ").map { it.toInt() })
        }
    }

    fun addNumber(number: Int) {
        if (!isSolved()) {
            numbers.add(number)
        }
    }

    fun isSolved(): Boolean {
        return checkRows() || checkColumns()
    }

    fun score(): Int {
        val lastNumber = numbers.last()
        val unmarkedNumbersSum = table.flatten().filter { it !in numbers }.sum()
        return unmarkedNumbersSum * lastNumber
    }

    private fun checkColumns(): Boolean {
        for (index in table.first().indices) {
            val isBingo = table.map { row -> row[index] }.all { number -> number in numbers }
            if (isBingo) {
                return true
            }
        }
        return false
    }

    private fun checkRows(): Boolean {
        return table.any { row -> row.all { it in numbers } }
    }

}
