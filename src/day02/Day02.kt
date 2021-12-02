package day02

import readInput

fun main() {

    fun toCommand(data: String): Command {
        val splitData = data.split(" ")
        val commandName = CommandType.valueOf(splitData[0].uppercase())
        val commandValue = splitData[1].toInt()
        return Command(commandName, commandValue)
    }

    fun calculateCommandPart1(command: Command, result: ResultPart1) {
        when (command.name) {
            CommandType.FORWARD -> {
                result.horizontalPosition += command.value
            }
            CommandType.UP -> {
                result.depth -= command.value
            }
            CommandType.DOWN -> {
                result.depth += command.value
            }
        }
    }

    fun calculateCommandPart2(command: Command, result: ResultPart2) {
        when (command.name) {
            CommandType.FORWARD -> {
                result.horizontalPosition += command.value
                if (result.aim != 0) {
                    result.depth += command.value * result.aim
                }
            }
            CommandType.UP -> {
                result.aim -= command.value
            }
            CommandType.DOWN -> {
                result.aim += command.value
            }
        }
    }

    fun part1(input: List<String>): Int {
        val result = ResultPart1(0, 0)
        input.map { toCommand(it) }
                .forEach { command -> calculateCommandPart1(command, result) }

        return result.depth * result.horizontalPosition
    }

    fun part2(input: List<String>): Int {
        val result = ResultPart2(0, 0, 0)
        input.map { toCommand(it) }
                .forEach { command -> calculateCommandPart2(command, result) }

        return result.depth * result.horizontalPosition
    }

    val testInput = readInput("day02/Day02_test")
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val testInput2 = readInput("day02/Day02")
    println(part1(testInput2))
    println(part2(testInput2))

}

enum class CommandType {
    FORWARD,
    UP,
    DOWN
}

data class ResultPart1(var horizontalPosition: Int, var depth: Int)
data class ResultPart2(var horizontalPosition: Int, var depth: Int, var aim: Int)
data class Command(var name: CommandType, var value: Int)