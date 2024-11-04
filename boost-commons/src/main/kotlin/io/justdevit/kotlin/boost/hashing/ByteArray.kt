package io.justdevit.kotlin.boost.hashing

import io.justdevit.kotlin.boost.base64.encodeToString
import java.security.MessageDigest.getInstance

/**
 * Calculates the SHA-256 hash value of this byte array.
 *
 * @return the SHA-256 hash value as a byte array.
 */
fun ByteArray.sha256(): ByteArray = getInstance(SHA256_ALGORITHM).digest(this)

/**
 * Calculates the SHA-256 hash value of this byte array and returns it as a hexadecimal string.
 *
 * @return the SHA-256 hash value as a hexadecimal string.
 */
fun ByteArray.sha256ToHex() = sha256().toHex()

/**
 * Converts a byte array to SHA-256 hash value represented as a string.
 *
 * @return the SHA-256 hash value as a string.
 */
fun ByteArray.sha256ToString() = sha256().encodeToString()

/**
 * Converts this byte array to a hexadecimal string representation.
 *
 * @return the hexadecimal string representation of this byte array.
 */
fun ByteArray.toHex(): String = joinToString(separator = "") { "%02x".format(it) }
