package io.justdevit.kotlin.boost.kotest.logging.assertion

import io.justdevit.kotlin.boost.kotest.logging.LogLevel
import io.justdevit.kotlin.boost.kotest.logging.LogLevel.ANY
import io.justdevit.kotlin.boost.kotest.logging.LogLevel.DEBUG
import io.justdevit.kotlin.boost.kotest.logging.LogLevel.ERROR
import io.justdevit.kotlin.boost.kotest.logging.LogLevel.INFO
import io.justdevit.kotlin.boost.kotest.logging.LogLevel.TRACE
import io.justdevit.kotlin.boost.kotest.logging.LogLevel.WARN
import io.justdevit.kotlin.boost.kotest.logging.LogRecord
import io.justdevit.kotlin.boost.kotest.logging.assertion.Appearance.Never
import io.justdevit.kotlin.boost.kotest.logging.assertion.Appearance.Times
import io.justdevit.kotlin.boost.kotest.logging.assertion.Appearance.Undefined
import io.justdevit.kotlin.boost.kotest.logging.assertion.LogRecordFilter.ContainsRegex
import io.justdevit.kotlin.boost.kotest.logging.assertion.LogRecordFilter.ContainsText
import io.justdevit.kotlin.boost.kotest.logging.assertion.LogRecordFilter.LogLevelFilter
import io.kotest.assertions.asClue

/**
 * Executor for log assertions.
 *
 * @property assertions A list of [LogRecordsAssertion] that defines the criteria for log validation.
 * @property ordered Indicates if the assertions should respect the order of log lines.
 *                   If `true`, assertions are validated in a sequential manner, and consumed log lines
 *                   are removed to maintain order. Defaults to `false`.
 */
data class LogAssertExecutor(val assertions: List<LogRecordsAssertion>, val ordered: Boolean = false) {

    /**
     * Executes the log assertions against a list of log records.
     *
     * @param records The list of log records to be validated against the defined assertions.
     */
    fun execute(records: List<LogRecord>) {
        val availableRecords = records.toMutableList()
        assertions.forEach { assertion ->
            "Log Assertion ${assertion.asString()} should succeed.".asClue {
                val acceptedLines = assertion.execute(availableRecords)
                if (ordered && acceptedLines.isNotEmpty()) {
                    val biggestIndex = acceptedLines.maxOf { it.index }
                    availableRecords.removeIf { biggestIndex >= it.index }
                }
            }
        }
    }
}

/**
 * A builder for creating and configuring logging assertions to validate log lines based
 * on their content, log level, and frequency of occurrence. Provides DSL-like syntax
 * to define various conditions for log messages and log levels.
 *
 * @property ordered Determines if the assertions should be validated in the same order in which they were defined.
 *                   If `true`, order will be enforced. Defaults to `false`.
 */
@LoggingAssertionsScope
class LogAssertExecutorBuilder internal constructor(private val ordered: Boolean = false) {

    private val assertions = mutableListOf<LogRecordsAssertion>()

    /**
     * Creates a negated log content assertion for the given string.
     *
     * @receiver The string value to be checked for absence in the log message.
     * @return A [ContainsText] assertion configured to check for the absence of the specified string.
     */
    operator fun String.not() = ContainsText(value = this, negated = true)

    /**
     * Creates a negated log content assertion for the given regular expression.
     *
     * @receiver The Regex object to be checked for absence in the log message.
     * @return A [ContainsRegex] assertion configured to check for the absence of the specified regular expression.
     */
    operator fun Regex.not() = ContainsRegex(regex = this, negated = true)

    /**
     * Negates the current log lines assertion within the logging assertion builder.
     */
    operator fun LogRecordsAssertion.not() {
        assertions -= this
        assertions += this.copy(
            appearance = when (appearance) {
                is Times -> appearance.copy(negated = true)
                is Never -> Undefined
                is Undefined -> Never
            },
        )
    }

