package challenge

import readInput
import verifyAndMeasureDuration


fun main() {
    val input = readInput(5)

    verifyAndMeasureDuration("Part One", "LBLVVTVLP") { input.readInput().moveCrates(9000) }
    verifyAndMeasureDuration("Part One", "TPFFBDRJD") { input.readInput().moveCrates(9001) }

}

private fun String.readInput(): Pair<List<MutableList<Char?>>, List<List<String>>> =
    split("\n\n").let { (stackString, instructionString) ->
        val stackCount = stackString.last().digitToInt()
        val crateRows = stackString.replace("    ", "-").replace("""\[|\]| """.toRegex(), "").trim()
            .split("\n").dropLast(1)

        val stacks = (0 until stackCount).map { i ->
            crateRows.map { it.getOrNull(i) }.filter { it != "-"[0] && it != null }.toMutableList()
        }
        val instructions = instructionString.split("\n").map {
            it.split("move | from | to ".toRegex()).drop(1)
        }
        Pair(stacks, instructions)
    }

fun Pair<List<MutableList<Char?>>, List<List<String>>>.moveCrates(cratePickerModel: Int) =
    second.forEach { instruction ->
        repeat(instruction[0].toInt()) { i ->
            val removalIndex = if (cratePickerModel == 9000) 0 else instruction[0].toInt() - 1 - i
            first[instruction[1].toInt() - 1].removeAt(removalIndex).let { takenCrate ->
                first[instruction[2].toInt() - 1].add(0, takenCrate)
            }
        }
    }.let { first.map { it[0] }.joinToString("") }
