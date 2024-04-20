package io.justdevit.kotlin.boost.kotest.mockserver

import io.kotest.core.extensions.install
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
 *     installMockServer()
 *
 *     ...
 * })
 * ```
 */
fun Spec.installMockServer() = install(MockServerExtension())

/**
 * The `mockServer` property is a lazy-loaded instance of the [MockServerClient] class, which is used for interacting
 * with a mock server.
 *
 * @property Spec.mockServer The property is defined as an extension property on the [Spec] class.
 * @return [MockServerClient] The [MockServerClient] instance that is lazily initialized.
 */
val Spec.mockServer: MockServerClient by lazy { MockServerClient(MOCK_SERVER_HOST, clientAndServer!!.port) }
