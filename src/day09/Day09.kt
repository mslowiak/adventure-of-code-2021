package day09

import readInput
import java.util.*


fun main() {
    fun getNeighbours(point: Point, allPoints: List<List<Int>>): List<Point> {
        val neighbours = mutableListOf<Point>()
        val height = allPoints.size
        val width = allPoints[0].size
        if (point.col + 1 < width) {
            neighbours.add(Point(point.row, point.col + 1, allPoints[point.row][point.col + 1]))
        }
        if (point.col - 1 >= 0) {
            neighbours.add(Point(point.row, point.col - 1, allPoints[point.row][point.col - 1]))
        }
        if (point.row + 1 < height) {
            neighbours.add(Point(point.row + 1, point.col, allPoints[point.row + 1][point.col]))
        }
        if (point.row - 1 >= 0) {
            neighbours.add(Point(point.row - 1, point.col, allPoints[point.row - 1][point.col]))
        }
        return neighbours
    }

    fun List<Point>.isLowPoint(comparedValue: Int): Boolean = this.all { point -> point.value > comparedValue }

    fun findLowPoints(matrix: List<List<Int>>): MutableList<Point> {
        val lowPoints = mutableListOf<Point>()
        matrix.forEachIndexed { rowIndex, rowNumbers ->
            rowNumbers.forEachIndexed { columnIndex, number ->
                val actualPoint = Point(rowIndex, columnIndex, number)
                val neighbours = getNeighbours(Point(rowIndex, columnIndex, actualPoint.value), matrix)
                if (neighbours.isLowPoint(actualPoint.value)) {
                    lowPoints.add(actualPoint)
                }
            }
        }
        return lowPoints
    }

    fun part1(input: List<String>): Int {
        val matrix = input.map { it.toCharArray().map { character -> character.digitToInt() } }
        val lowPoints = findLowPoints(matrix)
        return lowPoints.sumOf { it.value + 1 }
    }

    fun findBasinSize(point: Point, matrix: List<List<Int>>): Int {
        val currentBasin = HashSet<Point>()
        val stack = Stack<Point>()
        val visited = HashSet<Point>()
        currentBasin.add(point)
        stack.add(point)

        while (!stack.isEmpty()) {
            val number = stack.pop()
            if (number.value >= point.value && number.value != 9) {
                currentBasin.add(number)
                val neighbours = getNeighbours(number, matrix)
                neighbours.forEach {
                    if (!visited.contains(it)) {
                        stack.add(it)
                    }
                }
            }
            visited.add(number)
        }
        return currentBasin.size
    }

    fun part2(input: List<String>): Int {
        val matrix = input.map { it.toCharArray().map { character -> character.digitToInt() } }
        val lowPoints = findLowPoints(matrix)
        return lowPoints.map { findBasinSize(it, matrix) }
                .sortedDescending()
                .take(3)
                .reduce { accumulator, basinSize -> accumulator * basinSize }
    }


    val testInput = readInput("day09/Day09_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 1134)

    val input = readInput("day09/Day09")
    println(part1(input))
    println(part2(input))
}

data class Point(val row: Int, val col: Int, val value: Int)