package io.justdevit.kotlin.boost.compression

import io.justdevit.kotlin.boost.base64.encodeToString
import java.io.ByteArrayOutputStream
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream

/**
 * Compresses a byte array using GZIP-compression.
 *
 * @return The compressed byte array.
 */
fun ByteArray.compress(): ByteArray {
    val bos = ByteArrayOutputStream()
    GZIPOutputStream(bos).bufferedWriter().use { it.write(this.encodeToString()) }
    return bos.toByteArray()
}

/**
 * Decompresses a byte array using GZIP-compression.
 *
 * @return The decompressed byte array.
 */
fun ByteArray.decompress(): ByteArray = GZIPInputStream(this.inputStream()).bufferedReader().use { it.readText().toByteArray() }
