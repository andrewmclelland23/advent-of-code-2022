package challenge

import readInput
import verifyAndMeasureDuration


fun main() {
    val input = readInput(6)

    verifyAndMeasureDuration("Part One", 1896) { input.firstUniqueChars(4) }
    verifyAndMeasureDuration("Part Two", 3452) { input.firstUniqueChars(14) }
}

fun String.firstUniqueChars(size: Int): Int {
    var answer = -1
    var i = -1
    while(answer < 0) {
        i++
        if(this.drop(i).take(size).toSet().size == size) answer = i + size
    }
    return answer
}