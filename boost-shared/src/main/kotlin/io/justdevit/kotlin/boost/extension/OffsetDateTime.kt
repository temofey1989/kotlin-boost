package io.justdevit.kotlin.boost.extension

import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

/**
 * Converts the current representation of the Offset Date and Time to a representation in the local time zone.
 *
 * @return The [OffsetDateTime] object representing the date and time in the local time zone.
 */
fun OffsetDateTime.toLocal(): OffsetDateTime = toZonedDateTime().toLocalZone().toOffsetDateTime()

/**
 * Converts an [OffsetDateTime] to a string representation in ISO 8601 format.
 *
 * @return The string representation of the [OffsetDateTime] in ISO 8601 format.
 */
fun OffsetDateTime.toIsoString(): String = format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
