package io.justdevit.kotlin.boost.kotest.logging.assertion

import io.justdevit.kotlin.boost.kotest.logging.LogLevel
import io.justdevit.kotlin.boost.kotest.logging.LogRecord

/**
 * Represents an assertion for evaluating the content of a log record.
 */
sealed interface LogRecordFilter : LogAssertComponent {

    /**
     * Indicates whether the assertion is negated.
     */
    val negated: Boolean

    /**
     * Determines if the given log record satisfies the conditions defined by the filter.
     *
     * @param record The log record to be evaluated. Contains information such as the log's index and content.
     * @return `true` if the log record meets the filter criteria; otherwise, `false`.
     */
    fun accepts(record: LogRecord): Boolean

    /**
     * A log record filter that matches log entries containing a specific text value.
     *
     * @property negated Indicates if the filter should invert its logic.
     *                   If `true`, the filter accepts log records that do not contain the specified text.
     * @property value The text value used to match against the log record content.
     */
    data class ContainsText(val value: String, override val negated: Boolean = false) : LogRecordFilter {
        override fun accepts(record: LogRecord): Boolean {
            val value = record.content.contains(value)
            return if (negated) !value else value
        }

        override fun asString() = "text = ${if (negated) "!" else ""}\"$value\""
    }

    /**
     * A log record filter that matches log entries based on whether their content satisfies a specified regular expression pattern.
     *
     * @property negated Indicates if the filter's logic is inverted.
     *                   If `true`, the filter will accept log records that do not match the regular expression.
     * @property regex The regular expression used to evaluate the content of the log record.
     */
    data class ContainsRegex(val regex: Regex, override val negated: Boolean = false) : LogRecordFilter {
        override fun accepts(record: LogRecord): Boolean {
            val value = regex.containsMatchIn(record.content)
            return if (negated) !value else value
        }

        override fun asString() = "regex = ${if (negated) "!" else ""}\"${regex.pattern}\""
    }

    /**
     * A filter for log records based on a specific log level.
     *
     * @property level The log level to filter for. If `null`, the filter matches any log level.
     */
    data class LogLevelFilter(val level: LogLevel = LogLevel.ANY) : LogRecordFilter {
        override val negated = false

        override fun accepts(record: LogRecord) =
            level == LogLevel.ANY ||
                with(record.content) {
                    startsWith("$level ") ||
                        startsWith("$level:") ||
                        contains(" $level ") ||
                        contains("\"$level\"") ||
                        contains("[$level]") ||
                        contains("($level)")
                }

        override fun asString() = "level = $level"
    }
}
