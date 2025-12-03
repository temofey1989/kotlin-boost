package io.justdevit.kotlin.boost.kotest.mockserver

import io.justdevit.kotlin.boost.kotest.ExternalToolHolder
import org.mockserver.integration.ClientAndServer

/**
 * A singleton object responsible for managing the lifecycle of a Mock Server instance.
 */
object MockServerHolder : ExternalToolHolder<ClientAndServer>() {

    override fun initializeTool(): ClientAndServer =
        ClientAndServer.startClientAndServer().apply {
            System.setProperty("MOCK_SERVER_HOST", remoteAddress().hostString)
            System.setProperty("MOCK_SERVER_PORT", port.toString())
        }

    override fun tearDownTool() {
        materialized?.stop()
    }
}
