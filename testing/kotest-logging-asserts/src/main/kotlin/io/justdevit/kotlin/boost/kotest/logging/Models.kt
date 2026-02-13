package io.justdevit.kotlin.boost.kotest.logging

/**
 * Represents a single log record captured during test execution.
 *
 * @property index The sequential index of the log record, indicating its position in the log output.
 * @property content The textual content of the log record.
 */
data class LogRecord(val index: Int, val content: String)

/**
 * Represents the severity levels for log messages.
 */
enum class LogLevel {

    /**
     * Indicates a wildcard severity level that matches all log levels.
     */
    ANY,

    /**
     * Indicates a `TRACE` level.
     */
    TRACE,

    /**
     * Indicates a `DEBUG` level.
     */
    DEBUG,

    /**
     * Indicates an `INFO` level.
     */
    INFO,

    /**
     * Indicates a `WARN` level.
     */
    WARN,

    /**
     * Indicates an `ERROR` level.
     */
    ERROR,
}
