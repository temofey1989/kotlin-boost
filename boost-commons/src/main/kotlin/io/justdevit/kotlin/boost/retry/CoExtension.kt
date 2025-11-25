package io.justdevit.kotlin.boost.retry

import kotlinx.coroutines.delay
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

/**
 * Executes the given suspend action with a retry mechanism. The action will be attempted
 * up to the specified number of maximum attempts, with a delay that can increase
 * on each retry.
 *
 * @param T The type of the result returned by the action.
 * @param maxAttempts The maximum number of retry attempts (default is `3`).
 * @param delay The initial delay between retry attempts (default is `100 milliseconds`).
 * @param multiplier The multiplier for increasing the delay after each attempt (default is `1`).
 * @param action The action to be executed with retry logic.
 *
 * @return The result of the action if it is successfully executed within the retry limit.
 *
 * @throws IllegalArgumentException if the maxAttempts is `0`.
 */
suspend fun <T> withCoRetry(
    maxAttempts: UInt = 3u,
    delay: Duration = 100.milliseconds,
    multiplier: ULong = 1u,
    action: suspend () -> T,
): T {
    var attempt = 0u
    var wait = delay.inWholeNanoseconds.toULong()
    while (attempt < maxAttempts) {
        try {
            return action()
        } catch (throwable: Throwable) {
            attempt++
            if (attempt >= maxAttempts) {
                throw throwable
            }
            wait += (delay.inWholeNanoseconds.toULong() * attempt * multiplier)
            if (wait > 0u) {
                delay(wait.toLong())
            }
        }
    }
    throw IllegalArgumentException("Max attempts should be greater than 0, but was $maxAttempts.")
}
