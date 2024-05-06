package io.justdevit.kotlin.boost.base64

import java.util.Base64

/**
 * Encodes a byte array into a Base64 string.
 *
 * @return the Base64 encoded string.
 */
fun ByteArray.encodeToString(): String = Base64.getEncoder().encodeToString(this)

/**
 * Encodes the given byte array using Base64 encoding.
 *
 * @return The encoded byte array.
 */
fun ByteArray.encode(): ByteArray = Base64.getEncoder().encode(this)

/**
 * Decodes the [ByteArray] into a [String] using Base64 decoding.
 *
 * @return The [String] representation of the decoded [ByteArray].
 */
fun ByteArray.decodeToString(): String = String(this.decode())

/**
 * Decodes the given [ByteArray] using Base64 decoding.
 *
 * @return The decoded [ByteArray].
 */
fun ByteArray.decode(): ByteArray = Base64.getDecoder().decode(this)
