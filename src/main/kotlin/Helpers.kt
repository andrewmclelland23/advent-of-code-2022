fun <T> String.splitAndFold(delimiter: String, initial: T, f: (T, String) -> T): T =
    split(delimiter).fold(initial, f)

fun <T, R> String.splitTransformAndFold(delimiter: String, initial: R, transform: (String) -> T, fold: (R, T) -> R): R =
    split(delimiter).map(transform).fold(initial, fold)