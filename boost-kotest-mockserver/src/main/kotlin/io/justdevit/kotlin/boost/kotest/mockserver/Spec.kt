package io.justdevit.kotlin.boost.kotest.mockserver

import io.kotest.core.extensions.install
import io.kotest.core.spec.Spec

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
fun Spec.installMockServer() = install(MockServerExtension)
