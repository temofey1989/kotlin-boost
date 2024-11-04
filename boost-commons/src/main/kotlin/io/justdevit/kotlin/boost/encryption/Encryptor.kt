package io.justdevit.kotlin.boost.encryption

import io.justdevit.kotlin.boost.base64.encodeToString

/**
 * Represents an interface for encrypting data.
 */
interface Encryptor {

    /**
     * Encrypts a given raw text value.
     *
     * @param value The [RawText] to be encrypted.
     * @return The encrypted text as an [EncryptedText] object.
     */
    fun encrypt(value: RawText): EncryptedText = EncryptedText(encrypt(value.value))

    /**
     * Encrypts a given value and returns the encrypted text as a [String].
     *
     * @param value The String value to be encrypted.
     * @return The encrypted text as a String.
     */
    fun encrypt(value: String): String = encrypt(value.toByteArray()).encodeToString()

    /**
     * Encrypts a byte array.
     *
     * @param bytes The byte array to be encrypted.
     * @return The encrypted byte array.
     */
    fun encrypt(bytes: ByteArray): ByteArray
}
