package io.justdevit.kotlin.boost.kotest.testcontainters.localstack

import io.justdevit.kotlin.boost.kotest.SpecInstallation

/**
 * Installs the Localstack Extension for a Spec.
 *
 * Usage:
 * ```
 * class MyTest : FreeSpec({
 *
 *     install {
 *         localstack()
 *     }
 *
 *     ...
 * })
 * ```
 */
fun SpecInstallation.localStack() = install(LocalStackExtension())
