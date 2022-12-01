fun main() {
    val input = readInput(1)

    verifyAndTime("Part One", 67658) { sumMostCaloriesCarried(input, 1) }
    verifyAndTime("Part Two", 200158) { sumMostCaloriesCarried(input, 3) }
}

private fun totalCaloriesPerElf(input: String) = input.split("\n\n").map {
    it.split("\n").fold(0) { acc, calories ->
        acc + calories.toInt()
    }
}

private fun sumMostCaloriesCarried(input: String, numberOfElves: Int) =
    totalCaloriesPerElf(input).sortedDescending().take(numberOfElves).sum()