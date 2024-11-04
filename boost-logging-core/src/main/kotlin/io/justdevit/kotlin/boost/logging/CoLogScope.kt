package io.justdevit.kotlin.boost.logging

import io.justdevit.kotlin.boost.extension.runIf
import kotlinx.coroutines.slf4j.MDCContext
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.withContext
import java.util.ServiceLoader
import kotlin.jvm.optionals.getOrNull

private var logContextProcessor: LogContextProcessor? = null
private val LOG_CONTEXT_PROCESSOR_MUTEX = Mutex()

/**
 * Executes the specified suspending action within a coroutine scope and sets up a logging context using the provided pairs of attributes.
 *
 * @param pairs The pairs of attribute names and values to set in the logging context. The values can be null.
 * @param action The suspending action to execute.
 * @return The result of the suspending action.
 */
suspend fun <T> coLogScope(vararg pairs: Pair<String, String?>, action: suspend () -> T): T {
    val context = LogContext(attributes = pairs.toMap().toMutableMap())
    val processor = coFindLogContextProcessor()
    val snapshot = processor.applyContext(context)
    return try {
        withContext(MDCContext()) {
            action()
        }
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
internal suspend fun coFindLogContextProcessor(): LogContextProcessor {
    LOG_CONTEXT_PROCESSOR_MUTEX.runIf({ logContextProcessor == null }) {
        val loader = ServiceLoader.load(LogContextProcessor::class.java)
        val factory = loader.findFirst().getOrNull()
        factory.also {
            logContextProcessor = it
        }
    }
    return logContextProcessor ?: throw RuntimeException("No ${LogContextProcessor::class.java.name} found. Add a dependency for concrete implementation.")
}
