package io.justdevit.kotlin.boost.domain.model

import com.github.ksuid.Ksuid
import java.util.UUID

interface Identifier<T> {
    val value: T
}

abstract class CompositeIdentifier<T, U> : Identifier<T> {

    var internal: InternalIdentifier<U> = UndefinedInternalIdentifier()
}

sealed interface InternalIdentifier<T> {
    val value: T?
}

class UndefinedInternalIdentifier<T> : InternalIdentifier<T> {
    override val value: T? = null
}

data class UuidInternalIdentifier(override val value: UUID) : InternalIdentifier<UUID>

data class KsuidInternalIdentifier(override val value: Ksuid) : InternalIdentifier<Ksuid>

data class StringInternalIdentifier(override val value: String) : InternalIdentifier<String>

data class LongInternalIdentifier(override val value: Long) : InternalIdentifier<Long>

data class IntInternalIdentifier(override val value: Int) : InternalIdentifier<Int>

fun UUID?.toInternalIdentifier() =
    when (this) {
        null -> UndefinedInternalIdentifier()
        else -> UuidInternalIdentifier(this)
    }

fun Ksuid?.toInternalIdentifier() =
    when (this) {
        null -> UndefinedInternalIdentifier()
        else -> KsuidInternalIdentifier(this)
    }

fun String?.toInternalIdentifier() =
    when (this) {
        null -> UndefinedInternalIdentifier()
        else -> StringInternalIdentifier(this)
    }

fun Long?.toInternalIdentifier() =
    when (this) {
        null -> UndefinedInternalIdentifier()
        else -> LongInternalIdentifier(this)
    }

fun Int?.toInternalIdentifier() =
    when (this) {
        null -> UndefinedInternalIdentifier()
        else -> IntInternalIdentifier(this)
    }
