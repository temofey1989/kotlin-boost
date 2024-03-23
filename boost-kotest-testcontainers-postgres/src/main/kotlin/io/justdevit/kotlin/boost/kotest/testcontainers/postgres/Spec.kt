package io.justdevit.kotlin.boost.kotest.testcontainers.postgres

import io.kotest.core.extensions.install
import io.kotest.core.spec.Spec

/**
 * Installs the Postgres Extension for a Spec.
 *
 * Usage:
 * ```
 * class MyTest : FreeSpec({
 *
 *     installPostgres()
 *
 *     ...
 * })
 * ```
 */
fun Spec.installPostgres() = install(PostgresExtension.INSTANCE)
