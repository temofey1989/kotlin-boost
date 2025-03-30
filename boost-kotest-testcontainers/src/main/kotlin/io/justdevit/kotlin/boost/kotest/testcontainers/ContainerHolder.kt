package io.justdevit.kotlin.boost.kotest.testcontainers

import io.justdevit.kotlin.boost.kotest.ExternalToolHolder
import org.testcontainers.containers.GenericContainer

abstract class ContainerHolder<T : GenericContainer<*>> : ExternalToolHolder<T>() {

    override fun tearDownTool() {
        materialized?.stop()
    }
}
