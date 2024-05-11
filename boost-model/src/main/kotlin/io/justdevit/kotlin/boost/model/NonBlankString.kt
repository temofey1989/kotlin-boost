package io.justdevit.kotlin.boost.model

@JvmInline
value class NonBlankString(val value: String) {
    init {
        require(value.isNotBlank()) { "Value must not be blank." }
    }
}
