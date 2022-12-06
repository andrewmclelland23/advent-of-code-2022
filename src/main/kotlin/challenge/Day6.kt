package challenge

import readInput
import verifyAndMeasureDuration


fun main() {
    val input = readInput(6)

    verifyAndMeasureDuration("Part One", 1896) { input.firstUniqueChars(4) }
    verifyAndMeasureDuration("Part Two", 3452) { input.firstUniqueChars(14) }
}

tailrec fun String.firstUniqueChars(size: Int, startIndex: Int = 0): Int =
    if(this.subSequence(startIndex, startIndex + size).toSet().size == size)
        startIndex + size
    else
        firstUniqueChars(size, startIndex + 1)