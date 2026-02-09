package io.justdevit.kotlin.boost.kotest.testcontainters.toxiproxy

import io.justdevit.kotlin.boost.kotest.SpecInstallation
import io.kotest.core.spec.Spec

/**
 * Installs the Toxiproxy Extension for a Spec.
 *
 * Usage:
 * ```kotlin
 * class MyTest : FreeSpec({
 *
 *     install {
 *         toxiproxy()
 *     }
 *
 *     ...
 * })
 * ```
 */
fun SpecInstallation.toxiproxy() = install(ToxiproxyExtension())

/**
 * Creates a [Proxic] instance associated with the given alias and upstream configuration.
 *
 * Usage:
 * ```kotlin
 * class MyTest : FreeSpec({
 *
 *     val dbProxy = proxic("db", "postgres:5432")
 *
 *     ...
 * })
 * ```
 *
 * @param alias The unique identifier for the proxy to be created.
 * @param upstream The upstream endpoint that the proxy will forward traffic to.
 */
fun Spec.proxic(alias: String, upstream: String) = ToxiproxyHolder.createToxic(alias, upstream)
