package io.justdevit.kotlin.boost.extension

/**
 * Executes the specified [block] if the receiver [Boolean] value is `true`.
 *
 * @param block The block of code to be executed.
 * @receiver The [Boolean] value to be checked.
 */
inline fun Boolean?.ifTrue(block: () -> Unit) {
    if (true == this) block()
}

/**
 * Returns the result of [block] if the receiver [Boolean] value is `true`, otherwise returns `null`.
 *
 * @param block The block of code to be executed if the receiver [Boolean] value is `true`.
 * @return The result of [block] if the receiver [Boolean] value is `true`, `null` otherwise.
 */
fun <T> Boolean?.ifTrueReturn(block: () -> T): T? = if (true == this) block() else null

/**
 * Executes the specified [block] if the receiver [Boolean] value is `false`.
 *
 * @param block The block of code to be executed.
 * @receiver The [Boolean] value to be checked.
 */
inline fun Boolean?.ifFalse(block: () -> Unit) {
    if (false == this) block()
}

/**
 * Returns the result of [block] if the receiver [Boolean] value is `false`, otherwise returns `null`.
 *
 * @param block The block of code to be executed if the receiver [Boolean] value is `false`.
 * @return The result of [block] if the receiver [Boolean] value is `false`, `null` otherwise.
 */
fun <T> Boolean?.ifFalseReturn(block: () -> T): T? = if (false == this) block() else null
