package io.justdevit.kotlin.boost.logging.slf4j

import io.justdevit.kotlin.boost.logging.LogContext
import io.justdevit.kotlin.boost.logging.LogContextProcessor
import io.justdevit.kotlin.boost.logging.LogContextSnapshot
import org.slf4j.MDC

/**
 * The Slf4jLogContextProcessor class implements the LogContextProcessor interface to manage the logging context using SLF4J's MDC (Mapped Diagnostic Context).
 * MDC is a thread-local map with key-value pairs that can be used to associate contextual information with log messages.
 * This class applies and restores the logging context using SLF4J's MDC implementation.
 */
class Slf4jLogContextProcessor : LogContextProcessor {
    override fun applyContext(context: LogContext): LogContextSnapshot {
        if (context.attributes.isEmpty() && !context.clear) {
            return LogContextSnapshot()
        }

        val previousMdc = if (context.restorePrevious) MDC.getCopyOfContextMap() ?: emptyMap() else emptyMap()
        if (context.clear) {
            MDC.clear()
        }
        context.attributes.forEach {
            if (it.value != null) {
                MDC.put(it.key, it.value)
            } else {
                MDC.remove(it.key)
            }
        }
        return LogContextSnapshot(previousMdc)
    }

    override fun restore(snapshot: LogContextSnapshot) {
        if (snapshot.attributes.isEmpty()) {
            return
        }
        MDC.clear()
        snapshot
            .attributes
            .asSequence()
            .filter { it.value != null }
            .forEach {
                MDC.put(it.key, it.value)
            }
    }
}
