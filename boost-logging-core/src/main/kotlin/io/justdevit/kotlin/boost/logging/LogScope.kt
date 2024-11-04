package io.justdevit.kotlin.boost.logging

import io.justdevit.kotlin.boost.extension.runIf
import java.util.ServiceLoader
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock
import kotlin.jvm.optionals.getOrNull

private var logContextProcessor: LogContextProcessor? = null
private val LOG_CONTEXT_PROCESSOR_LOCK: Lock = ReentrantLock()

/**
 * Executes the provided [action] within a logging scope, applying the provided key-value pairs as attributes to the logging context.
 *
 * @param pairs The key-value pairs to be added as attributes to the logging context.
 * @param action The action to be executed within the logging scope.
 * @return The result of executing the [action].
 */
fun <T> logScope(vararg pairs: Pair<String, String?>, action: () -> T): T {
    val context = LogContext(attributes = pairs.toMap().toMutableMap())
    val processor = findLogContextProcessor()
    val snapshot = processor.applyContext(context)
    return try {
        action()
    } finally {
        processor.restore(snapshot)
    }
}

/**
 * Finds and returns a [LogContextProcessor].
 * If LOG_CONTEXT_PROCESSOR is already initialized, returns it.
 * Otherwise, checks for a concrete implementation of [LogContextProcessor] using [ServiceLoader].
 * If found, initializes LOG_CONTEXT_PROCESSOR with the first implementation and returns it.
 * Throws a [RuntimeException] if no concrete implementation is found.
 *
 * @return The found LogContextProcessor.
 * @throws [RuntimeException] if no concrete implementation of LogContextProcessor is found.
 */
internal fun findLogContextProcessor(): LogContextProcessor {
    LOG_CONTEXT_PROCESSOR_LOCK.runIf({ logContextProcessor == null }) {
        val loader = ServiceLoader.load(LogContextProcessor::class.java)
        val factory = loader.findFirst().getOrNull()
        factory.also {
            logContextProcessor = it
        }
    }
    return logContextProcessor ?: throw RuntimeException("No ${LogContextProcessor::class.java.name} found. Add a dependency for concrete implementation.")
}
