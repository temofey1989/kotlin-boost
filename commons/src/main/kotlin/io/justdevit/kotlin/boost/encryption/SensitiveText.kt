package io.justdevit.kotlin.boost.encryption

import io.justdevit.kotlin.boost.encryption.EncryptionConstants.ENCRYPTED_DATA_PREFIX

/**
 * Represents sensitive text that can be either encrypted or raw.
 *
 * @property value The value of the sensitive text.
 */
sealed class SensitiveText(val value: String) {
    companion object {
        fun of(value: String): SensitiveText = if (value.startsWith(ENCRYPTED_DATA_PREFIX)) EncryptedText(value) else RawText(value)
    }
}

/**
 * Represents an encrypted text.
 *
 * @param value The encrypted value.
 */
class EncryptedText(value: String) : SensitiveText(if (value.startsWith(ENCRYPTED_DATA_PREFIX)) value.substring(ENCRYPTED_DATA_PREFIX.length) else value) {
    override fun toString() = ENCRYPTED_DATA_PREFIX + value
}

/**
 * Represents raw text that is not encrypted.
 *
 * @param value The value of the raw text.
 */
class RawText(value: String) : SensitiveText(value)
