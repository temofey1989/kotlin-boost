package io.justdevit.kotlin.boost.encryption

class CipherEncryptor(private val factory: CipherFactory) : Encryptor {
    override fun encrypt(bytes: ByteArray): ByteArray = factory.createEncryptCipher().doFinal(bytes)
}
