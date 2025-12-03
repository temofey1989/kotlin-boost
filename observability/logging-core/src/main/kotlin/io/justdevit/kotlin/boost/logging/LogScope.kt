package io.justdevit.kotlin.boost.logging

/**
 * Executes the provided action within a logging scope, applying the provided key-value pairs as attributes to the logging context.
 *
 * @param pairs The key-value pairs to be added as attributes to the logging context.
 * @param action The action to be executed within the logging scope.
 * @return The result of executing the action.
 */
fun <T> logScope(vararg pairs: Pair<String, String?>, action: () -> T): T {
    val context = LogContext(attributes = pairs.toMap().toMutableMap())
    val processor = LogContextProcessor.DEFAULT
    val snapshot = processor.applyContext(context)
    return try {
        action()
    } finally {
        processor.restore(snapshot)
    }
}
