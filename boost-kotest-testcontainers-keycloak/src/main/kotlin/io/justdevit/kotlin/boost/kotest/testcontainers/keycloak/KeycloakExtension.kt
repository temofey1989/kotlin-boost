package io.justdevit.kotlin.boost.kotest.testcontainers.keycloak

import dasniko.testcontainers.keycloak.KeycloakContainer
import io.justdevit.kotlin.boost.kotest.ExternalToolExtension
import io.kotest.core.spec.Spec
import org.keycloak.admin.client.Keycloak
import org.testcontainers.containers.wait.strategy.HostPortWaitStrategy
import kotlin.reflect.KClass
import kotlin.reflect.jvm.jvmName

/**
 * Lazy-initialized variable that represents the Keycloak admin client.
 * The admin client is obtained from the Keycloak container.
 */
val KEYCLOAK_ADMIN_CLIENT: Keycloak by lazy {
    keycloakContainer!!.keycloakAdminClient
}

private var keycloakContainer: KeycloakContainer? = null

private val KEYCLOAK_EXTENSION_ACTIVATION_ANNOTATIONS: List<String> =
    listOf(
        "io.micronaut.test.extensions.kotest5.annotation.MicronautTest",
    )

/**
 * The `KeycloakExtension` class is an implementation of the `ExternalToolExtension` interface.
 * It provides methods to instantiate a Keycloak container, mount it, and perform operations after the project.
 */
object KeycloakExtension : ExternalToolExtension<KeycloakContainer, Keycloak> {
    override fun <T : Spec> instantiate(clazz: KClass<T>): Spec? {
        if (clazz
                .annotations
                .map { it.annotationClass.jvmName }
                .any { it in KEYCLOAK_EXTENSION_ACTIVATION_ANNOTATIONS }
        ) {
            startContainer()
        }
        return null
    }

    override fun mount(configure: KeycloakContainer.() -> Unit): Keycloak {
        startContainer()
        return keycloakContainer!!.keycloakAdminClient
    }

    override suspend fun afterProject() {
        keycloakContainer?.stop()
    }

    private fun startContainer() {
        if (keycloakContainer == null) {
            keycloakContainer =
                KeycloakContainer("quay.io/keycloak/keycloak:latest").apply {
                    if (javaClass.classLoader.getResource("realm.json") != null) {
                        withRealmImportFile("/realm.json")
                    }
                    start()
                    waitingFor(HostPortWaitStrategy())
                    System.setProperty("KEYCLOAK_BASE_URL", authServerUrl)
                }
            KEYCLOAK_ADMIN_CLIENT.realms().findAll().forEach {
                val uppercaseName =
                    it
                        .realm
                        .uppercase()
                        .replace("-", "_")
                System.setProperty("${uppercaseName}_JWKS_URI", "${keycloakContainer!!.authServerUrl}/realms/${it.realm}/protocol/openid-connect/certs")
                System.setProperty("${uppercaseName}_ISSUER_URL", "${keycloakContainer!!.authServerUrl}/realms/${it.realm}")
            }
        }
    }
}
