package day12

import readInput
import java.util.*
import kotlin.collections.HashMap


fun main() {

    fun printPaths(
        start: Cave,
        end: Cave,
        isVisited: MutableMap<Cave, Boolean>,
        pathList: MutableList<Cave>,
        edges: HashMap<Cave, MutableList<Cave>>
    ): Int {
        if (start.name == end.name) {
//            println(pathList)
            return 1
        }

        var found = 0
        isVisited[start] = true
        val neighbours = edges[start]
        neighbours!!.forEach {
            val edgeVisited = isVisited[it] ?: false
            if ((it.smallCave && !edgeVisited) || !it.smallCave) {
                pathList.add(it)
                found += printPaths(it, end, isVisited, pathList, edges)
                pathList.remove(it)
            }
        }
        isVisited[start] = false
        return found
    }

    fun getAllPaths(graph: CaveGraph): Int {
        val isVisited = HashMap<Cave, Boolean>()
        val pathsList = mutableListOf<Cave>()

        pathsList.add(Cave("start"))

        return printPaths(Cave("start"), Cave("end"), isVisited, pathsList, graph.edgesLinks)
    }


    fun part1(input: List<String>): Int {
        val edges = input.map {
            val vertices = it.split("-")
            Pair(Cave(vertices[0]), Cave(vertices[1]))
        }
        val graph = CaveGraph(edges)
        return getAllPaths(graph)
    }

    fun part2(input: List<String>): Int {
        return 1
    }

    val testInput = readInput("day12/Day12_test")
    check(part1(testInput) == 226)
//    check(part2(testInput) == 3509)

    val input = readInput("day12/Day12")
    println(part1(input))
//    println(part2(input))
}

class CaveGraph {
    val edgesLinks: HashMap<Cave, MutableList<Cave>>

    constructor(edges: List<Pair<Cave, Cave>>) {
        val links: HashMap<Cave, MutableList<Cave>> = HashMap()
        edges.forEach {
            if (it.first.start) {
                val connectedEdges = links.getOrDefault(it.first, mutableListOf())
                connectedEdges.add(it.second)
                links[it.first] = connectedEdges
            } else if (it.second.end) {
                val connectedEdges = links.getOrDefault(it.first, mutableListOf())
                connectedEdges.add(it.second)
                links[it.first] = connectedEdges
            } else {
                val connectedEdgesA = links.getOrDefault(it.first, mutableListOf())
                connectedEdgesA.add(it.second)
                links[it.first] = connectedEdgesA
                val connectedEdgesB = links.getOrDefault(it.second, mutableListOf())
                connectedEdgesB.add(it.first)
                it.second.name
                links[it.second] = connectedEdgesB
            }
        }
        this.edgesLinks = links
    }
}

class Cave(val name: String) {
    val smallCave: Boolean = name.lowercase() == name
    val start: Boolean = name == "start"
    val end: Boolean = name == "end"

    override fun toString() = name

    override fun equals(other: Any?): Boolean {
        return this.name == (other as Cave).name
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}