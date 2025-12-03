package io.justdevit.kotlin.boost.encryption

/**
 * Contains constants used for encryption and decryption operations.
 */
object EncryptionConstants {
    /**
     * Prefix used to identify strings that represent encrypted data.
     */
    const val ENCRYPTED_DATA_PREFIX = "encrypted:"

    /**
     * Represents the AES cipher transformation algorithm used for cryptographic operations.
     */
    const val AES_CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding"

    /**
     * Specifies the algorithm name used for generating and utilizing symmetric secret keys.
     */
    const val SECRET_KEY_ALGORITHM = "AES"

    /**
     * The algorithm used for generating a [javax.crypto.SecretKeyFactory] instance.
     */
    const val SECRET_KEY_FACTORY_ALGORITHM = "PBKDF2WithHmacSHA256"

    /**
     * The number of iterations used in the key derivation process for generating a cryptographic key.
     */
    const val SECRET_KEY_ITERATION_COUNT = 65536

    /**
     * Specifies the length of the secret key in bits.
     */
    const val SECRET_KEY_LENGTH = 256
}
