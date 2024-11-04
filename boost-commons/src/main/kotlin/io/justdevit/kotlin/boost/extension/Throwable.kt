package io.justdevit.kotlin.boost.extension

/**
 * Wraps the current throwable with a new message.
 *
 * @param message The message to prepend to the current throwable.
 * @return A new [RuntimeException] instance that wraps the current throwable with the specified message.
 */
fun Throwable.wrapWithMessage(message: String) = RuntimeException(message, this)
