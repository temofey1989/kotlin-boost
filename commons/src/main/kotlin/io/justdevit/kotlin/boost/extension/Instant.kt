package io.justdevit.kotlin.boost.extension

import java.time.Instant
import java.time.Instant.now
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId.systemDefault
import java.time.ZoneOffset.UTC

/**
 * Represents the current year as an integer.
 *
 * This variable dynamically fetches the current year based on the system's UTC time.
 */
val currentYear: Int
    get() = now()
        .atOffset(UTC)
        .toLocalDate()
        .year

/**
 * Checks if this [Instant] is between the given start and end [Instant]s (inclusive).
 *
 * @param from The start [Instant].
 * @param to The end [Instant].
 * @return `true` if this [Instant] is between the start and end [Instant]s (inclusive), `false` otherwise.
 */
fun Instant.isBetween(from: Instant, to: Instant) = !isBefore(from) && !isAfter(to)

/**
 * Converts the [Instant] to a [LocalDate] using the system default time zone.
 *
 * @return the [LocalDate] representation of this [Instant].
 */
fun Instant.toLocalDate(): LocalDate = atZone(systemDefault()).toLocalDate()

/**
 * Converts this [Instant] to a [LocalDateTime] in the system default time zone.
 *
 * @return The [LocalDateTime] representation of this [Instant] in the system default time zone.
 */
fun Instant.toLocalDateTime(): LocalDateTime = atZone(systemDefault()).toLocalDateTime()
