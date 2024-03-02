package io.justdevit.kotlin.boost.kotest.mockserver

import io.justdevit.kotlin.boost.kotest.ExternalToolExtension
import io.kotest.core.spec.Spec
import org.mockserver.client.MockServerClient
import org.mockserver.integration.ClientAndServer
import org.mockserver.integration.ClientAndServer.startClientAndServer
import kotlin.reflect.KClass
import kotlin.reflect.jvm.jvmName

private var clientAndServer: ClientAndServer? = null

private val MOCKSERVER_EXTENSION_ACTIVATION_ANNOTATIONS: List<String> =
    listOf(
        "io.micronaut.test.extensions.kotest5.annotation.MicronautTest",
    )

/**
 * Represents a mock server extension for an external tool.
 * Implements the [ExternalToolExtension] interface.
 */
object MockServerExtension : ExternalToolExtension<ClientAndServer, MockServerClient> {
    override fun <T : Spec> instantiate(clazz: KClass<T>): Spec? {
        if (clazz
                .annotations
                .map { it.annotationClass.jvmName }
                .any { it in MOCKSERVER_EXTENSION_ACTIVATION_ANNOTATIONS } && clientAndServer == null
        ) {
            clientAndServer = startClientAndServer()
        }
        return null
    }

    override fun mount(configure: ClientAndServer.() -> Unit): MockServerClient {
        if (clientAndServer == null) {
            clientAndServer = startClientAndServer()
        }
        return clientAndServer!!
    }

    override suspend fun afterProject() {
        clientAndServer?.stop()
    }
}
