package io.justdevit.kotlin.boost.encryption

import javax.crypto.Cipher
import javax.crypto.Cipher.DECRYPT_MODE
import javax.crypto.Cipher.ENCRYPT_MODE

/**
 * Factory interface for creating Cipher instances with specific modes.
 */
interface CipherFactory {

    /**
     * Creates a new Cipher instance configured with the specified mode.
     *
     * @param mode The operation mode of this cipher (e.g., encryption or decryption).
     * @return A Cipher instance set to the given mode.
     */
    fun createCipher(mode: Int): Cipher

    /**
     * Creates a new Cipher instance set to encryption mode.
     *
     * @return A Cipher instance configured for encryption.
     */
    fun createEncryptCipher() = createCipher(ENCRYPT_MODE)

    /**
     * Creates a new Cipher instance set to decryption mode.
     *
     * @return A Cipher instance configured for decryption.
     */
    fun createDecryptCipher() = createCipher(DECRYPT_MODE)
}
