package io.justdevit.kotlin.boost.kotest.logging.assertion

import io.justdevit.kotlin.boost.kotest.logging.LogRecord
import io.justdevit.kotlin.boost.kotest.logging.assertion.Appearance.Never
import io.justdevit.kotlin.boost.kotest.logging.assertion.Appearance.Times
import io.justdevit.kotlin.boost.kotest.logging.assertion.Appearance.Undefined
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.collections.shouldNotHaveSize

/**
 * Represents an assertion for validating the presence, content, and frequency of log records.
 *
 * @property appearance Specifies the expected frequency or condition of the log entry's appearance.
 *                      Can enforce rules such as appearing exactly a certain number of times or not appearing at all.
 *                      Defaults to `Undefined`.
 * @property filters A collection of [LogRecordFilter] instances that are used to evaluate individual log records.
 */
data class LogRecordsAssertion(val appearance: Appearance = Undefined, val filters: List<LogRecordFilter>) : LogAssertComponent {

    init {
        require(filters.isNotEmpty()) {
            "Log Record filters must not be empty."
        }
    }

    /**
     * Constructs an instance of `LogRecordsAssertion` with the specified appearance and filters.
     *
     * @param appearance The appearance criteria to be applied for the log records. By default, it is set to `Undefined`.
     *                   This determines the condition or frequency of log appearances during test assertions.
     * @param filters A collection of `LogRecordFilter` instances. These filters are used to evaluate and filter
     *                individual log records based on specific criteria such as matching content or log levels.
     */
    constructor(appearance: Appearance = Undefined, vararg filters: LogRecordFilter) : this(appearance, filters.toList())

    /**
     * Filters and validates a list of log records based on predefined assertions.
     * The method ensures that the resulting list of records complies with the defined appearance conditions.
     *
     * @param records The list of log records to be filtered and validated. Each log record contains
     *                details such as its index and content.
     * @return A list of log records that satisfy the assertions. The output is further checked
     *         against the defined appearance criteria, such as ensuring it is empty, has a specific
     *         size, or is not empty.
     */
    fun execute(records: List<LogRecord>): List<LogRecord> =
        records
            .filter { it.acceptFilters() }
            .also { it.assertAppearance() }

    override fun asString() = "(${if (appearance !is Undefined) " exactly = ${appearance.asString()}, " else ""}filters = [${filters.joinToString { it.asString() }}] )"

    private fun LogRecord.acceptFilters() = filters.all { it.accepts(this) }

    private fun List<LogRecord>.assertAppearance() =
        when (appearance) {
            is Never -> shouldBeEmpty()
            is Times -> if (appearance.negated) shouldNotHaveSize(appearance.value) else shouldHaveSize(appearance.value)
            is Undefined -> shouldNotBeEmpty()
        }
}
