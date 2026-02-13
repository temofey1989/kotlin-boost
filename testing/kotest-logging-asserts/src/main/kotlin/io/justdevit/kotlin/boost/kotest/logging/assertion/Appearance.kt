package io.justdevit.kotlin.boost.kotest.logging.assertion

/**
 * Represents the frequency or condition of log appearance during test validations.
 */
sealed interface Appearance : LogAssertComponent {

    /**
     * Represents the frequency of log appearances during test assertions.
     *
     * @property value The number of times the log is expected to appear.
     * @property negated Indicates whether the condition is negated.
     *                   If `true`, the log should NOT appear the specified number of times.
     */
    data class Times(val value: Int, val negated: Boolean = false) : Appearance {
        override fun asString() = value.toString()
    }

    /**
     * Represents a log appearance condition where the log is expected to never appear during test validations.
     */
    object Never : Appearance {
        override fun asString() = "0"
    }

    /**
     * Represents an undefined state for log appearance during test validations.
     * Indicates that the log appearance condition is not explicitly defined.
     */
    object Undefined : Appearance {
        override fun asString() = ""
    }
}
