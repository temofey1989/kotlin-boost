package io.justdevit.kotlin.boost.kotest.mockserver

import io.justdevit.kotlin.boost.extension.runIf
import io.justdevit.kotlin.boost.kotest.ANY_EXTENSION_FILTERS
import io.justdevit.kotlin.boost.kotest.AnnotationExtensionFilter
import io.justdevit.kotlin.boost.kotest.ExtensionFilter
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
class MockServerExtension(private val filters: Collection<ExtensionFilter> = ANY_EXTENSION_FILTERS) : ExternalToolExtension<ClientAndServer, MockServerClient> {

    constructor(vararg filters: ExtensionFilter) : this(filters.toSet())

    override fun <T : Spec> instantiate(clazz: KClass<T>): Spec? {
        if (filters.any { it.decide(clazz) }) {
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

/**
 * Creates a [MockServerExtension] with the specified predicates for annotation [A].
 *
 * @param predicates The predicates used to filter the annotations.
 * @return The [MockServerExtension] object.
 */
inline fun <reified A : Annotation> MockServerExtension(vararg predicates: (A) -> Boolean): MockServerExtension {
    val filters = when {
        predicates.isEmpty() -> ANY_EXTENSION_FILTERS
        else -> predicates.map { AnnotationExtensionFilter(A::class, it) }
    }
    return MockServerExtension(filters)
}
