package io.justdevit.kotlin.boost.logging

import com.github.ksuid.Ksuid.newKsuid

const val TRACE_ID_KEY = "traceId"
const val SPAN_ID_KEY = "spanId"

/**
 * Executes the provided [action] within a tracing scope by generating new trace and span IDs, and adding them as attributes to the logging context.
 *
 * @param T The return type of the action
 * @param action Action to be executed.
 *
 * @return The result of executing the [action].
 */
fun <T> withTracing(action: () -> T): T =
    logScope(
        TRACE_ID_KEY to newKsuid().toString(),
        SPAN_ID_KEY to newKsuid().toString(),
    ) {
        action()
    }

/**
 * Executes an action within a logging context that contains a Trace ID.
 *
 * @param forceNew Whether to force the creation of a new Trace ID.
 * @param action The action to be executed within the logging context.
 *
 * @return The result of the executed action.
 */
fun <T> withTraceId(forceNew: Boolean = false, action: () -> T): T = withLogAttribute(TRACE_ID_KEY, forceNew, action)

/**
 * Executes an action within a logging context that contains a Span ID.
 *
 * @param forceNew Whether to force the creation of a new Span ID.
 * @param action The action to be executed within the logging context.
 *
 * @return The result of the executed action.
 */
fun <T> withSpanId(forceNew: Boolean = false, action: () -> T): T = withLogAttribute(SPAN_ID_KEY, forceNew, action)

private fun <T> withLogAttribute(
    name: String,
    forceNew: Boolean = false,
    action: () -> T,
): T {
    val processor = LogContextProcessor.DEFAULT
    val attributes = processor.currentAttributes()
    return if (attributes[name] == null || forceNew) {
        logScope(name to newKsuid().toString()) {
            action()
        }
    } else {
        action()
    }
}
