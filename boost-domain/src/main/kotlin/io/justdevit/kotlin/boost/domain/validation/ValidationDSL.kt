package io.justdevit.kotlin.boost.domain.validation

import io.justdevit.kotlin.boost.domain.exception.UNDEFINED_EXCEPTION_CODE

@DslMarker
annotation class ValidationDslMarker

@ValidationDslMarker
data class Validation(
    var messageBuilder: () -> String = { "Validation - FAILED" },
    var code: String = UNDEFINED_EXCEPTION_CODE,
    val metadata: MutableMap<Any, Any> = mutableMapOf(),
    val failures: MutableList<ValidationFailure> = mutableListOf(),
) {

    constructor(name: String) : this({ "$name - FAILED" })

    fun failure(block: ValidationFailureBuilder.() -> String) {
        val builder = ValidationFailureBuilder()
        val message = builder.block()
        failures += ValidationFailure(
            message = message,
            key = builder.key,
            metadata = builder.metadata,
        )
    }

    infix fun String.to(value: String?) {
        if (value == null) {
            metadata.remove(this)
        } else {
            metadata[this] = value
        }
    }

    fun throwOnFailures() {
        if (failures.isNotEmpty()) {
            throw ValidationException(
                message = messageBuilder(),
                code = code,
                failures = failures.toList(),
                metadata = metadata
                    .mapKeys { it.key.toString() }
                    .mapValues { it.value.toString() },
            )
        }
    }
}

@ValidationDslMarker
data class ValidationFailureBuilder(var key: String? = null, val metadata: MutableMap<String, String> = mutableMapOf()) {

    infix fun String.to(value: String?) {
        if (value == null) {
            metadata.remove(this)
        } else {
            metadata[this] = value
        }
    }
}

fun validation(name: String, action: Validation.() -> Unit) {
    val validation = Validation(name)
    validation.action()
    validation.throwOnFailures()
}
