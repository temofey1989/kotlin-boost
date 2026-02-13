package io.justdevit.kotlin.boost.kotest.logging

import io.justdevit.kotlin.boost.kotest.SpecInstallation

/**
 * Applies logging extension for the current spec.
 *
 * Usage:
 * ```
 * class MyTest : FreeSpec({
 *
 *     install {
 *         logging()
 *     }
 *
 *     ...
 * })
 * ```
 */
fun SpecInstallation.logging() = install(LoggingExtension)
