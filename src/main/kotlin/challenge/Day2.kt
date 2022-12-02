package challenge

import readInput
import verifyAndMeasureDuration


fun main() {
    val input = readInput(2)

    verifyAndMeasureDuration("Part One", 11449) { input.parseToRpc<RPC_INCORRECT_RULES>().calculateScores() }
    verifyAndMeasureDuration("Part Two", 13187) { input.parseToRpc<RPC_CORRECT_RULES>().calculateScores() }
}

private inline fun <reified T : Enum<T>> String.parseToRpc(): List<Pair<T, T>> =
    split("\n").map { Pair(enumValueOf(it[0].toString()), enumValueOf(it[2].toString())) }

@JvmName("calculateScoresRPC_INCORRECT_RULESRPC_INCORRECT_RULES")
private fun List<Pair<RPC_INCORRECT_RULES, RPC_INCORRECT_RULES>>.calculateScores() =
    fold(0) { acc, (opponent, me) ->
        acc + when {
            opponent.score == me.score -> 3 + me.score
            opponent.score == 1 && me.score == 3 -> 3
            opponent.score == 2 && me.score == 1 -> 1
            opponent.score == 3 && me.score == 2 -> 2
            else -> 6 + me.score
        }
    }

private fun List<Pair<RPC_CORRECT_RULES, RPC_CORRECT_RULES>>.calculateScores() =
    fold(0) { acc, (opponent, me) ->
        acc + when (me.score) {
            3 -> opponent.score + 3
            0 -> when (opponent.score) {
                1 -> 3
                2 -> 1
                else -> 2
            }

            else -> when (opponent.score) {
                1 -> 6 + 2
                2 -> 6 + 3
                else -> 6 + 1
            }
        }
    }


enum class RPC_INCORRECT_RULES(val score: Int) {
    A(1),
    B(2),
    C(3),
    X(1),
    Y(2),
    Z(3);
}

enum class RPC_CORRECT_RULES(val score: Int) {
    A(1),
    B(2),
    C(3),
    X(0),
    Y(3),
    Z(6);
}