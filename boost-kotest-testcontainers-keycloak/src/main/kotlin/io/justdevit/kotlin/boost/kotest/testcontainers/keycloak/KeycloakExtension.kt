package io.justdevit.kotlin.boost.kotest.testcontainers.keycloak

import dasniko.testcontainers.keycloak.KeycloakContainer
import io.justdevit.kotlin.boost.extension.runIf
import io.justdevit.kotlin.boost.kotest.ExternalToolExtension
import io.kotest.core.spec.Spec
import org.keycloak.admin.client.Keycloak
import org.testcontainers.containers.wait.strategy.HostPortWaitStrategy
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock
import kotlin.reflect.KClass

/**
 * Lazy-initialized variable that represents the Keycloak admin client.
 * The admin client is obtained from the Keycloak container.
 */
val KEYCLOAK_ADMIN_CLIENT: Keycloak by lazy {
    keycloakContainer!!.keycloakAdminClient
}

private var keycloakContainer: KeycloakContainer? = null
private val LOCK: Lock = ReentrantLock()

/**
 * The `KeycloakExtension` class is an implementation of the `ExternalToolExtension` interface.
 * It provides methods to instantiate a Keycloak container, mount it, and perform operations after the project.
 */
class KeycloakExtension<A : Annotation>(private val activationAnnotations: Collection<KClass<A>> = emptySet()) : ExternalToolExtension<KeycloakContainer, Keycloak> {

    companion object {
        val INSTANCE: KeycloakExtension<*> by lazy { KeycloakExtension<Annotation>() }
    }

    constructor(vararg activationAnnotations: KClass<A>) : this(activationAnnotations.toSet())

    override fun <T : Spec> instantiate(clazz: KClass<T>): Spec? {
        if (clazz
                .annotations
                .any { it.annotationClass in activationAnnotations }
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
        LOCK.runIf({ keycloakContainer == null }) {
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
