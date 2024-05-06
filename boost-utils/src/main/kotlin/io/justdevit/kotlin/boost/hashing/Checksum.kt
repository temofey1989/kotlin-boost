@file:Suppress("ArrayInDataClass")

package io.justdevit.kotlin.boost.hashing

import io.justdevit.kotlin.boost.base64.encodeToString
import io.justdevit.kotlin.boost.extension.hasText

/**
 * Representing a checksum value.
 *
 * @property algorithm The algorithm for the checksum.
 * @property value The byte array representing the checksum value.
 */
sealed class Checksum(val algorithm: String) {
    abstract val value: ByteArray

    fun asString(): String {
        val prefix = if (algorithm.hasText()) algorithm.lowercase() else ""
        return "$prefix$CHECKSUM_SEPARATOR${value.encodeToString()}"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Sha256Checksum

        if (!value.contentEquals(other.value)) return false
        if (algorithm != other.algorithm) return false

        return true
    }

    override fun hashCode(): Int {
        var result = value.contentHashCode()
        result = 31 * result + algorithm.hashCode()
        return result
    }
}

/**
 * Represents SHA-256 checksum value.
 *
 * @param value The byte array representing the SHA-256 checksum value.
 */
data class Sha256Checksum(override val value: ByteArray) : Checksum(SHA256_ALGORITHM)

/**
 * Represents an unspecified checksum value.
 *
 * @param value The byte array representing the checksum value.
 */
data class UnspecifiedChecksum(override val value: ByteArray) : Checksum("")
