package io.justdevit.kotlin.boost

import java.io.IOException
import java.net.ServerSocket

/**
 * Generates a random port number within the specified range.
 *
 * @param from The starting port number (inclusive). Default value is 5000.
 * @param to The ending port number (inclusive). Default value is 65000.
 *
 * @return A random port number within the specified range.
 *
 * @throws IOException If no free port is found within the specified range.
 */
fun randomPort(from: Int = 5000, to: Int = 65000): Int {
    var port = from
    while (port <= to) {
        try {
            return ServerSocket(port).use {
                it.localPort
            }
        } catch (_: IOException) {
            port++
        }
    }
    throw IOException("No free port found.")
}
