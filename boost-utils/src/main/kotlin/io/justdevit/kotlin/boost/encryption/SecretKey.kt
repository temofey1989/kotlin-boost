package io.justdevit.kotlin.boost.encryption

import javax.crypto.SecretKeyFactory
import javax.crypto.spec.SecretKeySpec

/**
 * Represents a secret key used for encryption and decryption.
 *
 * @param value The secret key used for encryption and decryption.
 * @param salt The salt value used for generating the secret key.
 */
class SecretKey(value: String, salt: String) :
    SecretKeySpec(
        SecretKeyFactory
            .getInstance(SECRET_KEY_FACTORY_ALGORITHM)
            .generateSecret(Key(secretKey = value, salt = salt))
            .encoded,
        SECRET_KEY_ALGORITHM,
    )
