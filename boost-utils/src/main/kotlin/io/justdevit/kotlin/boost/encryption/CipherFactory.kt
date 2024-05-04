package io.justdevit.kotlin.boost.encryption

import javax.crypto.Cipher
import javax.crypto.Cipher.DECRYPT_MODE
import javax.crypto.Cipher.ENCRYPT_MODE

interface CipherFactory {

    fun createCipher(mode: Int): Cipher

    fun createEncryptCipher() = createCipher(ENCRYPT_MODE)

    fun createDecryptCipher() = createCipher(DECRYPT_MODE)
}
