package day11

import readInput
import java.util.*


fun main() {
    fun addNeighbour(
        row: Int,
        column: Int,
        neighbours: MutableList<Point>
    ) {
        if (row in 0..9 && column in 0..9) {
            neighbours.add(Point(row, column))
        }
    }

    fun getNeighbours(point: Point): List<Point> {
        val neighbours = mutableListOf<Point>()

        val topLeftColumn = point.col - 1
        val topLeftRow = point.row - 1

        val topColumn = point.col
        val topRow = point.row - 1

        val topRightColumn = point.col + 1
        val topRightRow = point.row - 1

        val rightColumn = point.col + 1
        val rightRow = point.row

        val leftColumn = point.col - 1
        val leftRow = point.row

        val bottomLeftColumn = point.col - 1
        val bottomLeftRow = point.row + 1

        val bottomColumn = point.col
        val bottomRow = point.row + 1

        val bottomRightColumn = point.col + 1
        val bottomRightRow = point.row + 1

        addNeighbour(topLeftRow, topLeftColumn, neighbours)
        addNeighbour(topRow, topColumn, neighbours)
        addNeighbour(topRightRow, topRightColumn, neighbours)
        addNeighbour(leftRow, leftColumn, neighbours)
        addNeighbour(rightRow, rightColumn, neighbours)
        addNeighbour(bottomLeftRow, bottomLeftColumn, neighbours)
        addNeighbour(bottomRow, bottomColumn, neighbours)
        addNeighbour(bottomRightRow, bottomRightColumn, neighbours)
        return neighbours
    }

    fun part1(input: List<String>): Int {
        val stepsToSimulate = 100
        val matrix: MutableList<MutableList<Int>> =
            input.map { it.toCharArray().map { character -> character.digitToInt() }.toMutableList() }.toMutableList()

        var flashCounter = 0
        for (i in 1..stepsToSimulate) {
            val zerosStack = Stack<Point>()
            for (row in 0 until 10) {
                for (col in 0 until 10) {
                    val newPointValue = matrix[row][col] + 1
                    if (newPointValue == 10) {
                        matrix[row][col] = 0
                        zerosStack.add(Point(row, col))
                        flashCounter++
                    } else {
                        matrix[row][col] = newPointValue
                    }
                }
            }
            while (!zerosStack.isEmpty()) {
                val zeroPoint = zerosStack.pop()
                getNeighbours(zeroPoint)
                    .forEach {
                        val currentNeighbourValue = matrix[it.row][it.col]
                        if (currentNeighbourValue != 0) {
                            val newNeighbourValue = currentNeighbourValue + 1
                            if (newNeighbourValue == 10) {
                                matrix[it.row][it.col] = 0
                                zerosStack.add(Point(it.row, it.col))
                                flashCounter++
                            } else {
                                matrix[it.row][it.col] = newNeighbourValue
                            }
                        }
                    }
            }
        }

        return flashCounter
    }

    fun part2(input: List<String>): Int {
        val matrix: MutableList<MutableList<Int>> =
            input.map { it.toCharArray().map { character -> character.digitToInt() }.toMutableList() }.toMutableList()

        var step = 1
        var allFlashes = false
        while (!allFlashes) {
            val zerosStack = Stack<Point>()
            for (row in 0 until 10) {
                for (col in 0 until 10) {
                    val newPointValue = matrix[row][col] + 1
                    if (newPointValue == 10) {
                        matrix[row][col] = 0
                        zerosStack.add(Point(row, col))
                    } else {
                        matrix[row][col] = newPointValue
                    }
                }
            }
            while (!zerosStack.isEmpty()) {
                val zeroPoint = zerosStack.pop()
                getNeighbours(zeroPoint)
                    .forEach {
                        val currentNeighbourValue = matrix[it.row][it.col]
                        if (currentNeighbourValue != 0) {
                            val newNeighbourValue = currentNeighbourValue + 1
                            if (newNeighbourValue == 10) {
                                matrix[it.row][it.col] = 0
                                zerosStack.add(Point(it.row, it.col))
                            } else {
                                matrix[it.row][it.col] = newNeighbourValue
                            }
                        }
                    }
            }

            val anyNotZero = matrix.flatten().any { it != 0 }
            if (anyNotZero) {
                step += 1
            } else {
                allFlashes = true
            }
        }

        return step
    }

    val testInput = readInput("day11/Day11_test")
    check(part1(testInput) == 1656)
    check(part2(testInput) == 195)

    val input = readInput("day11/Day11")
    println(part1(input))
    println(part2(input))
}

data class Point(val row: Int, val col: Int)