package io.justdevit.kotlin.boost.extension

import java.text.Normalizer
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.util.Locale
import java.util.UUID
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

/**
 * Throws an [IllegalArgumentException] if the string is empty or contains only whitespace characters.
 *
 * @param builder A lambda function that returns the exception message to be thrown if the string is empty or contains only whitespace characters.
 * @throws [IllegalArgumentException] If the string is empty or contains only whitespace characters.
 */
fun String.requireNotBlank(builder: () -> String) {
    if (this.isBlank()) {
        throw IllegalArgumentException(builder())
    }
}

/**
 * Executes the provided [builder] lambda function to obtain an error message if the given [String] is blank.
 * If the [String] is blank, this method throws an [IllegalArgumentException].
 *
 * @param builder A lambda function that returns the error message if the [String] is blank.
 * @throws [IllegalArgumentException] if the [String] is blank.
 */
context(String)
fun notBlank(builder: () -> String) {
    this@String.requireNotBlank(builder)
}

/**
 * Converts a string to an enum value of type T.
 *
 * @return The enum value corresponding to the string.
 */
inline fun <reified T : Enum<T>> String.toEnum(): T = enumValueOf(this)

/**
 * Converts a string to an enum of type [T] based on a given predicate.
 *
 * @param predicate The predicate used to filter the enum values.
 * @return The enum value that matches the predicate.
 */
inline fun <reified T : Enum<*>> String.toEnumBy(predicate: (T) -> Boolean): T =
    T::class.java
        .enumConstants
        .first(predicate)

/**
 * Parses the given string and returns the corresponding enum constant of type [T], or null if no match is found.
 *
 * @return The enum constant of type [T] that matches the string representation, or null if no matching enum constant is found.
 */
inline fun <reified T : Enum<T>> String.toEnumOrNull(): T? =
    T::class.java
        .enumConstants
        .firstOrNull { it.name == this }

/**
 * Converts a string to an enum value of type [T] if it satisfies the given [predicate].
 *
 * @param predicate The predicate function used to filter the enum values.
 * @return The enum value of type [T] that satisfies the [predicate], or null if no such value is found.
 */
inline fun <reified T : Enum<*>> String.toEnumOrNullBy(predicate: (T) -> Boolean): T? =
    T::class.java
        .enumConstants
        .firstOrNull(predicate)

/**
 * Converts a string to a list of enum values of the specified enum class.
 *
 * @param T The type of enum class.
 * @param delimiter The delimiter used to separate multiple enum values in the string. Default is ','.
 * @return A list of enum values matching the provided string.
 */
inline fun <reified T : Enum<T>> String.toEnums(delimiter: Char = ','): List<T> = split(delimiter).map(String::trim).map { enumValueOf(it) }

/**
 * Converts a [String] representation of a UUID to [UUID] object.
 *
 * @return The [UUID] object parsed from the string.
 */
fun String.toUUID(): UUID = UUID.fromString(this)

/**
 * Squashes all consecutive whitespace characters in a string into a single space.
 *
 * @return The modified string with consecutive whitespace characters squashed into a single space.
 */
fun String.squashWhitespaces() = this.replace("\\s+".toRegex(), " ").trim()

/**
 * Converts a string to a [Locale] object.
 *
 * If the string contains a delimiter "_" character, it splits the string and creates a
 * [Locale] object with the parts.
 *
 * If the string has two parts, it creates a [Locale] object with the language and country.
 * If the string has three parts, it creates a [Locale] object with the language, country, and variant.
 *
 * If the string does not contain a delimiter "_", it creates a [Locale] object with the string as the language.
 *
 * @return The [Locale] object created from the string.
 */
fun String.toLocale(): Locale {
    val delimiter = "_"
    if (this.indexOf(delimiter) != -1) {
        val parts = this.split(delimiter)
        if (parts.size == 2) {
            return Locale.of(parts[0], parts[1])
        } else if (parts.size == 3) {
            return Locale.of(parts[0], parts[1], parts[2])
        }
    }
    return Locale.of(this)
}

/**
 * Converts this String representation of a zone offset to a ZoneOffset instance.
 *
 * @return the [ZoneOffset] instance corresponding to the string representation.
 * @throws [java.time.DateTimeException] if the string cannot be parsed to a valid [ZoneOffset].
 */
fun String.toZoneOffset(): ZoneOffset = ZoneOffset.of(this)

/**
 * Parses this string into a [LocalDate].
 *
 * @return The [LocalDate] representation of this string.
 */
fun String.toLocalDate(): LocalDate = LocalDate.parse(this)

/**
 * Converts the string to a [LocalTime] instance.
 *
 * @return The [LocalTime] instance parsed from the string.
 */
fun String.toLocalTime(): LocalTime = LocalTime.parse(this)

/**
 * Converts a string to a [LocalDateTime] object.
 *
 * If the string ends with "Z", "+00:00", or "+0000", it is assumed that the string represents a UTC timestamp.
 * In this case, the string is converted to the system's local date and time.
 *
 * Otherwise, the string is parsed into a [LocalDateTime] object.
 *
 * @return The parsed [LocalDateTime] object.
 */
fun String.toLocalDateTime(): LocalDateTime = if (endsWith("Z") || endsWith("+00:00") || endsWith("+0000")) toSystemLocalDate() else LocalDateTime.parse(this)

/**
 * Converts the current representation of the Offset Date and Time to a [LocalDateTime] object in the system's local time zone.
 *
 * @return The [LocalDateTime] object representing the date and time in the system's local time zone.
 */
