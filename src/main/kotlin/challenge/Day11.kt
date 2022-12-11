package challenge

import challenge.Day11.parseToMonkeys
import challenge.Day11.performRounds
import readInput
import verifyAndMeasureDuration
import kotlin.math.floor

fun main() {
    val input = readInput(11)
    verifyAndMeasureDuration("Part One", 99840) {
        input.parseToMonkeys().performRounds(20, 3).sortedDescending().let { it[0] * it[1] }
    }
    verifyAndMeasureDuration("Part Two", 20683044837) {
        input.parseToMonkeys().performRounds(10000).sortedDescending().let { it[0] * it[1] }
    }
}

object Day11 {
    fun String.parseToMonkeys(): List<Monkey> =
        split("\n\n").map { monkeyText ->
            monkeyText.split("\n").let { monkeyLines ->
                Monkey(
                    items = monkeyLines[1].substringAfter("Starting items: ").split(", ")
                        .map { it.toLong() }.toMutableList(),
                    operation = monkeyLines[2].parseToOperation(),
                    divisor = monkeyLines[3].substringAfter("Test: divisible by ").toInt(),
                    testTrueThrow = monkeyLines[4].last().digitToInt(),
                    testFalseThrow = monkeyLines[5].last().digitToInt()
                )
            }
        }

    private fun String.parseToOperation(): (Long) -> Long =
        substringAfter("Operation: new = old ").split(" ").let { (operation, value) ->
            val maybeLongValue = value.toLongOrNull()
            when (operation) {
                "*" -> fun(old: Long) = old * (maybeLongValue ?: old)
                else -> fun(old: Long) = old + (maybeLongValue ?: old)
            }
        }

    private fun List<Monkey>.performRound(worryDivisor: Int? = null): List<Long> {
        val lowestCommonMultiplier = fold(1.toLong()) { acc, monkey -> monkey.divisor * acc }.toLong()
        val itemCounts = mutableListOf<Long>()
        forEachIndexed { i, monkey ->
            itemCounts.add(i, monkey.items.size.toLong())

            monkey.items.forEach { item ->
                val newItemValue =
                    if (worryDivisor != null)
                        floor(monkey.operation(item) / worryDivisor.toDouble()).toLong()
                    else
                        monkey.operation(item) % lowestCommonMultiplier
                when (newItemValue % monkey.divisor == 0L) {
                    true -> get(monkey.testTrueThrow).items.add(newItemValue)
                    false -> get(monkey.testFalseThrow).items.add(newItemValue)
                }
            }
            monkey.items.clear()
        }
        return itemCounts
    }

    fun List<Monkey>.performRounds(roundCount: Int, worryDivisor: Int? = null): List<Long> {
        val itemCounts = (indices).map { 0L }.toMutableList()
        repeat(roundCount) { i ->
            performRound(worryDivisor).forEachIndexed { itemIndex, itemCount ->
                itemCounts[itemIndex] = itemCounts[itemIndex] + itemCount
            }
        }
        return itemCounts
    }
}

class Monkey(
    val items: MutableList<Long>,
    val operation: (Long) -> Long,
    val divisor: Int,
    val testTrueThrow: Int,
    val testFalseThrow: Int,
)