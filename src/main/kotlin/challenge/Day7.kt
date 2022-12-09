package challenge

import challenge.Type.*
import readInput
import verifyAndMeasureDuration


fun main() {
    val input = readInput(7)

    verifyAndMeasureDuration("Part One", 1886043) { input.parse().filter { it.value <= 100000 }.values.sum() }
    verifyAndMeasureDuration("Part Two", 3842121) {
        input.parse().let { directories ->
            val requiredSpace = 30000000 - (70000000 - directories[listOf("root")]!!)
            directories.values.sorted().first { it >= requiredSpace }
        }
    }
}

private fun String.parse(): MutableMap<List<String>, Int> {
    val currentDir= mutableListOf("root")
    val directorySizes = mutableMapOf<List<String>, Int>()
    split("\n").drop(1).forEach { inputLine ->
        when (Type.fromString(inputLine)) {
            CD_BACK -> currentDir.removeLast()
            CD -> currentDir.add(CD.getValue(inputLine))
            FILE -> currentDir.indices.map { i ->
                val dirToAddValue = currentDir.subList(0, i + 1).toList()
                directorySizes.remove(dirToAddValue).let { maybeSize ->
                    directorySizes.put(dirToAddValue, FILE.getSize(inputLine) + (maybeSize ?: 0))
                }
            }
            else -> false // do nothing
        }
    }
    return directorySizes
}

enum class Type(val matcher: Regex) {
    DIRECTORY("""^dir [a-z]""".toRegex()),
    LS("""^\$ ls""".toRegex()),
    CD_BACK("""^\$ cd \.\.""".toRegex()),
    CD("""^\$ cd """.toRegex()),
    FILE("""^\d+ """.toRegex());

    fun getValue(input: String) = input.replace(matcher, "")
    fun getSize(input: String) = require(this == FILE).let { input.split(" ")[0].toInt() }

    companion object {
        fun fromString(input: String) = values().first { input.contains(it.matcher) }
    }
}

