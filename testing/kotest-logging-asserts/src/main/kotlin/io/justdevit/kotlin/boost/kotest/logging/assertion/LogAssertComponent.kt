package io.justdevit.kotlin.boost.kotest.logging.assertion

/**
 * Represents an assertion for validating log entries.
 *
 * This interface serves as the base for various types of log assertions
 * that can be used to verify the presence, format, or order of log messages.
 */
sealed interface LogAssertComponent {

    /**
     * Converts the log assertion into its string representation.
     *
     * @return A string that represents the log assertion details.
     */
    fun asString(): String
}
