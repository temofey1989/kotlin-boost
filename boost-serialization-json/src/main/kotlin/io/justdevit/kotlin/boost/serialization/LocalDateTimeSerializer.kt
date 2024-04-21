package io.justdevit.kotlin.boost.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind.STRING
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDateTime

object LocalDateTimeSerializer : KSerializer<LocalDateTime> {

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("java.time.LocalDateTime", STRING)

    override fun serialize(encoder: Encoder, value: LocalDateTime) = encoder.encodeString(value.toString())

    override fun deserialize(decoder: Decoder): LocalDateTime = LocalDateTime.parse(decoder.decodeString())
}
