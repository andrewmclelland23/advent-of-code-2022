package challenge

import challenge.Day8.calculateScenicScores
import challenge.Day8.parse
import challenge.Day8.treesVisibleFromOutside
import readInput
import verifyAndMeasureDuration


fun main() {
    val input = readInput(8)

    verifyAndMeasureDuration("Part One", 1533) { input.parse().treesVisibleFromOutside().size }
    verifyAndMeasureDuration("Part Two", 345744) { input.parse().calculateScenicScores() }
}


object Day8 {
    fun String.parse(): Pair<List<List<Int>>, List<List<Int>>> = split("\n").let { rows ->
        val gridSize = rows.first().length
        val splitRows = rows.map { it.split("").filter { it != "" }.map { it.toInt() } }
        val splitColumns = (0 until gridSize).map { i -> splitRows.mapNotNull { it.getOrNull(i) } }
        Pair(splitRows, splitColumns)
    }

    fun Pair<List<List<Int>>, List<List<Int>>>.treesVisibleFromOutside(): List<Pair<Int, Int>> {
        val visibleTrees: MutableList<Pair<Int, Int>> = mutableListOf()
        val gridWidth = first.size
        // add outside trees
        (first.indices).forEach { x ->
            (first.indices).forEach { y ->
                when {
                    x in listOf(0, gridWidth - 1) || y in listOf(0, gridWidth - 1) -> visibleTrees.add(Pair(x, y))
                    second[x][y] > first[y].subList(0, x).max() -> visibleTrees.add(Pair(x, y))
                    second[x][y] > first[y].subList(x + 1, gridWidth).max() -> visibleTrees.add(Pair(x, y))
                    second[x][y] > second[x].subList(0, y).max() -> visibleTrees.add(Pair(x, y))
                    second[x][y] > second[x].subList(y + 1, gridWidth).max() -> visibleTrees.add(Pair(x, y))
                    else -> "nothing"
                }
            }
        }
        return visibleTrees
    }

    fun Pair<List<List<Int>>, List<List<Int>>>.calculateScenicScores(): Int {
        var maxScore = 0
        val gridWidth = first.size
        (first.indices).forEach { x ->
            (first.indices).forEach { y ->
                val score =
                    first[y].subList(0, x).let { trees ->
                        trees.indexOfLast { treeSize -> treeSize >= second[x][y] }.let { i ->
                            if (i == -1) trees.size else trees.size - i
                        }
                    } * first[y].subList(x + 1, gridWidth).let { trees ->
                        trees.indexOfFirst { treeSize ->
                            treeSize >= second[x][y]
                        }.let { i ->
                            if (i == -1) trees.size else i + 1
                        }
                    } * second[x].subList(0, y).let { trees ->
                        trees.indexOfLast { treeSize ->
                            treeSize >= second[x][y]
                        }.let { i ->
                            if (i == -1) trees.size else trees.size - i
                        }
                    } * second[x].subList(y + 1, gridWidth).let { trees ->
                        trees.indexOfFirst { treeSize ->
                            treeSize >= second[x][y]
                        }.let { i ->
                            if (i == -1) trees.size else i + 1
                        }
                    }
                if (score > maxScore) maxScore = score
            }
        }
        return maxScore
    }
}