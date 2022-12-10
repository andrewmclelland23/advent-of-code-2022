package challenge

import challenge.Day9.moveLongRope
import challenge.Day9.moveRope
import challenge.Day9.parseInstructions
import readInput
import verifyAndMeasureDuration
import kotlin.math.absoluteValue


fun main() {
    val input = readInput(9)

    verifyAndMeasureDuration("Part One", 6181) { input.parseInstructions().moveRope(Pair(0, 0), Pair(0, 0)).size }
    verifyAndMeasureDuration("Part Two", 2386) { input.parseInstructions().moveLongRope().size }
}
object Day9 {
    fun String.parseInstructions(): List<Pair<Direction, Int>> =
        split("\n").map { it.split(" ").let { (dir, num) -> Pair(Direction.valueOf(dir), num.toInt()) } }

    fun List<Pair<Direction, Int>>.moveRope(
        initialHeadPos: Pair<Int, Int>,
        initialTailPos: Pair<Int, Int>,
    ): Set<Pair<Int, Int>> {
        var headPos = initialHeadPos
        var tailPos = initialTailPos
        val tailPositions: MutableSet<Pair<Int, Int>> = mutableSetOf(Pair(0, 0))
        map { instructions ->
            repeat(instructions.second) { i ->
                instructions.first.moveFirstKnot(headPos, tailPos).let { (newHeadPos, newTailPos) ->
                    headPos = newHeadPos
                    tailPos = newTailPos
                    tailPositions.add(tailPos)
                }
            }
        }
        return tailPositions
    }

    fun List<Pair<Direction, Int>>.moveLongRope(): Set<Pair<Int, Int>> {
        var head = Pair(0, 0)
        var knot2 = Pair(0, 0)
        var knot3 = Pair(0, 0)
        var knot4 = Pair(0, 0)
        var knot5 = Pair(0, 0)
        var knot6 = Pair(0, 0)
        var knot7 = Pair(0, 0)
        var knot8 = Pair(0, 0)
        var knot9 = Pair(0, 0)
        var tail = Pair(0, 0)
        val tailPositions: MutableSet<Pair<Int, Int>> = mutableSetOf(Pair(0, 0))
        forEach { (direction, repetitions) ->
            repeat(repetitions) {
                direction.moveFirstKnot(head, knot2).let {
                    head = it.first; knot2 = it.second
                }
                calculateTailMove(knot2, knot3).let { knot3 = it }
                calculateTailMove(knot3, knot4).let { knot4 = it }
                calculateTailMove(knot4, knot5).let { knot5 = it }
                calculateTailMove(knot5, knot6).let { knot6 = it }
                calculateTailMove(knot6, knot7).let { knot7 = it }
                calculateTailMove(knot7, knot8).let { knot8 = it }
                calculateTailMove(knot8, knot9).let { knot9 = it }
                calculateTailMove(knot9, tail).let { tail = it }
                tailPositions.add(tail)
            }
        }
        return tailPositions
    }

    private fun Direction.moveFirstKnot(
        initialHeadPos: Pair<Int, Int>,
        initialTailPos: Pair<Int, Int>,
    ): Pair<Pair<Int, Int>, Pair<Int, Int>> =
        initialHeadPos.move(this).let { newHeadPos ->
            Pair(newHeadPos, calculateTailMove(newHeadPos, initialTailPos))
        }

    private fun touching(
        headPos: Pair<Int, Int>,
        tailPos: Pair<Int, Int>,
    ): Boolean =
        (headPos.first - tailPos.first).absoluteValue < 2 && (headPos.second - tailPos.second).absoluteValue < 2

    private fun calculateTailMove(
        headPos: Pair<Int, Int>,
        tailPos: Pair<Int, Int>,
    ): Pair<Int, Int> = when {
        touching(headPos, tailPos) -> tailPos
        (headPos.first == tailPos.first) && (headPos.second < tailPos.second) -> Pair(
            tailPos.first,
            tailPos.second - 1
        )

        (headPos.first == tailPos.first) && (headPos.second > tailPos.second) -> Pair(
            tailPos.first,
            tailPos.second + 1
        )

        (headPos.second == tailPos.second) && (headPos.first > tailPos.first) -> Pair(
            tailPos.first + 1,
            tailPos.second
        )

        (headPos.second == tailPos.second) && (headPos.first < tailPos.first) -> Pair(
            tailPos.first - 1,
            tailPos.second
        )
        (headPos.first > tailPos.first && headPos.second > tailPos.second) -> Pair(
            tailPos.first + 1,
            tailPos.second + 1
        )

        (headPos.first > tailPos.first) -> Pair(
            tailPos.first + 1,
            tailPos.second - 1
        )

        (headPos.first < tailPos.first && headPos.second > tailPos.second) -> Pair(
            tailPos.first - 1,
            tailPos.second + 1
        )

        (headPos.first < tailPos.first) -> Pair(
            tailPos.first - 1,
            tailPos.second - 1
        )

        else -> tailPos
    }

    private fun Pair<Int, Int>.move(direction: Direction) = when (direction) {
        Direction.R -> Pair(this.first + 1, this.second)
        Direction.L -> Pair(this.first - 1, this.second)
        Direction.U -> Pair(this.first, this.second + 1)
        Direction.D -> Pair(this.first, this.second - 1)
    }

    enum class Direction {
        U, D, L, R
    }
}