package io.justdevit.kotlin.boost.encryption

import io.justdevit.kotlin.boost.encryption.EncryptionConstants.SECRET_KEY_ITERATION_COUNT
import io.justdevit.kotlin.boost.encryption.EncryptionConstants.SECRET_KEY_LENGTH
import javax.crypto.spec.PBEKeySpec

/**
 * Represents a cryptographic key used for encryption and decryption.
 *
 * @param secretKey The secret key used for encryption and decryption.
 * @param salt The salt value used for generating the secret key.
 */
class Key(secretKey: String, salt: String) :
    PBEKeySpec(
        secretKey.toCharArray(),
        salt.toByteArray(),
        SECRET_KEY_ITERATION_COUNT,
        SECRET_KEY_LENGTH,
    )
