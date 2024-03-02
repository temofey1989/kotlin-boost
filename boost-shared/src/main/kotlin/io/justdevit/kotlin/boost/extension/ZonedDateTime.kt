package io.justdevit.kotlin.boost.extension

import java.time.ZoneId.systemDefault
import java.time.ZonedDateTime

/**
 * Converts the given [ZonedDateTime] to the local system time zone.
 *
 * @return The [ZonedDateTime] converted to the local system time zone.
 */
fun ZonedDateTime.toLocalZone(): ZonedDateTime = withZoneSameInstant(systemDefault())
