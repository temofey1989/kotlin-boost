package io.justdevit.kotlin.boost.logging

import java.util.ServiceLoader
import kotlin.jvm.optionals.getOrNull

private val loggerFactory: LoggerFactory by lazy {
    val loader = ServiceLoader.load(LoggerFactory::class.java)
    loader.findFirst().getOrNull()
        ?: throw RuntimeException("No ${LoggerFactory::class.java.name} found. Add a dependency for concrete implementation.")
}

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
    /**
     * Creates a `TRACE`-level log record.
     *
     * @param builder A lambda function used to construct the log message.
     */
    fun trace(builder: LogRecordBuilderFunction)

    /**
     * Creates a `TRACE`-level log record.
     *
     * @param cause The throwable associated with the log record.
     * @param builder A lambda function used to construct the log message.
     */
    fun trace(cause: Throwable, builder: LogRecordBuilderFunction)

    /**
     * Creates a `DEBUG`-level log record.
     *
     * @param builder A lambda function used to construct the log message.
     */
    fun debug(builder: LogRecordBuilderFunction)

    /**
     * Creates a `DEBUG`-level log record.
     *
     * @param cause The throwable associated with the log record.
     * @param builder A lambda function used to construct the log message.
     */
    fun debug(cause: Throwable, builder: LogRecordBuilderFunction)

    /**
     * Creates a `INFO`-level log record.
     *
     * @param builder A lambda function used to construct the log message.
     */
    fun info(builder: LogRecordBuilderFunction)

    /**
     * Creates a `INFO`-level log record.
     *
     * @param cause The throwable associated with the log record.
     * @param builder A lambda function used to construct the log message.
     */
    fun info(cause: Throwable, builder: LogRecordBuilderFunction)

    /**
     * Creates a `WARN`-level log record.
     *
     * @param builder A lambda function used to construct the log message.
     */
    fun warn(builder: LogRecordBuilderFunction)

    /**
     * Creates a `WARN`-level log record.
     *
     * @param cause The throwable associated with the log record.
     * @param builder A lambda function used to construct the log message.
     */
    fun warn(cause: Throwable, builder: LogRecordBuilderFunction)

    /**
     * Creates a `ERROR`-level log record.
     *
     * @param builder A lambda function used to construct the log message.
     */
    fun error(builder: LogRecordBuilderFunction)

    /**
     * Creates a `ERROR`-level log record.
     *
     * @param cause The throwable associated with the log record.
     * @param builder A lambda function used to construct the log message.
     */
    fun error(cause: Throwable, builder: LogRecordBuilderFunction)
}

/**
 * Retrieves a logger using the provided function.
 *
 * @param function The function to be used for find/creates logger.
 * @return The logger instance.
 */
fun logger(function: (() -> Unit)): Logger = loggerFactory.getLogger(function)

/**
 * Retrieves an instance of the Logger interface for the specified name.
 *
 * @param name The name of the logger.
 * @return An instance of the Logger interface for the specified name.
 */
fun logger(name: String): Logger = loggerFactory.getLogger(name)

/**
 * Retrieves a Logger instance for the specified class.
 *
 * @param clazz the class for which the Logger is obtained
 * @return the Logger instance
 */
fun logger(clazz: Class<*>): Logger = loggerFactory.getLogger(clazz)
