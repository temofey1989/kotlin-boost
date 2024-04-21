package io.justdevit.kotlin.boost.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind.STRING
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalTime

object LocalTimeSerializer : KSerializer<LocalTime> {

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("java.time.LocalTime", STRING)

    override fun serialize(encoder: Encoder, value: LocalTime) = encoder.encodeString(value.toString())

    override fun deserialize(decoder: Decoder): LocalTime = LocalTime.parse(decoder.decodeString())
}
