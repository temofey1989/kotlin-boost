package io.justdevit.kotlin.boost.kotest.testcontainers.postgres

import io.justdevit.kotlin.boost.kotest.SpecInstallation

/**
 * Installs the Postgres Extension for a Spec.
 *
 * Usage:
 * ```
 * class MyTest : FreeSpec({
 *
 *     install {
 *         postgres()
 *     }
 *
 *     ...
 * })
 * ```
 */
fun SpecInstallation.postgres() = install(PostgresExtension())
