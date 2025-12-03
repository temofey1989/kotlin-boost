package io.justdevit.kotlin.boost.hashing

/**
 * Provides constant values used across hashing-related functionality.
 */
object HashingConstants {
    /**
     * Delimiter used to separate the algorithm type and the checksum value in a checksum string.
     */
    const val CHECKSUM_SEPARATOR = ":"

    /**
     * Specifies the algorithm name for calculating SHA-256 hash values.
     */
    const val SHA256_ALGORITHM = "SHA-256"
}
