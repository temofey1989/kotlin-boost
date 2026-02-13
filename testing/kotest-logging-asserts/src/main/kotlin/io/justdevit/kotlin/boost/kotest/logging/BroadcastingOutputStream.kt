package io.justdevit.kotlin.boost.kotest.logging

import java.io.OutputStream

/**
 * A specialized [OutputStream] that broadcasts written data to multiple underlying output streams.
 *
 * @param streams The array of [OutputStream] instances to which data will be broadcast.
 */
class BroadcastingOutputStream(vararg val streams: OutputStream) : OutputStream() {

    override fun write(value: Int) =
        synchronized(this) {
            streams.forEach { it.write(value) }
        }

    override fun flush() =
        synchronized(this) {
            streams.forEach { it.flush() }
        }
}
