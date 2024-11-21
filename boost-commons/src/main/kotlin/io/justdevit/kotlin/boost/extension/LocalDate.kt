package io.justdevit.kotlin.boost.extension

import io.justdevit.kotlin.boost.EUROPEAN_DATE_FORMATTER
import java.time.DayOfWeek.SATURDAY
import java.time.DayOfWeek.SUNDAY
import java.time.Instant
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.ZoneOffset.UTC
import java.time.ZoneOffset.ofHours
import java.time.format.DateTimeFormatter.ofPattern

/**
 * Extension property to get the first day of the week for a given [LocalDate].
 *
 * This property computes the date of the first day of the week (Monday) for
 * the LocalDate instance it is called on.
 */
val LocalDate.firstDayOfWeek: LocalDate
    get() = minusDays(dayOfWeek.value - 1L)

/**
 * Extension property that calculates the last day of the week for a given [LocalDate] instance.
 */
val LocalDate.lastDayOfWeek: LocalDate
    get() = firstDayOfWeek.plusDays(6)

/**
 * Extension property that calculates the first day of the month for a given [LocalDate] instance.
 */
val LocalDate.firstDayOfMonth: LocalDate
    get() = withDayOfMonth(1)

/**
 * Extension property that calculates the last day of the month for a given [LocalDate] instance.
 */
val LocalDate.lastDayOfMonth: LocalDate
    get() = firstDayOfMonth.plusMonths(1).minusDays(1)

/**
 * Extension property for [LocalDate] that represents the start of the day in [Instant].
 */
val LocalDate.startOfDay: Instant
    get() = atStartOfDay().toInstant(ofHours(TIME_OFFSET_HOURS))

/**
 * Extension property for [LocalDate] that represents the end of the day in [Instant].
 */
val LocalDate.endOfDay: Instant
    get() = plusDays(1)
        .atStartOfDay()
        .toInstant(ofHours(TIME_OFFSET_HOURS))
        .minusNanos(1)

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

/**
 * Formats this [LocalDate] instance using the given pattern and returns the formatted string.
 *
 * @param pattern The pattern describing the date and time format.
 * @return The formatted date string.
 */
fun LocalDate.format(pattern: String): String = format(ofPattern(pattern))

/**
 * Checks if the current [LocalDate] falls on a weekend.
 *
 * @return `true` if the date is a Saturday or Sunday, `false` otherwise.
 */
fun LocalDate.isWeekend() = dayOfWeek == SATURDAY || dayOfWeek == SUNDAY
