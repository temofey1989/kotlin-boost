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
