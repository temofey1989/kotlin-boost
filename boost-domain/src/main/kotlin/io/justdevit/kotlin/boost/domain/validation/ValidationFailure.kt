package io.justdevit.kotlin.boost.domain.validation

data class ValidationFailure(
    val message: String,
    val key: String? = null,
    val metadata: Map<String, String> = emptyMap(),
)
