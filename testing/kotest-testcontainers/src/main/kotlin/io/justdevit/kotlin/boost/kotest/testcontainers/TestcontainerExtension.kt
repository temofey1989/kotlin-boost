package io.justdevit.kotlin.boost.kotest.testcontainers

import io.justdevit.kotlin.boost.kotest.ExtensionFilter
import io.justdevit.kotlin.boost.kotest.ExternalToolExtension
import io.justdevit.kotlin.boost.kotest.ExternalToolHolder
import org.testcontainers.containers.Container

abstract class TestcontainerExtension<T : Container<T>>(holder: ExternalToolHolder<T>, filters: Collection<ExtensionFilter>) : ExternalToolExtension<T>(holder, filters)
