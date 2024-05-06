package io.justdevit.kotlin.boost.kotest.testcontainters.localstack

import io.kotest.core.extensions.install
import io.kotest.core.spec.Spec

/**
 * Installs the Localstack Extension for a Spec.
 *
 * Usage:
 * ```
 * class MyTest : FreeSpec({
 *
 *     installLocalstack()
 *
 *     ...
 * })
 * ```
 */
fun Spec.installLocalStack() = install(LocalstackExtension())
