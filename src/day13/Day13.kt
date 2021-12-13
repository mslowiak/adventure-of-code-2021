package day13

import readInput


fun main() {

    fun toPoint(coords: String): Point {
        val coordsValues = coords.split(",")
        return Point(coordsValues[0].toInt(), coordsValues[1].toInt())
    }

    fun toFold(line: String): Fold {
        if (line.contains("x=")) {
            val value = line.split("x=")[1].toInt()
            return Fold("x", value)
        } else {
            val value = line.split("y=")[1].toInt()
            return Fold("y", value)
        }
    }

    fun foldPointsByX(value: Int, points: List<Point>) =
        points.map {
            if (it.x > value) {
                val diffToFold = it.x - value
                val newX = value - diffToFold
                Point(value - diffToFold, it.y)
            } else {
                it
            }
        }.toSet()

    fun foldPointsByY(value: Int, points: List<Point>) =
        points.map {
            if (it.y > value) {
                val diffToFold = it.y - value
                val newY = value - diffToFold
                Point(it.x, newY)
            } else {
                it
            }
        }.toSet()

    fun foldPointsBy(fold: Fold, points: List<Point>): Set<Point> {
        return if (fold.typeOfFolding == "x") {
            foldPointsByX(fold.value, points)
        } else {
            foldPointsByY(fold.value, points)
        }
    }

    fun part1(input: List<String>): Int {
        val points = input.filter { it.contains(",") }.map { toPoint(it) }
        val fold = input.filter { it.contains("fold") }.map { toFold(it) }.first()

        val foldedPoints = foldPointsBy(fold, points)

        return foldedPoints.size
    }

    fun part2(input: List<String>): String {
        val points = input.filter { it.contains(",") }.map { toPoint(it) }
        val folds = input.filter { it.contains("fold") }.map { toFold(it) }

        var foldedPoints = points.toSet()
        for (fold in folds) {
            foldedPoints = foldPointsBy(fold, foldedPoints.toList())
        }

        val maxX = foldedPoints.maxOf { it.x }
        val maxY = foldedPoints.maxOf { it.y }

        val paper = Array(maxY + 1) { StringBuilder(" ".repeat(maxX + 1)) }

        for (point in foldedPoints) {
            paper[point.y][point.x] = '#'
        }

        return paper.joinToString("\n")
    }

    val testInput = readInput("day13/Day13_test")
    check(part1(testInput) == 17)
    println(part2(testInput))

    val input = readInput("day13/Day13")
    println(part1(input))
    println(part2(input))
}

data class Point(val x: Int, val y: Int)
data class Fold(val typeOfFolding: String, val value: Int)