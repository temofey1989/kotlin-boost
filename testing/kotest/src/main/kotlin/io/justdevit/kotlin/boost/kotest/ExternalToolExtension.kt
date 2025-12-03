package io.justdevit.kotlin.boost.kotest

import io.kotest.core.extensions.ConstructorExtension
import io.kotest.core.extensions.MountableExtension
import io.kotest.core.listeners.AfterProjectListener
import io.kotest.core.spec.Spec
import kotlin.reflect.KClass

/**
 * Represents an extension for an external tool.
 */
abstract class ExternalToolExtension<T>(private val holder: ExternalToolHolder<T>, protected val filters: Collection<ExtensionFilter> = emptyList()) :
    ConstructorExtension,
    AfterProjectListener,
    MountableExtension<T, T> {

    override fun <T : Spec> instantiate(clazz: KClass<T>): Spec? {
        if (filters.isEmpty() || filters.any { it.decide(clazz) }) {
            holder.initialize()
        }
        return null
    }

    override fun mount(configure: T.() -> Unit) = holder.tool.also(configure)
}
