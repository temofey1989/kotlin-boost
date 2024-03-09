package io.justdevit.kotlin.boost.logging

/**
 * Represents a snapshot of the logging context containing a map of attributes.
 *
 * @property attributes The map of attributes in the logging context.
 */
data class LogContextSnapshot(val attributes: Map<String, String?> = emptyMap())
