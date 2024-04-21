package io.justdevit.kotlin.boost.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind.STRING
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.Period

object PeriodSerializer : KSerializer<Period> {

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("java.time.Period", STRING)

    override fun serialize(encoder: Encoder, value: Period) = encoder.encodeString(value.toString())

    override fun deserialize(decoder: Decoder): Period = Period.parse(decoder.decodeString())
}
