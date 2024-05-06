package io.justdevit.kotlin.boost.domain.model

import io.justdevit.kotlin.boost.base64.decode
import io.justdevit.kotlin.boost.base64.encode

/**
 * Represents a cursor used for pagination.
 *
 * @property identifier The unique identifier associated with the cursor.
 * @property value The value of the cursor.
 * @property direction The direction of the cursor.
 */
data class Cursor(
    val identifier: String,
    val value: String,
    val direction: CursorDirection,
) {

    /**
     * Serializes the Cursor object into a string representation.
     */
    fun serialize() = "$identifier${direction.separator}$value".encode()

    companion object {

        /**
         * Deserializes a string into a Cursor object.
         *
         * @param value The string value to deserialize.
         * @return The deserialized Cursor object.
         * @throws IllegalArgumentException if the value is blank.
         */
        fun deserialize(value: String): Cursor {
            require(value.isNotBlank()) {
                "Blank value is not allowed to deserialize cursor."
            }
            val decodedValue = value.decode()
            val direction = CursorDirection.from(decodedValue)
            val separatorPosition = decodedValue.indexOf(direction.separator)
            return Cursor(
                decodedValue.substring(0, separatorPosition),
                decodedValue.substring(separatorPosition + direction.separator.length),
                direction,
            )
        }
    }
}