    /**
     * Registers an assertion to check if the log messages contain the specified text.
     *
     * @param message The string value to be checked for its presence in the log messages.
     * @param exactly The exact number of times the message is expected to appear in the log.
     *                Use `-1` for an undefined count. Defaults to `-1`.
     */
    fun message(message: String, exactly: Int = -1) = message(ContainsText(message), exactly)

    /**
     * Registers an assertion to check if the log messages contain the specified regular expression.
     *
     * @param regex The regular expression to be checked for its presence in the log messages.
     * @param exactly The exact number of times the message is expected to appear in the log.
     *                Use `-1` for an undefined count. Defaults to `-1`.
     */
    fun message(regex: Regex, exactly: Int = -1) = message(ContainsRegex(regex), exactly)

    /**
     * Registers an assertion to check if the log messages contain the specified assertion.
     *
     * @param filter The filter to be checked for its presence in the log messages.
     * @param exactly The exact number of times the message is expected to appear in the log.
     *                Use `-1` for an undefined count. Defaults to `-1`.
     */
    fun message(filter: LogRecordFilter, exactly: Int = -1) = register(ANY, filter, exactly)

    /**
     * Registers an assertion to check if the log messages contain the specified text for TRACE level.
     *
     * @param message The string value to be checked for its presence in the log messages.
     * @param exactly The exact number of times the message is expected to appear in the log.
     *                Use `-1` for an undefined count. Defaults to `-1`.
     */
    fun trace(message: String, exactly: Int = -1) = trace(ContainsText(message), exactly)

    /**
     * Registers an assertion to check if the log messages contain the specified regular expression for TRACE level.
     *
     * @param regex The regular expression to be checked for its presence in the log messages.
     * @param exactly The exact number of times the message is expected to appear in the log.
     *                Use `-1` for an undefined count. Defaults to `-1`.
     */
    fun trace(regex: Regex, exactly: Int = -1) = trace(ContainsRegex(regex), exactly)

    /**
     * Registers an assertion to check if the log messages contain the specified assertion for TRACE level.
     *
     * @param filter The filter to be checked for its presence in the log messages.
     * @param exactly The exact number of times the message is expected to appear in the log.
     *                Use `-1` for an undefined count. Defaults to `-1`.
     */
    fun trace(filter: LogRecordFilter, exactly: Int = -1) = register(TRACE, filter, exactly)

    /**
     * Registers an assertion to check if the log messages contain the specified text for DEBUG level.
     *
     * @param message The string value to be checked for its presence in the log messages.
     * @param exactly The exact number of times the message is expected to appear in the log.
     *                Use `-1` for an undefined count. Defaults to `-1`.
     */
    fun debug(message: String, exactly: Int = -1) = debug(ContainsText(message), exactly)

    /**
     * Registers an assertion to check if the log messages contain the specified regular expression for DEBUG level.
     *
     * @param regex The regular expression to be checked for its presence in the log messages.
     * @param exactly The exact number of times the message is expected to appear in the log.
     *                Use `-1` for an undefined count. Defaults to `-1`.
     */
    fun debug(regex: Regex, exactly: Int = -1) = debug(ContainsRegex(regex), exactly)

    /**
     * Registers an assertion to check if the log messages contain the specified assertion for DEBUG level.
     *
     * @param filter The filter to be checked for its presence in the log messages.
     * @param exactly The exact number of times the message is expected to appear in the log.
     *                Use `-1` for an undefined count. Defaults to `-1`.
     */
    fun debug(filter: LogRecordFilter, exactly: Int = -1) = register(DEBUG, filter, exactly)

    /**
     * Registers an assertion to check if the log messages contain the specified text for INFO level.
     *
     * @param message The string value to be checked for its presence in the log messages.
     * @param exactly The exact number of times the message is expected to appear in the log.
     *                Use `-1` for an undefined count. Defaults to `-1`.
     */
    fun info(message: String, exactly: Int = -1) = info(ContainsText(message), exactly)

