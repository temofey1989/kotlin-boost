package io.justdevit.kotlin.boost.logging

import java.io.Serializable

/**
 * A DSL builder class for creating log records.
 *
 * @property builderFunction The function to build the log message.
 * @property throwable An optional throwable object associated with the log record.
 */
@LogRecordMarker
class LogRecordBuilder(private val builderFunction: LogRecordBuilderFunction, private val throwable: Throwable? = null) {
    private val fields: MutableMap<String, Any?> = mutableMapOf()

    infix fun String.to(value: Serializable?) {
        require(isNotBlank()) {
            "The key is required."
        }
        fields[this] = value ?: "null"
    }

    /**
     * Builds a [LogRecord] containing the message, attributes, and an optional throwable.
     *
     * @return An instance of [LogRecord].
     */
    fun build(): LogRecord {
        val message = builderFunction()
        return LogRecord(
            message = message,
            attributes = fields.toMap(),
            cause = throwable,
        )
    }
}
