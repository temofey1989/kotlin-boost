package io.justdevit.kotlin.boost.extension

import io.justdevit.kotlin.boost.EUROPEAN_DATE_TIME_FORMATTER
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZoneOffset.UTC
import java.time.ZoneOffset.systemDefault
import java.time.temporal.ChronoUnit.DAYS
import java.time.temporal.ChronoUnit.HALF_DAYS
import java.time.temporal.ChronoUnit.HOURS
import java.time.temporal.ChronoUnit.MILLIS
import java.time.temporal.ChronoUnit.MINUTES
import java.time.temporal.ChronoUnit.MONTHS
import java.time.temporal.ChronoUnit.SECONDS
import java.time.temporal.ChronoUnit.WEEKS
import java.time.temporal.ChronoUnit.YEARS

/**
 * Converts a [LocalDateTime] object to [OffsetDateTime] in UTC timezone.
 *
 * @return The converted [OffsetDateTime] in UTC.
 */
fun LocalDateTime.toUTC(): OffsetDateTime = atZone(systemDefault()).toOffsetDateTime().withOffsetSameInstant(UTC)

/**
 * Truncates the given [LocalDateTime] object to millis precision.
 *
 * @return A new [LocalDateTime] object with the millis precision truncated.
 */
fun LocalDateTime.truncatedToMillis(): LocalDateTime = truncatedTo(MILLIS)

/**
 * Truncates the given [LocalDateTime] object to seconds precision.
 *
 * @return A new [LocalDateTime] object with the seconds precision truncated.
 */
fun LocalDateTime.truncatedToSeconds(): LocalDateTime = truncatedTo(SECONDS)

/**
 * Truncates the given [LocalDateTime] object to minutes precision.
 *
 * @return A new [LocalDateTime] object with the minutes precision truncated.
 */
fun LocalDateTime.truncatedToMinutes(): LocalDateTime = truncatedTo(MINUTES)

/**
 * Truncates the given [LocalDateTime] object to hours precision.
 *
 * @return A new [LocalDateTime] object with the hours precision truncated.
 */
fun LocalDateTime.truncatedToHours(): LocalDateTime = truncatedTo(HOURS)

/**
 * Truncates the given [LocalDateTime] object to days precision.
 *
 * @return A new [LocalDateTime] object with the days precision truncated.
 */
fun LocalDateTime.truncatedToDays(): LocalDateTime = truncatedTo(DAYS)

/**
 * Truncates the given [LocalDateTime] object to half-days precision.
 *
 * @return A new [LocalDateTime] object with the half-days precision truncated.
 */
fun LocalDateTime.truncatedToHalfDays(): LocalDateTime = truncatedTo(HALF_DAYS)

/**
 * Truncates the given [LocalDateTime] object to weeks precision.
 *
 * @return A new [LocalDateTime] object with the weeks precision truncated.
 */
fun LocalDateTime.truncatedToWeeks(): LocalDateTime = truncatedTo(WEEKS)

/**
 * Truncates the given [LocalDateTime] object to months precision.
 *
 * @return A new [LocalDateTime] object with the months precision truncated.
 */
fun LocalDateTime.truncatedToMonths(): LocalDateTime = truncatedTo(MONTHS)

/**
 * Truncates the given [LocalDateTime] object to years precision.
 *
 * @return A new [LocalDateTime] object with the years precision truncated.
 */
fun LocalDateTime.truncatedToYears(): LocalDateTime = truncatedTo(YEARS)

/**
 * Checks if the current [LocalDateTime] is between the given range.
 *
 * @param from The start [LocalDateTime] of the range.
 * @param to The end [LocalDateTime] of the range.
 * @return `true` if the current [LocalDateTime] is between the range, `false` otherwise.
 */
fun LocalDateTime.isBetween(from: LocalDateTime, to: LocalDateTime): Boolean = !(isBefore(from) || isAfter(to))

/**
 * Converts the [LocalDateTime] object to a European string representation.
 *
 * @return The European string representation of the [LocalDateTime] object.
 */
fun LocalDateTime.toEuropeanString(): String = format(EUROPEAN_DATE_TIME_FORMATTER)

/**
 * Checks if this [LocalDateTime] is not before the specified other [LocalDateTime].
 *
 * @param other The [LocalDateTime] to compare with.
 * @return `true` if this [LocalDateTime] is not before the other [LocalDateTime], `false` otherwise
 */
fun LocalDateTime.isNotBefore(other: LocalDateTime): Boolean = !this.isBefore(other)

/**
 * Checks if this [LocalDateTime] is not after the specified [other] [LocalDateTime].
 *
 * @param other The [LocalDateTime] to compare against.
 * @return `true` if this [LocalDateTime] is not after the specified [other], `false` otherwise.
 */
fun LocalDateTime.isNotAfter(other: LocalDateTime): Boolean = !this.isAfter(other)

/**
 * Converts the current [LocalDateTime] object to [OffsetDateTime] using the system default time zone.
 *
 * @return The converted [OffsetDateTime] object.
 */
fun LocalDateTime.toOffsetDateTime(): OffsetDateTime =
    atOffset(
        ZoneId
            .systemDefault()
            .rules
            .getOffset(this),
    )
