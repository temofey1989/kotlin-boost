package io.justdevit.kotlin.boost.extension

import java.time.Instant

/**
 * Checks if this [Instant] is between the given start and end [Instant]s (inclusive).
 *
 * @param from The start [Instant].
 * @param to The end [Instant].
 * @return `true` if this [Instant] is between the start and end [Instant]s (inclusive), `false` otherwise.
 */
fun Instant.isBetween(from: Instant, to: Instant) = !isBefore(from) && !isAfter(to)
