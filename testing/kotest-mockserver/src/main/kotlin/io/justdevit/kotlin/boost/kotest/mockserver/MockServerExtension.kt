package io.justdevit.kotlin.boost.kotest.mockserver

import io.justdevit.kotlin.boost.kotest.AnnotationExtensionFilter
import io.justdevit.kotlin.boost.kotest.ExtensionFilter
import io.justdevit.kotlin.boost.kotest.ExternalToolExtension
import org.mockserver.integration.ClientAndServer

/**
 * Represents a Mock Server extension for an external tool.
 * Implements the [ExternalToolExtension] interface.
 */
class MockServerExtension(filters: Collection<ExtensionFilter> = emptySet()) :
    ExternalToolExtension<ClientAndServer>(
        holder = MockServerHolder,
        filters = filters,
    ) {

    constructor(vararg filters: ExtensionFilter) : this(filters.toSet())

    constructor() : this(emptySet())
}

/**
 * Creates a [MockServerExtension] with the specified predicates for annotation [A].
 *
 * @param predicates The predicates used to filter the annotations.
 * @return The [MockServerExtension] object.
 */
inline fun <reified A : Annotation> MockServerExtension(vararg predicates: (A) -> Boolean): MockServerExtension {
    val filters = when {
        predicates.isEmpty() -> emptySet()
        else -> predicates.map { AnnotationExtensionFilter(A::class, it) }
    }
    return MockServerExtension(filters)
}

/**
 * Creates a [MockServerExtension] with the specified predicate for annotation [A].
 *
 * @param predicate The predicate used to filter the annotations.
 * @return The [MockServerExtension] object.
 */
inline fun <reified A : Annotation> MockServerExtension(noinline predicate: (A) -> Boolean) = MockServerExtension<A>(*arrayOf(predicate))
