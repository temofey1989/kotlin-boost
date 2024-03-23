package io.justdevit.kotlin.boost.kotest.testcontainers.keycloak

import io.kotest.core.extensions.install
import io.kotest.core.spec.Spec

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
 *     installKeycloak()
 *
 *     ...
 * })
 * ```
 *
 * @see KeycloakExtension
 */
fun Spec.installKeycloak() = install(KeycloakExtension.INSTANCE)
