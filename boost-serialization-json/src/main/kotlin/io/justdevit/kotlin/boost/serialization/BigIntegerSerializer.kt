package io.justdevit.kotlin.boost.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind.LONG
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object BigIntegerSerializer : KSerializer<BigInteger> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("java.math.BigInteger", LONG)

    override fun serialize(encoder: Encoder, value: BigInteger) = encoder.encodeLong(value.toLong())

    override fun deserialize(decoder: Decoder): BigInteger = BigInteger.valueOf(decoder.decodeLong())
}
