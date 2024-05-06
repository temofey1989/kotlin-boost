package io.justdevit.kotlin.boost.io

import java.io.Closeable
import java.io.IOException
import java.io.InputStream

private const val EOF = -1
private val EMPTY_BYTE_ARRAY = ByteArray(0)

class InputStreamChunkIterator(private val inputStream: InputStream, chunkSize: Int) :
    Iterator<ByteArray>,
    Closeable {

    private val chunk: ByteArray
    private var readLength = -1
    private var closed = false
    private var pulled = false

    init {
        require(chunkSize > 0) {
            "Buffer size should be greater than zero."
        }
        this.chunk = ByteArray(chunkSize)
    }

    override fun hasNext(): Boolean {
        read()
        val result = readLength != EOF
        return result
    }

    override fun next(): ByteArray {
        read()
        try {
            var bytes = EMPTY_BYTE_ARRAY
            if (readLength > 0) {
                bytes = ByteArray(readLength)
                System.arraycopy(chunk, 0, bytes, 0, readLength)
            }
            return bytes
        } finally {
            pulled = false
        }
    }

    override fun close() {
        try {
            inputStream.close()
        } finally {
            closed = true
        }
    }

    private fun read() {
        checkAvailability()
        if (pulled) {
            return
        }
        try {
            readLength = inputStream.read(chunk)
            pulled = true
        } catch (e: IOException) {
            readLength = EOF
            closed = true
        }
    }

    private fun checkAvailability() {
        if (closed) {
            throw IOException("Input stream is closed.")
        }
    }
}