fun String.toSystemLocalDate(): LocalDateTime = toOffsetDateTime().toLocal().toLocalDateTime()

/**
 * Converts the string representation of a date and time to an instance of [ZonedDateTime].
 *
 * @return An instance of [ZonedDateTime] parsed from the given string representation.
 * @throws [DateTimeParseException] if the string cannot be parsed as a valid date and time.
 */
fun String.toZonedDateTime(): ZonedDateTime = ZonedDateTime.parse(this)

/**
 * Converts the current string representation to an [OffsetDateTime] object.
 *
 * @return The [OffsetDateTime] object parsed from the string representation.
 */
fun String.toOffsetDateTime(): OffsetDateTime = OffsetDateTime.parse(this)

/**
 * Checks if a given string represents a valid [LocalDate].
 *
 * @return `true` if the string represents a valid [LocalDate], `false` otherwise.
 */
fun String.isLocalDate(): Boolean =
    try {
        LocalDate.parse(this)
        true
    } catch (e: Throwable) {
        false
    }

/**
 * Truncates the string to a specified length.
 *
 * @param length The maximum length of the truncated string.
 * @return The truncated string.
 */
fun String.truncateToLength(length: Int): String =
    when {
        this.length <= length -> this
        length < 4 -> substring(0, length)
        else -> substring(0, this.length.coerceAtMost(length - 3)) + "..."
    }

/**
 * Converts a string to a specified type using the given block.
 *
 * @param block The block to perform conversion on the string.
 * @return The converted object or null if an exception occurs during conversion.
 */
fun <T> String.convertOrNull(block: (String) -> T): T? =
    try {
        block(this)
    } catch (e: Exception) {
        null
    }

/**
 * Generates a random string.
 *
 * @return A randomly generated string.
 */
fun randomString() = UUID.randomUUID().toString()

/**
 * Converts a string into a [List] of type [T] using the specified delimiter and transformer function.
 *
 * @param delimiter The delimiter used to split the string into substrings.
 * @param transformer The function used to transform each substring into type [T].
 * @return A [List] of type [T] containing the transformed substrings.
 *
 * @param T The type of the elements in the resulting [List].
 */
fun <T> String?.toList(delimiter: String = ",", transformer: (String) -> T): List<T> =
    if (this == null) {
        emptyList()
    } else {
        split(delimiter).map { transformer(it) }
    }

/**
 * Checks if the string representation of a value is boolean.
 *
 * @return `true` if the string is equal to "false" or "true" (case-insensitive), `false` otherwise.
 */
fun String.isBoolean(): Boolean = this.equals("false", true) || this.equals("true", true)

/**
 * Determines whether a string represents a numeric value.
 *
 * @return `true` if the string represents a numeric value, `false` otherwise.
 */
fun String.isNumeric(): Boolean = this.toDoubleOrNull() != null

/**
 * Converts the given string to a like literal, which can be used in SQL queries.
 * If the string contains an asterisk ('*'), it will be replaced with a percent ('%') character.
 * Otherwise, the string will be wrapped with percent ('%') characters.
 *
 * @return The like literal representation of the string.
 */
fun String.toLikeLiteral(): String = if (contains('*')) replace('*', '%') else "%$this%"

/**
 * Converts the string representation of a duration to a [Duration] object.
 *
 * @return The [Duration] object representing the duration specified by the string.
 */
fun String.toDuration(): Duration = Duration.parse(this)

/**
 * Checks whether the given string contains any whitespace characters.
 *
 * @return `true` if the string contains whitespace characters, `false` otherwise.
 */
fun String.hasWhitespaces(): Boolean = contains("\\s+".toRegex())

/**
 * Converts a string to dash style by replacing uppercase letters with a dash followed by the lowercase version.
 *
 * @return The string converted to dash style.
 */
fun String.toDashStyle() =
    this[0].lowercase() +
        this
            .substring(1)
            .map { if (it.isUpperCase()) "-${it.lowercase()}" else "$it" }
            .joinToString(separator = "")

/**
 * Checks if the given string has text.
 *
 * @return `true` if the string has text, `false` otherwise.
 */
@OptIn(ExperimentalContracts::class)
fun String?.hasText(): Boolean {
    contract {
        returnsNotNull()
    }
    return !isNullOrBlank()
}

/**
 * Removes diacritics (accented characters) from the given string.
 *
 * @receiver The string from which to remove diacritics.
 * @return A new string without diacritics.
 */
fun String.removeDiacritics() =
    Normalizer
        .normalize(this, Normalizer.Form.NFD)
        .replace("\\p{InCombiningDiacriticalMarks}+".toRegex(), "")

/**
 * Executes the specified action if this string is equal to the provided value.
 *
 * @param value The value to compare against.
 * @param action The action to execute if this string is equal to the provided value.
 */
inline fun String?.ifEquals(value: String?, action: () -> Unit) {
    if (this == value) action()
}

/**
 * Executes the specified action if the value of this string is not equal to the specified value.
 *
 * @param value The value to compare this string against.
 * @param action The action to be executed if the strings are not equal.
 */
inline fun String?.ifNotEquals(value: String?, action: () -> Unit) {
    if (this != value) action()
}

/**
 * Executes the `action` lambda on the [String] object only if it's not `null` or blank.
 *
 * @param action The action to execute on the [String] object if it's not `null` or blank.
 */
fun String?.ifNotBlank(action: String.() -> Unit) {
    if (!isNullOrBlank()) {
        this.action()
    }
}
