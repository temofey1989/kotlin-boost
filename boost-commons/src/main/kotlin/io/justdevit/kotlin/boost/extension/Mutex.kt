package io.justdevit.kotlin.boost.extension

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * Runs the specified [action] if the given [predicate] evaluates to `true`, while ensuring exclusive access to the
 * critical section by acquiring the mutex before running the action and releasing the mutex afterwards.
 *
 * @param predicate A function that evaluates the condition for executing the [action].
 * @param action The operation to perform if the [predicate] evaluates to `true`.
 */
suspend inline fun Mutex.runIf(predicate: () -> Boolean, action: () -> Unit) {
    if (predicate()) {
        withLock {
            if (predicate()) {
                action()
            }
        }
    }
}
