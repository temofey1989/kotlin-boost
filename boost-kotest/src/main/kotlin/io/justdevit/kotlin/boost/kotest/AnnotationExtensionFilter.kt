package io.justdevit.kotlin.boost.kotest

import io.kotest.core.spec.Spec
import kotlin.reflect.KClass

/**
 * A filter that selects classes based on the presence of a specific annotation [A]
 * and a predicate that further filters based on the annotation's properties.
 *
 * @param A The type of the annotation.
 * @property annotationClass The [KClass] of the annotation.
 * @property predicate The predicate to further filter based on the annotation's properties. Default is `true` for no filtering.
 */
class AnnotationExtensionFilter<A : Annotation>(private val annotationClass: KClass<A>, private val predicate: (A) -> Boolean = { true }) : ExtensionFilter {

    @Suppress("UNCHECKED_CAST")
    override fun <T : Spec> decide(clazz: KClass<T>): Boolean {
        val annotation = clazz
            .annotations
            .firstOrNull { it.annotationClass == annotationClass } as? A
            ?: return false
        return predicate(annotation)
    }
}
