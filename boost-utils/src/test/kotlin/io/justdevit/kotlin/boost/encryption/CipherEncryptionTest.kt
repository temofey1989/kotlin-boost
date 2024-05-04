package io.justdevit.kotlin.boost.encryption

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class CipherEncryptionTest :
    FreeSpec(
        {
            val initialVector = InitialVector("AQIDBAUGBwgJCgsMDQ4PEA==")
            val secretKey = SecretKey(value = "secret", salt = "salt")
            val factory = AesCipherFactory(initialVector, secretKey)
            val encryptor = CipherEncryptor(factory)
            val decrypter = CipherDecrypter(factory)

            "Should be able to encrypt and decrypt given text" {
                val text = "This is a secret message."

                val encryptedText = encryptor.encrypt(text)
                val decryptedText = decrypter.decrypt(encryptedText)

                decryptedText shouldBe text
            }
        },
    )
