package io.justdevit.kotlin.boost.kotest.testcontainers.keycloak

import dasniko.testcontainers.keycloak.KeycloakContainer
import io.justdevit.kotlin.boost.environment.property
import io.justdevit.kotlin.boost.io.listResourceFiles
import io.justdevit.kotlin.boost.kotest.testcontainers.ContainerHolder
import org.testcontainers.containers.Network.SHARED
import org.testcontainers.containers.wait.strategy.HostPortWaitStrategy

/**
 * Object responsible for managing the lifecycle of a Keycloak test container.
 */
object KeycloakHolder : ContainerHolder<KeycloakContainer>() {

    /**
     * Represents the default Docker image tag used for initializing and running Keycloak containers in the testing environment.
     * The value can be overridden by specifying a system property or environment variable named `keycloak.image-tag`.
     *
     * If not explicitly defined, this variable defaults to `latest`.
     */
    val imageTag = property("keycloak.image-tag", "latest")

    /**
     * Specifies the directory where Keycloak realm configurations are located.
     *
     * This variable retrieves its value from the property `keycloak.realm-directory`,
     * defaulting to `realms` if the property is not set. It is used to configure
     * the location of realm-related data and settings in a Keycloak environment.
     */
    val realmDirectory = property("keycloak.realm-directory", "realms")

    override fun initializeTool() =
        KeycloakContainer("quay.io/keycloak/keycloak:$imageTag").apply {
            registerRealms()
            withNetwork(SHARED)
            withNetworkAliases("keycloak")
            start()
            waitingFor(HostPortWaitStrategy())
            configureSystemProperties()
        }

    private fun KeycloakContainer.registerRealms() {
        listResourceFiles(realmDirectory)
            .filter { it.endsWith(".json") }
            .map { "/$realmDirectory/$it" }
            .forEach(::withRealmImportFiles)
    }

    private fun KeycloakContainer.configureSystemProperties() {
        System.setProperty(KEYCLOAK_BASE_URL_PROPERTY, authServerUrl)
        configureRealmSystemProperties()
    }

    private fun KeycloakContainer.configureRealmSystemProperties() {
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

    private fun KeycloakContainer.configureRealmIssuerUri(realm: String, suffix: String = "") {
        val fullSuffix = if (suffix.isBlank()) "" else "_$suffix"
        System.setProperty("${KEYCLOAK_ISSUER_PROPERTY}$fullSuffix", "$authServerUrl/realms/$realm")
    }

    private fun String.toSystemPropertyPrefix() =
        trim()
            .uppercase()
            .replace("-", "_")
            .replace(Regex("\\s+"), "_")
}
