package io.justdevit.kotlin.boost.compression

import java.io.ByteArrayOutputStream
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream

/**
 * Compresses a string using GZIP-compression.
 *
 * @return The compressed string.
 */
fun String.compress(): String {
    val bos = ByteArrayOutputStream()
    GZIPOutputStream(bos).bufferedWriter().use { it.write(this) }
    return bos.toByteArray().decodeToString()
}

/**
 * Decompresses a GZIP-compressed string.
 *
 * @return The decompressed string.
 */
fun String.decompress(): String = GZIPInputStream(this.byteInputStream()).bufferedReader().use { it.readText() }
