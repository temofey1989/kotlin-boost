package io.justdevit.kotlin.boost.kotest.testcontainters.toxiproxy

import io.justdevit.kotlin.boost.kotest.AnnotationExtensionFilter
import io.justdevit.kotlin.boost.kotest.ExtensionFilter
import io.justdevit.kotlin.boost.kotest.ExternalToolExtension
import io.justdevit.kotlin.boost.kotest.testcontainers.TestcontainerExtension
import org.testcontainers.toxiproxy.ToxiproxyContainer

/**
 * The [ToxiproxyExtension] class is an implementation of the [ExternalToolExtension] interface that represents an extension for running Toxiproxy containers.
 * It provides functionality to start and stop a Toxiproxy container as needed.
 */
class ToxiproxyExtension(filters: Collection<ExtensionFilter> = emptyList()) :
    TestcontainerExtension<ToxiproxyContainer>(
        holder = ToxiproxyHolder,
        filters = filters,
    ) {

    constructor(vararg filters: ExtensionFilter) : this(filters.toSet())
}

/**
 * Creates a [ToxiproxyExtension] with the specified predicates for annotation [A].
 *
 * @param predicates The predicates used to filter the annotations.
 * @return The [ToxiproxyExtension] object.
 */
inline fun <reified A : Annotation> ToxiproxyExtension(vararg predicates: (A) -> Boolean): ToxiproxyExtension {
    val filters = when {
        predicates.isEmpty() -> emptyList()
        else -> predicates.map { AnnotationExtensionFilter(A::class, it) }
    }
    return ToxiproxyExtension(filters)
}

/**
 * Creates a [ToxiproxyExtension] with the specified predicate for annotation [A].
 *
 * @param predicate The predicate used to filter the annotations.
 * @return The [ToxiproxyExtension] object.
 */
inline fun <reified A : Annotation> ToxiproxyExtension(noinline predicate: (A) -> Boolean) = ToxiproxyExtension<A>(*arrayOf(predicate))
