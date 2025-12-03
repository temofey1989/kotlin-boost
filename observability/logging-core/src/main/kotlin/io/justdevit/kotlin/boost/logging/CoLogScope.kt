package io.justdevit.kotlin.boost.logging

import kotlinx.coroutines.slf4j.MDCContext
import kotlinx.coroutines.withContext

/**
 * Executes the specified suspending action within a coroutine scope and sets up a logging context using the provided pairs of attributes.
 *
 * @param pairs The pairs of attribute names and values to set in the logging context. The values can be null.
 * @param action The suspending action to execute.
 * @return The result of the suspending action.
 */
suspend fun <T> coLogScope(vararg pairs: Pair<String, String?>, action: suspend () -> T): T {
    val context = LogContext(attributes = pairs.toMap().toMutableMap())
    val processor = LogContextProcessor.DEFAULT
    val snapshot = processor.applyContext(context)
    return try {
        withContext(MDCContext()) {
            action()
        }
    } finally {
        processor.restore(snapshot)
    }
}
