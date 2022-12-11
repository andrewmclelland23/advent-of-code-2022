package challenge

import challenge.Day10.calculateXAfterCycle
import challenge.Day10.draw
import challenge.Day10.calculatePixelPositions
import challenge.Day10.parse
import challenge.Day10.sumSignalStrengthDuringCycles
import createGrid
import readInput
import verifyAndMeasureDuration

fun main() {
    val input = readInput(10)
    val expectedPartTwoDrawing = """
        
            #### #  # ###  #### ###    ##  ##  #    
            #    #  # #  #    # #  #    # #  # #    
            ###  #### #  #   #  #  #    # #    #    
            #    #  # ###   #   ###     # # ## #    
            #    #  # #    #    #    #  # #  # #    
            #### #  # #    #### #     ##   ### #### 
        """.trimIndent()

    verifyAndMeasureDuration("Part One", 12520) {
        input.parse().calculateXAfterCycle()
            .sumSignalStrengthDuringCycles(20, 60, 100, 140, 180, 220)
    }
    verifyAndMeasureDuration("Part Two", expectedPartTwoDrawing) {
        input.parse().calculateXAfterCycle().calculatePixelPositions().draw()
    }
}

object Day10 {

    fun List<List<Pair<Pair<Int, Int>, Boolean>>>.draw(): String {
        var drawing = ""
        forEach { crtRow ->
            crtRow.forEach { crtPos ->
                if (crtPos.first.first == 0) drawing += "\n"
                drawing += when (crtPos.second) {
                    true -> "#"
                    false -> " "
                }
            }
        }
        return drawing
    }

    fun List<Int>.sumSignalStrengthDuringCycles(vararg cycleNumbers: Int) = cycleNumbers.map { cycle ->
        cycle * get(cycle - 2)
    }.sum()

    fun String.parse(): List<Pair<Int, Int>> = this.split("\n").map {
        it.split(" ").let {
            when (it[0]) {
                "addx" -> Pair(it[1].toInt(), 2)
                else -> Pair(0, 1)
            }
        }
    }

    fun List<Int>.calculatePixelPositions(): List<List<Pair<Pair<Int, Int>, Boolean>>> =
        createGrid(6, 40).map { crtRow ->
            crtRow.map { crtPos ->
                val reg = getOrNull(((crtPos.first + 1) + ((crtPos.second) * 40)) - 2) ?: 1
                Pair(crtPos, (crtPos.first - 1 <= reg && reg <= crtPos.first + 1))
            }
        }

    fun List<Pair<Int, Int>>.calculateXAfterCycle(): List<Int> {
        var x = 1
        val xRegValues = mutableListOf<Int>()

        forEach { (addition, cycles) ->
            repeat(cycles) { i ->
                when {
                    i + 1 < cycles -> xRegValues.add(x)
                    else -> {
                        x += addition; xRegValues.add(x)
                    }
                }
            }
        }
        return xRegValues
    }
}