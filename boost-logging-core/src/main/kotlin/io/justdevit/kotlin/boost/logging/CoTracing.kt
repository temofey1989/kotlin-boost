package io.justdevit.kotlin.boost.logging

import com.github.ksuid.Ksuid.newKsuid

/**
 * Executes the provided suspend [action] within a tracing scope by generating new trace and span IDs, and adding them as attributes to the logging context.
 *
 * @param T The return type of the action
 * @param action Action to be executed.
 *
 * @return The result of executing the [action].
 */
suspend fun <T> withCoTracing(action: suspend () -> T): T =
    coLogScope(
        TRACE_ID_KEY to newKsuid().toString(),
        SPAN_ID_KEY to newKsuid().toString(),
    ) {
        action()
    }

/**
 * Executes a suspend action within a logging context that contains a Trace ID.
 *
 * @param forceNew Whether to force the creation of a new Trace ID.
 * @param action The action to be executed within the logging context.
 *
 * @return The result of the executed action.
 */
suspend fun <T> withCoTraceId(forceNew: Boolean = false, action: suspend () -> T): T = withCoLogAttribute(TRACE_ID_KEY, forceNew, action)

/**
 * Executes a suspend action within a logging context that contains a Span ID.
 *
 * @param forceNew Whether to force the creation of a new Span ID.
 * @param action The action to be executed within the logging context.
 *
 * @return The result of the executed action.
 */
suspend fun <T> withCoSpanId(forceNew: Boolean = false, action: suspend () -> T): T = withCoLogAttribute(SPAN_ID_KEY, forceNew, action)

private suspend fun <T> withCoLogAttribute(
    name: String,
    forceNew: Boolean = false,
    action: suspend () -> T,
): T {
    val processor = findLogContextProcessor()
    val attributes = processor.currentAttributes()
    return if (attributes[name] == null || forceNew) {
        coLogScope(name to newKsuid().toString()) {
            action()
        }
    } else {
        action()
    }
}
