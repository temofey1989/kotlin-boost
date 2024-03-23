package io.justdevit.kotlin.boost.kotest.testcontainters.rabbitmq

import io.kotest.core.extensions.install
import io.kotest.core.spec.Spec

/**
 * Installs the RabbitMQ Extension for a Spec.
 *
 * Usage:
 * ```
 * class MyTest : FreeSpec({
 *
 *     installRabbitMq()
 *
 *     ...
 * })
 * ```
 */
fun Spec.installRabbitMq() = install(RabbitMqExtension.INSTANCE)
