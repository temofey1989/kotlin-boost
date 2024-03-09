package io.justdevit.kotlin.boost.logging

/**
 * Represents a log record that stores information about a log message.
 *
 * @property message The log message.
 * @property attributes Additional attributes associated with the log record.
 * @property throwable An optional throwable object associated with the log record.
 */
data class LogRecord(
    val message: String,
    val attributes: Map<String, Any?> = emptyMap(),
    val throwable: Throwable? = null,
)
