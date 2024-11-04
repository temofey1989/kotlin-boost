package io.justdevit.kotlin.boost.encryption

import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

class AesCipherFactory(private val initialVector: IvParameterSpec, private val secretKey: SecretKey) : CipherFactory {

    override fun createCipher(mode: Int): Cipher {
        val cipher = Cipher.getInstance(AES_CIPHER_ALGORITHM)
        cipher.init(mode, secretKey, initialVector)
        return cipher
    }
}
