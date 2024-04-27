package io.justdevit.kotlin.boost.domain

import io.justdevit.kotlin.boost.serialization.UUID
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Contextual
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.SerialKind
import kotlinx.serialization.descriptors.buildSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

interface Identifier<T> {
    val value: T
}

@Serializable
abstract class CompositeIdentifier<T, U> : Identifier<T> {

    @Contextual
    var internal: InternalIdentifier<U> = UndefinedInternalIdentifier()
}

@Serializable
sealed interface InternalIdentifier<T> {
    val value: T?
}

@Serializable
@SerialName("UNDEFINED")
class UndefinedInternalIdentifier<T> : InternalIdentifier<T> {
    @Transient
    override val value: T? = null
}

@Serializable
@SerialName("UUID")
data class UuidInternalIdentifier(override val value: UUID) : InternalIdentifier<UUID>

fun UUID?.toInternalIdentifier() =
    when (this) {
        null -> UndefinedInternalIdentifier()
        else -> UuidInternalIdentifier(this)
    }

@Serializable
@SerialName("STRING")
data class StringInternalIdentifier(override val value: String) : InternalIdentifier<String>

fun String?.toInternalIdentifier() =
    when (this) {
        null -> UndefinedInternalIdentifier()
        else -> StringInternalIdentifier(this)
    }

@Serializable
@SerialName("LONG")
data class LongInternalIdentifier(override val value: Long) : InternalIdentifier<Long>

fun Long?.toInternalIdentifier() =
    when (this) {
        null -> UndefinedInternalIdentifier()
        else -> LongInternalIdentifier(this)
    }

@Serializable
@SerialName("INT")
data class IntInternalIdentifier(override val value: Int) : InternalIdentifier<Int>

fun Int?.toInternalIdentifier() =
    when (this) {
        null -> UndefinedInternalIdentifier()
        else -> IntInternalIdentifier(this)
    }

@Serializable(LazyInternalIdentifierSerializer::class)
internal class LazyInternalIdentifier : InternalIdentifier<Any> {

    @Transient
    var supplier: (() -> @Contextual Any?)? = null

    @Transient
    override val value: @Contextual Any?
        get() = supplier!!()
}

@Suppress("UNCHECKED_CAST")
fun <T> lazyInternalIdentifier(supplier: () -> T?) =
    LazyInternalIdentifier().apply {
        this.supplier = supplier
    } as InternalIdentifier<T>

fun <T> coLazyInternalIdentifier(supplier: suspend () -> T?) =
    lazyInternalIdentifier {
        runBlocking { supplier() }
    }

internal object LazyInternalIdentifierSerializer : KSerializer<LazyInternalIdentifier> {

    @OptIn(InternalSerializationApi::class, ExperimentalSerializationApi::class)
    override val descriptor: SerialDescriptor = buildSerialDescriptor("io.justdevit.kotlin.boost.domain.LazyInternalIdentifierSerializer", SerialKind.CONTEXTUAL)

    override fun deserialize(decoder: Decoder) = throw UnsupportedOperationException("Deserialization of ${LazyInternalIdentifier::class.simpleName} is not supported.")

    override fun serialize(encoder: Encoder, value: LazyInternalIdentifier) {
        when (value.value) {
            is UUID -> encoder.encodeSerializableValue(UuidInternalIdentifier.serializer(), UuidInternalIdentifier(value.value as UUID))

            is String -> encoder.encodeSerializableValue(StringInternalIdentifier.serializer(), StringInternalIdentifier(value.value as String))

            is Long -> encoder.encodeSerializableValue(LongInternalIdentifier.serializer(), LongInternalIdentifier(value.value as Long))

            is Int -> encoder.encodeSerializableValue(IntInternalIdentifier.serializer(), IntInternalIdentifier(value.value as Int))

            else -> encoder.encodeSerializableValue(
                UndefinedInternalIdentifier.serializer(Unit.serializer()),
                UndefinedInternalIdentifier(),
            )
        }
    }
}
