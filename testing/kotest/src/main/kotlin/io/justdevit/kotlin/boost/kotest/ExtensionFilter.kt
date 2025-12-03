package io.justdevit.kotlin.boost.kotest

import io.kotest.core.spec.Spec
import kotlin.reflect.KClass

/**
 * Represents an Extension Filter for deciding whether a class should be selected or not.
 */
interface ExtensionFilter {

    /**
     * Determines whether a class should be selected or not based on the provided filter.
     *
     * @param clazz The class of type [Spec] to be evaluated.
     * @return `true` if the class should be selected, `false` otherwise.
     */
    fun <T : Spec> decide(clazz: KClass<T>): Boolean
}
