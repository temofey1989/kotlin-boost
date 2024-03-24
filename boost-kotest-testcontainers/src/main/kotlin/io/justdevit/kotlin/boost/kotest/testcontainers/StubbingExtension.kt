package io.justdevit.kotlin.boost.kotest.testcontainers

import io.justdevit.kotlin.boost.kotest.ExternalToolExtension
import io.kotest.core.spec.Spec
import io.kotest.core.spec.style.FreeSpec
import kotlin.reflect.KClass

/**
 * Represents a stubbing extension that conditionally stubs a specific class.
 *
 * @param predicate A function that determines whether the stubbing should occur or not.
 */
class StubbingExtension(private val predicate: () -> Boolean) : ExternalToolExtension<Any, Any> {

    override fun <T : Spec> instantiate(clazz: KClass<T>): Spec? {
        if (predicate()) {
            return object : FreeSpec(
                {
                    "STUB: ${clazz.simpleName}" {}
                },
            ) {}
        }
        return null
    }

    override fun mount(configure: Any.() -> Unit): Any = throw UnsupportedOperationException("Unable to mount: ${this.javaClass.simpleName}")
}
