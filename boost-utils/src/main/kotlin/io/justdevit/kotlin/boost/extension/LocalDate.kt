package io.justdevit.kotlin.boost.extension

import io.justdevit.kotlin.boost.EUROPEAN_DATE_FORMATTER
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.ZoneOffset.UTC

/**
 * Converts a [LocalDate] object to an [OffsetDateTime] object in the UTC time zone.
 *
 * @return The converted [OffsetDateTime] object in UTC.
 */
fun LocalDate.toUtcDateTime(): OffsetDateTime = atStartOfDay().atOffset(UTC)

/**
 * Converts the [LocalDate] object to a string representation in European date format.
 *
 * @return The string representation of the [LocalDate] in European date format.
 */
fun LocalDate.toEuropeanString(): String = format(EUROPEAN_DATE_FORMATTER)

/**
 * Checks if this [LocalDate] is between the [from] date (inclusive) and the [to] date (inclusive).
 *
 * @param from The start date of the range to check.
 * @param to The end date of the range to check.
 * @return `true` if this date is between the range (inclusive), `false` otherwise.
 */
fun LocalDate.isBetween(from: LocalDate, to: LocalDate) = !isBefore(from) && !isAfter(to)
