package io.justdevit.kotlin.boost.kotest.testcontainters.rabbitmq

import io.justdevit.kotlin.boost.kotest.SpecInstallation

/**
 * Installs the RabbitMQ Extension for a Spec.
 *
 * Usage:
 * ```
 * class MyTest : FreeSpec({
 *
 *     install {
 *         rabbitMq()
 *     }
 *
 *     ...
 * })
 * ```
 */
fun SpecInstallation.rabbitMq() = install(RabbitMqExtension())
