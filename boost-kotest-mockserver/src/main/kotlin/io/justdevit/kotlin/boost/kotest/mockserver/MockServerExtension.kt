package io.justdevit.kotlin.boost.kotest.mockserver

import io.justdevit.kotlin.boost.extension.runIf
import io.justdevit.kotlin.boost.kotest.ExternalToolExtension
import io.kotest.core.spec.Spec
import org.mockserver.client.MockServerClient
import org.mockserver.integration.ClientAndServer
import org.mockserver.integration.ClientAndServer.startClientAndServer
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock
import kotlin.reflect.KClass

internal var clientAndServer: ClientAndServer? = null
internal const val MOCK_SERVER_HOST = "localhost"

private val LOCK: Lock = ReentrantLock()

/**
 * Represents a mock server extension for an external tool.
 * Implements the [ExternalToolExtension] interface.
 */
class MockServerExtension<A : Annotation>(private val activationAnnotations: Collection<KClass<A>> = emptySet()) : ExternalToolExtension<ClientAndServer, MockServerClient> {

    companion object {
        val INSTANCE: MockServerExtension<*> by lazy { MockServerExtension<Annotation>() }
    }

    constructor(vararg activationAnnotations: KClass<A>) : this(activationAnnotations.toSet())

    override fun <T : Spec> instantiate(clazz: KClass<T>): Spec? {
        if (clazz
                .annotations
                .any { it.annotationClass in activationAnnotations }
        ) {
            initMockServer()
        }
        return null
    }

    override fun mount(configure: ClientAndServer.() -> Unit): MockServerClient {
        initMockServer()
        return clientAndServer!!
    }

    override suspend fun afterProject() {
        clientAndServer?.stop()
    }

    private fun initMockServer() {
        LOCK.runIf({ clientAndServer == null }) {
            clientAndServer = startClientAndServer()
            System.setProperty("MOCK_SERVER_HOST", MOCK_SERVER_HOST)
            System.setProperty("MOCK_SERVER_PORT", clientAndServer!!.port.toString())
        }
    }
}
