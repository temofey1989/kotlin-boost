package io.justdevit.kotlin.boost.extension

/**
 * Determines whether the integer is even.
 *
 * @return `true` if the integer is even, `false` otherwise.
 */
fun Int.isEven(): Boolean = this % 2 == 0

/**
 * Executes the specified [action] only if the current integer is even.
 *
 * @param action The action to be executed if the integer is even.
 */
inline fun Int.ifEven(action: (Int) -> Unit) {
    if (isEven()) action(this)
}

/**
 * Determines whether the integer is odd.
 *
 * @return `true` if the integer is odd, `false` otherwise.
 */
fun Int.isOdd(): Boolean = !isEven()

/**
 * Executes the specified [action] only if the current integer is odd.
 *
 * @param action The action to be executed if the integer is odd.
 */
inline fun Int.ifOdd(action: (Int) -> Unit) {
    if (isOdd()) action(this)
}
