package io.justdevit.kotlin.boost.extension

import java.time.ZoneId.systemDefault
import java.time.ZonedDateTime

/**
 * Converts the given [ZonedDateTime] to the local system time zone.
 *
 * @return The [ZonedDateTime] converted to the local system time zone.
 */
fun ZonedDateTime.toLocalZone(): ZonedDateTime = withZoneSameInstant(systemDefault())

/**
 * Checks if the current [ZonedDateTime] is between the specified from and to [ZonedDateTime]s.
 *
 * @param from The starting [ZonedDateTime] (inclusive).
 * @param to The ending [ZonedDateTime] (inclusive).
 * @return `true` if the current [ZonedDateTime] is between from and to, `false` otherwise.
 */
fun ZonedDateTime.isBetween(from: ZonedDateTime, to: ZonedDateTime) = !isBefore(from) && !isAfter(to)
