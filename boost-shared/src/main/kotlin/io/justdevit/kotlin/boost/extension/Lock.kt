package io.justdevit.kotlin.boost.extension

import java.util.concurrent.locks.Lock

/**
 * Runs the specified [action] if the given [predicate] evaluates to `true`, while ensuring exclusive access to the
 * critical section by acquiring the lock before running the action and releasing the lock afterwards.
 *
 * @param predicate A function that evaluates the condition for executing the [action].
 * @param action The operation to perform if the [predicate] evaluates to `true`.
 */
fun Lock.runIf(predicate: () -> Boolean, action: () -> Unit) {
    if (predicate()) {
        lock()
        try {
            if (predicate()) {
                action()
            }
        } finally {
            unlock()
        }
    }
}
