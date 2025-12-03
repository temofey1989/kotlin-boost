package io.justdevit.kotlin.boost.hashing

import io.justdevit.kotlin.boost.base64.decode
import io.justdevit.kotlin.boost.base64.isBase64
import io.justdevit.kotlin.boost.hashing.HashingConstants.CHECKSUM_SEPARATOR
import io.justdevit.kotlin.boost.hashing.HashingConstants.SHA256_ALGORITHM
import java.security.MessageDigest.getInstance

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
        SHA256_ALGORITHM -> Sha256Checksum(checksumValue)
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
        Sha256Checksum::class -> Sha256Checksum(getInstance(SHA256_ALGORITHM).digest(toByteArray())) as T
        UnspecifiedChecksum::class -> UnspecifiedChecksum(toByteArray()) as T
        else -> throw UnsupportedOperationException("Unsupported checksum type ${T::class}.")
    }

/**
 * Calculates the SHA-256 hash value of a given string and returns it as a hexadecimal string.
 *
 * @return the SHA-256 hash value in hexadecimal format.
 */
fun String.sha256ToHex() = toByteArray().sha256().toHex()

/**
 * Converts a string to its SHA-256 hash value represented as a string.
 *
 * @return the SHA-256 hash value as a string.
 */
fun String.sha256ToString() = toByteArray().sha256ToString()

/**
 * Converts this string to a hexadecimal string representation.
 *
 * @return The hexadecimal string representation of this string.
 */
fun String.toHex(): String = toByteArray().toHex()
