package io.justdevit.kotlin.boost.domain.model

const val CURSOR_DIRECTION_SEPARATOR_MINIMAL_POSITION = 1

enum class CursorDirection(val separator: String) {
    ASC(">"),
    DESC("<"),
    ;

    companion object {
        fun resolveFrom(text: String) =
            entries
                .filter { text.indexOf(it.separator) >= CURSOR_DIRECTION_SEPARATOR_MINIMAL_POSITION }
                .minBy { text.indexOf(it.separator) }
    }
}
