package challenge

import readInput
import splitTransformAndFold
import verifyAndMeasureDuration


fun main() {
    val input = readInput(2)

    verifyAndMeasureDuration("Part One", 11449) { input.calculateIncorrectScores() }
    verifyAndMeasureDuration("Part Two", 13187) { input.calculateCorrectScores() }
}

private fun String.calculateIncorrectScores() =
    splitTransformAndFold(delimiter = "\n", initial = 0,
        transform = { Pair(RPC.valueFromName(it[0]), RPC.valueFromName(it[2])) },
        fold = { acc, matchUp -> acc + resultTableOne[matchUp]!! }
    )

private fun String.calculateCorrectScores() =
    splitTransformAndFold(delimiter = "\n", initial = 0,
        transform = { Pair(RPC.valueFromName(it[0]), RESULT.valueFromName(it[2])) },
        fold = { acc, input -> acc + resultTableTwo[input]!! }
    )

private val resultTableOne: Map<Pair<RPC, RPC>, Int> = mapOf(
    Pair(RPC.ROCK, RPC.ROCK) to 4,
    Pair(RPC.PAPER, RPC.PAPER) to 5,
    Pair(RPC.SCISSORS, RPC.SCISSORS) to 6,
    Pair(RPC.PAPER, RPC.ROCK) to 1,
    Pair(RPC.SCISSORS, RPC.PAPER) to 2,
    Pair(RPC.ROCK, RPC.SCISSORS) to 3,
    Pair(RPC.SCISSORS, RPC.ROCK) to 7,
    Pair(RPC.ROCK, RPC.PAPER) to 8,
    Pair(RPC.PAPER, RPC.SCISSORS) to 9
)

val resultTableTwo: Map<Pair<RPC, RESULT>, Int> = mapOf(
    Pair(RPC.ROCK, RESULT.LOSE) to 3,
    Pair(RPC.ROCK, RESULT.DRAW) to 4,
    Pair(RPC.ROCK, RESULT.WIN) to 8,
    Pair(RPC.SCISSORS, RESULT.LOSE) to 2,
    Pair(RPC.SCISSORS, RESULT.DRAW) to 6,
    Pair(RPC.SCISSORS, RESULT.WIN) to 7,
    Pair(RPC.PAPER, RESULT.LOSE) to 1,
    Pair(RPC.PAPER, RESULT.DRAW) to 5,
    Pair(RPC.PAPER, RESULT.WIN) to 9
)

enum class RPC(val names: String) {
    ROCK("AX"),
    PAPER("BY"),
    SCISSORS("CZ");

    companion object {
        fun valueFromName(name: Char) = values().first { rpc -> rpc.names.contains(name) }
    }
}

enum class RESULT(val code: String) {
    WIN("Z"),
    LOSE("X"),
    DRAW("Y");

    companion object {
        fun valueFromName(code: Char) = RESULT.values().first { result -> result.code[0] == code }
    }
}