package io.justdevit.kotlin.boost.encryption

import io.justdevit.kotlin.boost.extension.decode
import io.justdevit.kotlin.boost.extension.isBase64
import javax.crypto.spec.IvParameterSpec

/**
 * Represents an Initial Vector for encryption and decryption.
 *
 * @param value The initial vector as a byte array.
 */
class InitialVector(value: ByteArray) : IvParameterSpec(value) {

    /**
     * Constructs an instance of the [InitialVector] for given [String] value.
     *
     * @param value The initial vector as text. Can be Base64 encoded text also.
     */
    constructor(value: String) : this(if (value.isBase64()) value.toByteArray().decode() else value.toByteArray())
}
