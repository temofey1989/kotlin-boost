package io.justdevit.kotlin.boost.kotest.testcontainters.toxiproxy

import eu.rekawek.toxiproxy.ToxiproxyClient
import io.justdevit.kotlin.boost.environment.property
import io.justdevit.kotlin.boost.kotest.testcontainers.ContainerHolder
import org.testcontainers.containers.Network
import org.testcontainers.containers.wait.strategy.HostPortWaitStrategy
import org.testcontainers.toxiproxy.ToxiproxyContainer
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

/**
 * Represents a container holder for managing the lifecycle of a Toxiproxy container.
 */
object ToxiproxyHolder : ContainerHolder<ToxiproxyContainer>() {

    /**
     * Represents the default Docker image tag used for initializing and running Toxiproxy containers in the testing environment.
     * The value can be overridden by specifying a system property or environment variable named `toxiproxy.image-tag`.
     *
     * If not explicitly defined, this variable defaults to `2.5.0`.
     */
    val imageTag = property("toxiproxy.image-tag", "2.5.0")

    private val toxiproxyClient by lazy {
        ToxiproxyClient(tool.host, tool.controlPort)
    }
    private var nextToxiPort = 8666
    private val lock = ReentrantLock()
    private val toxicMap = mutableMapOf<String, Proxic>()

    /**
     * Creates and retrieves a [Proxic] instance associated with the specified alias and upstream configuration.
     *
     * @param alias The unique identifier for the proxy being created.
     * @param upstream The upstream endpoint that the proxy will forward traffic to.
     * @return An instance of [Proxic] representing the created proxy.
     */
    fun createToxic(alias: String, upstream: String): Proxic =
        (
            toxicMap[alias]
                ?: lock.withLock {
                    val proxy = toxiproxyClient.createProxy(alias, "0.0.0.0:$nextToxiPort", upstream)
                    nextToxiPort++
                    Proxic(
                        listenHost = tool.host,
                        listenPort = tool.getMappedPort(
                            proxy.listen
                                .substringAfterLast(":")
                                .toInt(),
                        ),
                        proxy = proxy,
                        onDeleted = { toxicMap.remove(alias) },
                    ).also {
                        toxicMap[alias] = it
                    }
                }
        ).also { it.enable() }

    override fun initializeTool() =
        ToxiproxyContainer("ghcr.io/shopify/toxiproxy:$imageTag").apply {
            withNetwork(Network.SHARED)
            withNetworkAliases("toxiproxy")
            start()
            waitingFor(HostPortWaitStrategy())
            System.setProperty("TOXIPROXY_HOST", host)
            System.setProperty("TOXIPROXY_PORT", controlPort.toString())
        }
}
