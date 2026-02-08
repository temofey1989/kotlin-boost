package io.justdevit.kotlin.boost.kotest

import io.kotest.core.extensions.ConstructorExtension
import io.kotest.core.extensions.MountableExtension
import io.kotest.core.listeners.AfterProjectListener
import io.kotest.core.spec.Spec
import kotlin.reflect.KClass

/**
 * Represents an extension for an external tool.
 *
 * @param T The type of the external tool.
 * @property holder The holder of the external tool.
 * @property filters The filters for selecting classes to be initialized.
 */
abstract class ExternalToolExtension<T>(protected val holder: ExternalToolHolder<T>, protected val filters: Collection<ExtensionFilter> = emptyList()) :
    ConstructorExtension,
    AfterProjectListener,
    MountableExtension<T, T> {

    override fun <T : Spec> instantiate(clazz: KClass<T>): Spec? {
        if (filters.isEmpty() || filters.any { it.decide(clazz) }) {
            holder.initialize()
        }
        return null
    }

    override fun mount(configure: T.() -> Unit): T {
        holder.initialize()
        return holder.tool.also(configure)
    }
}
