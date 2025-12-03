package io.justdevit.kotlin.boost.domain.exception

open class DomainException(
    override val message: String,
    open val code: String = UNDEFINED_EXCEPTION_CODE,
    open val source: Any = UndefinedDomainExceptionSource,
) : Exception(message, null, false, false),
    java.io.Serializable {

    val rootCause: DomainException by lazy {
        var rootCause = this
        while (rootCause.source is Error) {
            rootCause = rootCause.source as DomainException
        }
        rootCause
    }
}

const val UNDEFINED_EXCEPTION_CODE = "UNDEFINED"

object UndefinedDomainExceptionSource

data class SystemException(
    override val message: String,
    override val source: Any = UndefinedDomainExceptionSource,
    override val code: String = UNDEFINED_EXCEPTION_CODE,
) : DomainException(
        message = message,
        source = source,
    )

data class EntityNotFoundException(
    override val message: String,
    override val source: Any = UndefinedDomainExceptionSource,
    override val code: String = UNDEFINED_EXCEPTION_CODE,
) : DomainException(
        message = message,
        source = source,
    )

data class BusinessException(
    override val message: String,
    override val source: Any = UndefinedDomainExceptionSource,
    override val code: String = UNDEFINED_EXCEPTION_CODE,
) : DomainException(
        message = message,
        source = source,
    )

data class IntegrationException(
    override val message: String,
    override val source: Any = UndefinedDomainExceptionSource,
    override val code: String = UNDEFINED_EXCEPTION_CODE,
) : DomainException(
        message = message,
        source = source,
    )
