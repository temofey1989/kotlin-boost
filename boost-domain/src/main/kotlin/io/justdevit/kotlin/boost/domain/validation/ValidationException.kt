package io.justdevit.kotlin.boost.domain.validation

import io.justdevit.kotlin.boost.domain.exception.DomainException
import io.justdevit.kotlin.boost.domain.exception.UNDEFINED_EXCEPTION_CODE
import io.justdevit.kotlin.boost.domain.exception.UndefinedDomainExceptionSource

data class ValidationException(
    override val message: String,
    override val source: Any = UndefinedDomainExceptionSource,
    override val code: String = UNDEFINED_EXCEPTION_CODE,
    val metadata: Map<String, String> = emptyMap(),
    val failures: List<ValidationFailure> = emptyList(),
) : DomainException(
        message = message,
        source = source,
    )
