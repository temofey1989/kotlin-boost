package io.justdevit.kotlin.boost.extension

import java.time.Instant
import java.time.Instant.now
import java.time.ZoneOffset.UTC

/**
 * Checks if this [Instant] is between the given start and end [Instant]s (inclusive).
 *
 * @param from The start [Instant].
 * @param to The end [Instant].
 * @return `true` if this [Instant] is between the start and end [Instant]s (inclusive), `false` otherwise.
 */
fun Instant.isBetween(from: Instant, to: Instant) = !isBefore(from) && !isAfter(to)

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
