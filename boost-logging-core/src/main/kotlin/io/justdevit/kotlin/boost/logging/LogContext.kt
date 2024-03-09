package io.justdevit.kotlin.boost.logging

/**
 * Represents a logging context that stores attributes as key-value pairs.
 * Attributes can be added using the infix `to` function.
 * The `clear` property determines if the logging context should be cleared before adding new attributes.
 * The `restorePrevious` property determines if the previous attributes should be restored after applying the new attributes.
 *
 * @property attributes The map of attributes in the logging context.
 * @property clear Specifies if the logging context should be cleared before adding new attributes.
 * @property restorePrevious Specifies if the previous attributes should be restored after applying the new attributes.
 */
data class LogContext(
    val attributes: MutableMap<String, String?> = mutableMapOf(),
    var clear: Boolean = false,
    var restorePrevious: Boolean = true,
) {
    /**
     * Adds an attribute to the logging context.
     *
     * @param value The value of the attribute.
     */
    infix fun String.to(value: Any?) {
        attributes[this] = value?.toString() ?: "null"
    }
}
