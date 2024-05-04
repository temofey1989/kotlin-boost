package io.justdevit.kotlin.boost.domain.model

import io.justdevit.kotlin.boost.extension.decode
import io.justdevit.kotlin.boost.extension.encode

data class Cursor(
    val identifier: String,
    val value: String,
    val direction: CursorDirection,
) {

    fun serialize() = "$identifier${direction.separator}$value".encode()

    companion object {
        fun deserialize(value: String): Cursor {
            require(value.isNotBlank()) {
                "Blank value is not allowed to deserialize cursor."
            }
            val decodedValue = value.decode()
            val direction = CursorDirection.resolveFrom(decodedValue)
            val separatorPosition = decodedValue.indexOf(direction.separator)
            return Cursor(
                decodedValue.substring(0, separatorPosition),
                decodedValue.substring(separatorPosition + direction.separator.length),
                direction,
            )
        }
    }
}
