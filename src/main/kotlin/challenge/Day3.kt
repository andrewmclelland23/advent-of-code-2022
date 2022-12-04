package challenge

import findCommonChars
import readInput
import splitTransformAndFold
import verifyAndMeasureDuration


fun main() {
    val input = readInput(3)

    verifyAndMeasureDuration("Part One", 7889) { input.sumIncorrectItems()}
    verifyAndMeasureDuration("Part Two", 2825) { input.findAndSumGroupBadges(3) }
}

private fun String.sumIncorrectItems(): Int =
    splitTransformAndFold(delimiter = "\n", initial = 0,
        transform = { contents -> contents.chunked(contents.length / 2) },
        fold = { acc, pockets -> acc + itemValueMap[findCommonChars(pockets).first()]!! }
    )

private fun String.findAndSumGroupBadges(groupSize: Int): Int =
    split("\n").chunked(groupSize).sumOf { elfGroup -> itemValueMap[findCommonChars(elfGroup).first()]!! }

private val itemValueMap = mapOf(
    "a"[0] to 1,
    "b"[0] to 2,
    "c"[0] to 3,
    "d"[0] to 4,
    "e"[0] to 5,
    "f"[0] to 6,
    "g"[0] to 7,
    "h"[0] to 8,
    "i"[0] to 9,
    "j"[0] to 10,
    "k"[0] to 11,
    "l"[0] to 12,
    "m"[0] to 13,
    "n"[0] to 14,
    "o"[0] to 15,
    "p"[0] to 16,
    "q"[0] to 17,
    "r"[0] to 18,
    "s"[0] to 19,
    "t"[0] to 20,
    "u"[0] to 21,
    "v"[0] to 22,
    "w"[0] to 23,
    "x"[0] to 24,
    "y"[0] to 25,
    "z"[0] to 26,
    "A"[0] to 27,
    "B"[0] to 28,
    "C"[0] to 29,
    "D"[0] to 30,
    "E"[0] to 31,
    "F"[0] to 32,
    "G"[0] to 33,
    "H"[0] to 34,
    "I"[0] to 35,
    "J"[0] to 36,
    "K"[0] to 37,
    "L"[0] to 38,
    "M"[0] to 39,
    "N"[0] to 40,
    "O"[0] to 41,
    "P"[0] to 42,
    "Q"[0] to 43,
    "R"[0] to 44,
    "S"[0] to 45,
    "T"[0] to 46,
    "U"[0] to 47,
    "V"[0] to 48,
    "W"[0] to 49,
    "X"[0] to 50,
    "Y"[0] to 51,
    "Z"[0] to 52
)