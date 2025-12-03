package io.justdevit.kotlin.boost.base64

private val BASE64_REGEX = Regex("^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)?$")

/**
 * Checks if the current string is a valid Base64 encoded string.
 *
 * @return `true` if the string is a valid Base64 encoded string, `false` otherwise.
 */
fun String.isBase64() = BASE64_REGEX.matches(this)

/**
 * Encodes the current string into a Base64 encoded string.
 *
 * This method converts the current string into a byte array and then encodes it
 * using Base64 encoding. The resulting Base64 encoded string is returned.
 *
 * @return The Base64 encoded string representation of the current string.
 */
fun String.encode() = this.toByteArray().encodeToString()

/**
 * Decodes the current [String] into a [ByteArray] using Base64 decoding.
 *
 * @return The [String] representation of the decoded [ByteArray].
 */
fun String.decode() = this.toByteArray().decodeToString()
