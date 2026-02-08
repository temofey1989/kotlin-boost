package io.justdevit.kotlin.boost.kotest.testcontainers

import io.justdevit.kotlin.boost.kotest.ExtensionFilter
import io.justdevit.kotlin.boost.kotest.ExternalToolExtension
import io.justdevit.kotlin.boost.kotest.ExternalToolHolder
import org.testcontainers.containers.Container

/**
 * An abstract extension class for managing integrations with Testcontainers-based external tools.
 *
 * @param T The type of container managed by the extension that extends [Container].
 * @param holder The [ExternalToolHolder] responsible for managing the lifecycle of the container.
 * @param filters A collection of [ExtensionFilter] instances used to decide if the extension
 *        applies to specific test classes.
 */
abstract class TestcontainerExtension<T : Container<T>>(holder: ExternalToolHolder<T>, filters: Collection<ExtensionFilter>) : ExternalToolExtension<T>(holder, filters)
