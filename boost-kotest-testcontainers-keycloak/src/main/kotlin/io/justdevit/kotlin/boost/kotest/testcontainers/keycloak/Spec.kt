package io.justdevit.kotlin.boost.kotest.testcontainers.keycloak

import io.justdevit.kotlin.boost.kotest.SpecInstallation
import io.kotest.core.extensions.install

/**
 * Installs the Keycloak extension for the current testing spec.
 *
 * The Keycloak extension is used to enable Keycloak authentication during testing.
 *
 * When this method is called, the Keycloak extension will be installed for the current test spec.
 * This will allow Keycloak authentication functionality to be used in the tests defined in this spec.
 *
 * Usage:
 * ```
 * class MyTest : FreeSpec({
 *
 *     install {
 *         keycloak()
 *     }
 *
 *     ...
 * })
 * ```
 *
 * @see KeycloakExtension
 */
fun SpecInstallation.keycloak() = install(KeycloakExtension())
