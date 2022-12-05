package challenge

import readInput
import verifyAndMeasureDuration


fun main() {
    val input = readInput(4)

    verifyAndMeasureDuration("Part One", 602) {
        var count = 0
        input.split("\n")
            .map {
                it.split(",")
                    .let {
                        Pair(
                            it[0].split("-").let { it[0].toInt()..it[1].toInt() },
                            it[1].split("-").let { it[0].toInt()..it[1].toInt() }
                        )
                    }.let { (range1, range2) ->
                        if (range1.toList().containsAll(range2.toList()) || range2.toList()
                                .containsAll(range1.toList())
                        ) count += 1
                    }
            }
        count
    }

    verifyAndMeasureDuration("Part Two", 891) {
        var count = 0
        input.split("\n")
            .map {
                it.split(",")
                    .let {
                        Pair(
                            it[0].split("-").let { Pair(it[0].toInt(), it[1].toInt()) },
                            it[1].split("-").let { Pair(it[0].toInt(), it[1].toInt()) }
                        )
                    }.let { (range1, range2) ->
                        when {
                            range1.first <= range2.first && range1.second >= range2.first -> count += 1
                            range2.first <= range1.first && range2.second >= range1.first -> count += 1
                            else -> "do nothing"
                        }
                    }
            }
        count
    }
}