    /**
     * Registers an assertion to check if the log messages contain the specified regular expression for INFO level.
     *
     * @param regex The regular expression to be checked for its presence in the log messages.
     * @param exactly The exact number of times the message is expected to appear in the log.
     *                Use `-1` for an undefined count. Defaults to `-1`.
     */
    fun info(regex: Regex, exactly: Int = -1) = info(ContainsRegex(regex), exactly)

    /**
     * Registers an assertion to check if the log messages contain the specified assertion for INFO level.
     *
     * @param filter The filter to be checked for its presence in the log messages.
     * @param exactly The exact number of times the message is expected to appear in the log.
     *                Use `-1` for an undefined count. Defaults to `-1`.
     */
    fun info(filter: LogRecordFilter, exactly: Int = -1) = register(INFO, filter, exactly)

    /**
     * Registers an assertion to check if the log messages contain the specified text for WARN level.
     *
     * @param message The string value to be checked for its presence in the log messages.
     * @param exactly The exact number of times the message is expected to appear in the log.
     *                Use `-1` for an undefined count. Defaults to `-1`.
     */
    fun warn(message: String, exactly: Int = -1) = warn(ContainsText(message), exactly)

    /**
     * Registers an assertion to check if the log messages contain the specified regular expression for WARN level.
     *
     * @param regex The regular expression to be checked for its presence in the log messages.
     * @param exactly The exact number of times the message is expected to appear in the log.
     *                Use `-1` for an undefined count. Defaults to `-1`.
     */
    fun warn(regex: Regex, exactly: Int = -1) = warn(ContainsRegex(regex), exactly)

    /**
     * Registers an assertion to check if the log messages contain the specified assertion for WARN level.
     *
     * @param filter The filter to be checked for its presence in the log messages.
     * @param exactly The exact number of times the message is expected to appear in the log.
     *                Use `-1` for an undefined count. Defaults to `-1`.
     */
    fun warn(filter: LogRecordFilter, exactly: Int = -1) = register(WARN, filter, exactly)

    /**
     * Registers an assertion to check if the log messages contain the specified text for ERROR level.
     *
     * @param message The string value to be checked for its presence in the log messages.
     * @param exactly The exact number of times the message is expected to appear in the log.
     *                Use `-1` for an undefined count. Defaults to `-1`.
     */
    fun error(message: String, exactly: Int = -1) = error(ContainsText(message), exactly)

    /**
     * Registers an assertion to check if the log messages contain the specified regular expression for ERROR level.
     *
     * @param regex The regular expression to be checked for its presence in the log messages.
     * @param exactly The exact number of times the message is expected to appear in the log.
     *                Use `-1` for an undefined count. Defaults to `-1`.
     */
    fun error(regex: Regex, exactly: Int = -1) = error(ContainsRegex(regex), exactly)

    /**
     * Registers an assertion to check if the log messages contain the specified assertion for ERROR level.
     *
     * @param filter The filter to be checked for its presence in the log messages.
     * @param exactly The exact number of times the message is expected to appear in the log.
     *                Use `-1` for an undefined count. Defaults to `-1`.
     */
    fun error(filter: LogRecordFilter, exactly: Int = -1) = register(ERROR, filter, exactly)

    /**
     * Constructs and returns a new instance of [LogAssertExecutor] using the provided
     * assertions and order configuration from the current state.
     *
     * @return A [LogAssertExecutor] instance initialized with the configured assertions and the ordering flag.
     */
    internal fun build() = LogAssertExecutor(assertions.toList(), ordered)

    private fun register(
        level: LogLevel,
        filter: LogRecordFilter,
        exactly: Int = -1,
    ) = LogRecordsAssertion(
        appearance = when {
            exactly == 0 -> Never
            exactly >= 1 -> Times(exactly)
            else -> Undefined
        },
        LogLevelFilter(level),
        filter,
    ).also { assertions += it }
}
