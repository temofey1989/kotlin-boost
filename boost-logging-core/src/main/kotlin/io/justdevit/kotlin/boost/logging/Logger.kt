package io.justdevit.kotlin.boost.logging

import java.util.ServiceLoader
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock
import kotlin.jvm.optionals.getOrNull

private var loggerFactory: LoggerFactory? = null
private val LOGGER_FACTORY_LOCK: Lock = ReentrantLock()

/**
 * Defines a type alias for a function that builds a log record message.
 *
 * The function is defined within a `LogRecordBuilder` instance and returns a `String`.
 */
typealias LogRecordBuilderFunction = LogRecordBuilder.() -> String

/**
 * The Logger interface provides methods for logging different levels of messages.
 */
interface Logger {
    fun trace(builder: LogRecordBuilderFunction)

    fun trace(throwable: Throwable, builder: LogRecordBuilderFunction)

    fun debug(builder: LogRecordBuilderFunction)

    fun debug(throwable: Throwable, builder: LogRecordBuilderFunction)

    fun info(builder: LogRecordBuilderFunction)

    fun info(throwable: Throwable, builder: LogRecordBuilderFunction)

    fun warn(builder: LogRecordBuilderFunction)

    fun warn(throwable: Throwable, builder: LogRecordBuilderFunction)

    fun error(builder: LogRecordBuilderFunction)

    fun error(throwable: Throwable, builder: LogRecordBuilderFunction)
}

/**
 * Retrieves a logger using the provided function.
 *
 * @param function The function to be used for find/creates logger.
 * @return The logger instance.
 */
fun logger(function: (() -> Unit)): Logger = findLoggerFactory().getLogger(function)

/**
 * Retrieves an instance of the Logger interface for the specified name.
 *
 * @param name The name of the logger.
 * @return An instance of the Logger interface for the specified name.
 */
fun logger(name: String): Logger = findLoggerFactory().getLogger(name)

/**
 * Retrieves a Logger instance for the specified class.
 *
 * @param clazz the class for which the Logger is obtained
 * @return the Logger instance
 */
fun logger(clazz: Class<*>): Logger = findLoggerFactory().getLogger(clazz)

private fun findLoggerFactory(): LoggerFactory {
    if (loggerFactory != null) {
        return loggerFactory!!
    }
    LOGGER_FACTORY_LOCK.lock()
    if (loggerFactory != null) {
        return loggerFactory!!
    }
    val loader = ServiceLoader.load(LoggerFactory::class.java)
    val factory = loader.findFirst().getOrNull()
    return factory.also {
        loggerFactory = it
        LOGGER_FACTORY_LOCK.unlock()
    } ?: throw RuntimeException("No ${LoggerFactory::class.java.name} found. Add a dependency for concrete implementation.")
}
