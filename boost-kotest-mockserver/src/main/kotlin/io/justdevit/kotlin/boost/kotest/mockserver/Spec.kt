package io.justdevit.kotlin.boost.kotest.mockserver

import io.justdevit.kotlin.boost.kotest.SpecInstallation
import io.kotest.core.spec.Spec
import org.mockserver.client.MockServerClient

/**
 * Installs the Mock Server extension for the current spec.
 * This method enables the mock server extension for the current spec,
 * allowing you to easily mock server responses during testing.
 *
 * Usage:
 * ```
 * class MyTest : FreeSpec({
 *
 *     install {
 *          mockServer()
 *     }
 *
 *     ...
 * })
 * ```
 */
fun SpecInstallation.mockServer(configure: MockServerClient.() -> Unit = {}) = install(MockServerExtension(), configure)

/**
 * The `mockServer` property is an instance of the [MockServerClient] class, which is used for interacting
 * with a Mock Server.
 *
 * @property Spec.mockServer The property is defined as an extension property on the [Spec] class.
 *
 * @return [MockServerClient] The [MockServerClient] instance that is lazily initialized.
 *
 * @throws IllegalStateException If Mock Server client is not initialized.
 */
val Spec.mockServer: MockServerClient
    get() = MockServerHolder.tool
