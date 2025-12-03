package io.justdevit.kotlin.boost.domain.model

const val CURSOR_DIRECTION_SEPARATOR_MINIMAL_POSITION = 1

/**
 * Represents the direction of a cursor used for pagination.
 *
 * @param separator The separator used to serialize and deserialize the cursor.
 */
enum class CursorDirection(val separator: String) {
    /**
     * Ascending.
     */
    ASC(">"),

    /**
     * Descending.
     */
    DESC("<"),
    ;

    companion object {
        /**
         * Resolves the appropriate [CursorDirection] from the given text.
         *
         * @param text The text to resolve the [CursorDirection] from.
         * @return The resolved [CursorDirection], or null if no valid [CursorDirection] is found.
         */
        fun from(text: String) =
            entries
                .filter { text.indexOf(it.separator) >= CURSOR_DIRECTION_SEPARATOR_MINIMAL_POSITION }
                .minBy { text.indexOf(it.separator) }
    }
}
