package challenge

import readInput
import verifyAndMeasureDuration

fun main() {
    val input = readInput(1)

    verifyAndMeasureDuration("Part One", 67658) { sumMostCaloriesCarried(input, 1) }
    verifyAndMeasureDuration("Part Two", 200158) { sumMostCaloriesCarried(input, 3) }
}

private fun totalCaloriesPerElf(input: String) = input.split("\n\n").map {
    it.split("\n").fold(0) { acc, calories ->
        acc + calories.toInt()
    }
}

private fun sumMostCaloriesCarried(input: String, numberOfElves: Int) =
    totalCaloriesPerElf(input).sortedDescending().take(numberOfElves).sum()