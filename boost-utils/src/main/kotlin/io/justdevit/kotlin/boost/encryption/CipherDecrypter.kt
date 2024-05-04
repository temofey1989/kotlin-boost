package io.justdevit.kotlin.boost.encryption

class CipherDecrypter(private val factory: CipherFactory) : Decrypter {
    override fun decrypt(bytes: ByteArray): ByteArray = factory.createDecryptCipher().doFinal(bytes)
}
