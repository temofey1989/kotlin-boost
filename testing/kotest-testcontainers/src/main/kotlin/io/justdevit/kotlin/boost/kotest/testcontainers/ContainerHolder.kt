package io.justdevit.kotlin.boost.kotest.testcontainers

import io.justdevit.kotlin.boost.kotest.ExternalToolHolder
import org.testcontainers.containers.GenericContainer

/**
 * Abstract class for managing instances of [GenericContainer].
 *
 * @param T The type of container managed by this holder that extends [GenericContainer].
 */
abstract class ContainerHolder<T : GenericContainer<*>> : ExternalToolHolder<T>() {

    override fun tearDownTool() {
        materialized?.stop()
    }
}
