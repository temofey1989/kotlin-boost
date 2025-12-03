package io.justdevit.kotlin.boost.encryption

import io.justdevit.kotlin.boost.base64.decode
import io.justdevit.kotlin.boost.base64.isBase64
import io.justdevit.kotlin.boost.encryption.EncryptionConstants.ENCRYPTED_DATA_PREFIX

/**
 * Represents an interface for decrypting encrypted texts.
 */
interface Decrypter {

    /**
     * Decrypts the given [EncryptedText].
     *
     * @param value The encrypted text to decrypt.
     * @return The decrypted raw text.
     */
    fun decrypt(value: EncryptedText): RawText = RawText(decrypt(value.value))

    /**
     * Decrypts the given encrypted value.
     *
     * @param value The encrypted value to decrypt.
     * @return The decrypted value.
     */
    fun decrypt(value: String): String {
        var clearedValue = value
        if (clearedValue.startsWith(ENCRYPTED_DATA_PREFIX)) {
            clearedValue = clearedValue.substring(ENCRYPTED_DATA_PREFIX.length)
        }
        val bytes = if (clearedValue.isBase64()) clearedValue.toByteArray().decode() else clearedValue.toByteArray()
        return String(decrypt(bytes))
    }

    /**
     * Decrypts the given byte array.
     *
     * @param bytes The byte array to decrypt.
     * @return The decrypted byte array.
     */
    fun decrypt(bytes: ByteArray): ByteArray
}
