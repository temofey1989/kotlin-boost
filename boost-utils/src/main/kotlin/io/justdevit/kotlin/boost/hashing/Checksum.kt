@file:Suppress("ArrayInDataClass")

package io.justdevit.kotlin.boost.hashing

import io.justdevit.kotlin.boost.extension.decode
import io.justdevit.kotlin.boost.extension.encodeToString
import io.justdevit.kotlin.boost.extension.hasText
import io.justdevit.kotlin.boost.extension.isBase64
import java.security.MessageDigest.getInstance

const val CHECKSUM_SEPARATOR = ":"
const val SHA256_CHECKSUM_ALGORITHM = "SHA-256"

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
data class Sha256Checksum(override val value: ByteArray) : Checksum(SHA256_CHECKSUM_ALGORITHM)

/**
 * Represents an unspecified checksum value.
 *
 * @param value The byte array representing the checksum value.
 */
data class UnspecifiedChecksum(override val value: ByteArray) : Checksum("")

/**
 * Calculates the checksum value for the given string.
 * If the string is not blank, it separates the extension and the checksum value.
 * It then decodes the checksum value if it is in Base64 format.
 * Finally, it returns a CheckSum object based on the extension.
 *
 * @return The calculated CheckSum object.
 *
 * @throws IllegalArgumentException if the string is blank.
 */
fun String.toChecksum(): Checksum {
    require(isNotBlank()) {
        "Checksum text cannot be blank."
    }
    val separatorPosition = indexOf(CHECKSUM_SEPARATOR)
    val extension = if (separatorPosition > 0) substring(0, separatorPosition) else null
    val checksumText = if (separatorPosition > 0) substring(separatorPosition + CHECKSUM_SEPARATOR.length) else this
    val checksumValue = if (checksumText.isBase64()) checksumText.decode().toByteArray() else checksumText.toByteArray()
    return when (extension?.uppercase()) {
        SHA256_CHECKSUM_ALGORITHM -> Sha256Checksum(checksumValue)
        else -> UnspecifiedChecksum(checksumValue)
    }
}

/**
 * Converts a string to a specific type of checksum.
 *
 * @return The converted checksum of type [T].
 *
 * @throws UnsupportedOperationException if the specified [T] is not supported.
 */
inline fun <reified T : Checksum> String.to(): T =
    when (T::class) {
        Sha256Checksum::class -> Sha256Checksum(getInstance(SHA256_CHECKSUM_ALGORITHM).digest(toByteArray())) as T
        UnspecifiedChecksum::class -> UnspecifiedChecksum(toByteArray()) as T
        else -> throw UnsupportedOperationException("Unsupported checksum type ${T::class}.")
    }
