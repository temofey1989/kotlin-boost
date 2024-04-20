package io.justdevit.kotlin.boost.kotest

import io.kotest.core.spec.Spec
import kotlin.reflect.KClass

/**
 * Represents an extension filter for selecting any classes.
 *
 * This object implements the [ExtensionFilter] interface and decides that all classes should be selected.
 */
object AnyExtensionFilter : ExtensionFilter {
    override fun <T : Spec> decide(clazz: KClass<T>): Boolean = true
}

/**
 * Represents a list of extension filters with [AnyExtensionFilter].
 */
val ANY_EXTENSION_FILTERS = listOf(AnyExtensionFilter)
