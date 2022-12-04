fun <T> String.splitAndFold(delimiter: String, initial: T, f: (T, String) -> T): T =
    split(delimiter).fold(initial, f)

fun <T, R> String.splitTransformAndFold(delimiter: String, initial: R, transform: (String) -> T, fold: (R, T) -> R): R =
    split(delimiter).map(transform).fold(initial, fold)
tailrec fun findCommonChars(inputs: List<String>): List<Char> =
    when {
        inputs.isEmpty() -> emptyList()
        inputs.size == 1 -> inputs[0].toCharArray().toList()
        else -> {
            val matches = inputs[0].toCharArray().filter { char -> inputs[1].contains(char) }
            findCommonChars(mutableListOf(matches.joinToString("")).plus(inputs.drop(2)))
        }
    }