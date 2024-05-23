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

/**
 * Checks if the given [OffsetDateTime] falls between the [from] and [to] dates (inclusive).
 *
 * @param from The starting date to compare against.
 * @param to The ending date to compare against.
 * @return `true` if this [OffsetDateTime] falls between [from] and [to] dates (inclusive), `false` otherwise.
 */
fun OffsetDateTime.isBetween(from: OffsetDateTime, to: OffsetDateTime) = !isBefore(from) && !isAfter(to)
