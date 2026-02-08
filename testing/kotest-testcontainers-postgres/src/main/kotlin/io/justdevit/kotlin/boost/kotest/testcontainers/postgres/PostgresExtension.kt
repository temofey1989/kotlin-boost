package io.justdevit.kotlin.boost.kotest.testcontainers.postgres

import io.justdevit.kotlin.boost.kotest.AnnotationExtensionFilter
import io.justdevit.kotlin.boost.kotest.ExtensionFilter
import io.justdevit.kotlin.boost.kotest.testcontainers.TestcontainerExtension
import org.testcontainers.postgresql.PostgreSQLContainer

/**
 * The `PostgresExtension` class is an implementation of the `ExternalToolExtension` interface that represents an extension for running PostgreSQL containers.
 * It provides functionality to start and stop a PostgreSQL container as needed.
 */
class PostgresExtension(filters: Collection<ExtensionFilter> = emptyList()) :
    TestcontainerExtension<PostgreSQLContainer>(
        holder = PostgresHolder,
        filters = filters,
    ) {
    constructor(vararg filters: ExtensionFilter) : this(filters.toSet())
}

/**
 * Creates a [PostgresExtension] with the specified predicates for annotation [A].
 *
 * @param predicates The predicates used to filter the annotations.
 * @return The [PostgresExtension] object.
 */
inline fun <reified A : Annotation> PostgresExtension(vararg predicates: (A) -> Boolean): PostgresExtension {
    val filters = when {
        predicates.isEmpty() -> emptyList()
        else -> predicates.map { AnnotationExtensionFilter(A::class, it) }
    }
    return PostgresExtension(filters)
}

/**
 * Creates a [PostgresExtension] with the specified predicate for annotation [A].
 *
 * @param predicate The predicate used to filter the annotations.
 * @return The [PostgresExtension] object.
 */
inline fun <reified A : Annotation> PostgresExtension(noinline predicate: (A) -> Boolean) = PostgresExtension<A>(*arrayOf(predicate))
