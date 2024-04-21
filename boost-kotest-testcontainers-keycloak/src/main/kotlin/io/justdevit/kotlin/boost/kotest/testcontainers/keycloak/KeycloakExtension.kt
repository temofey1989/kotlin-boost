package io.justdevit.kotlin.boost.kotest.testcontainers.keycloak

import dasniko.testcontainers.keycloak.KeycloakContainer
import io.justdevit.kotlin.boost.extension.runIf
import io.justdevit.kotlin.boost.kotest.ANY_EXTENSION_FILTERS
import io.justdevit.kotlin.boost.kotest.AnnotationExtensionFilter
import io.justdevit.kotlin.boost.kotest.ExtensionFilter
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

internal var keycloakContainer: KeycloakContainer? = null
private val LOCK: Lock = ReentrantLock()

/**
 * The `KeycloakExtension` class is an implementation of the `ExternalToolExtension` interface.
 * It provides methods to instantiate a Keycloak container, mount it, and perform operations after the project.
 */
class KeycloakExtension(private val filters: Collection<ExtensionFilter> = ANY_EXTENSION_FILTERS) : ExternalToolExtension<KeycloakContainer, Keycloak> {

    constructor(vararg filters: ExtensionFilter) : this(filters.toSet())

    override fun <T : Spec> instantiate(clazz: KClass<T>): Spec? {
        if (filters.any { it.decide(clazz) }) {
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
                    System.setProperty(KEYCLOAK_BASE_URL_PROPERTY, authServerUrl)
                }
            configureSystemProperties()
        }
    }

    private fun configureSystemProperties() {
        val realms = KEYCLOAK_REALMS
        System.setProperty(KEYCLOAK_REALMS_PROPERTY, realms.joinToString(separator = ","))
        if (realms.size == 1) {
            configureRealmIssuerUri(realms[0])
        } else {
            realms.forEach {
                configureRealmIssuerUri(it, it.toSystemPropertyPrefix())
            }
        }
    }

    private fun String.toSystemPropertyPrefix() =
        trim()
            .uppercase()
            .replace("-", "_")
            .replace(Regex("\\s+"), "_")

    private fun configureRealmIssuerUri(realm: String, prefix: String = "") {
        val fullPrefix = if (prefix.isBlank()) "" else "${prefix}_"
        System.setProperty("${fullPrefix}KEYCLOAK_ISSUER_URI", "${keycloakContainer!!.authServerUrl}/realms/$realm")
    }
}

/**
 * Creates a [KeycloakExtension] with the specified predicates for annotation [A].
 *
 * @param predicates The predicates used to filter the annotations.
 * @return The [KeycloakExtension] object.
 */
inline fun <reified A : Annotation> KeycloakExtension(vararg predicates: (A) -> Boolean): KeycloakExtension {
    val filters = when {
        predicates.isEmpty() -> ANY_EXTENSION_FILTERS
        else -> predicates.map { AnnotationExtensionFilter(A::class, it) }
    }
    return KeycloakExtension(filters)
}

/**
 * Creates a [KeycloakExtension] with the specified predicate for annotation [A].
 *
 * @param predicate The predicate used to filter the annotations.
 * @return The [KeycloakExtension] object.
 */
inline fun <reified A : Annotation> KeycloakExtension(noinline predicate: (A) -> Boolean) = KeycloakExtension<A>(*arrayOf(predicate))
