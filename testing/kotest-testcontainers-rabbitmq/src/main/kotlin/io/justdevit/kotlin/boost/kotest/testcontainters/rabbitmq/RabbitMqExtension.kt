package io.justdevit.kotlin.boost.kotest.testcontainters.rabbitmq

import io.justdevit.kotlin.boost.kotest.AnnotationExtensionFilter
import io.justdevit.kotlin.boost.kotest.ExtensionFilter
import io.justdevit.kotlin.boost.kotest.ExternalToolExtension
import org.testcontainers.rabbitmq.RabbitMQContainer

/**
 * The `RabbitMqExtension` class is an implementation of the `ExternalToolExtension` interface that represents an extension for running RabbitMQ containers.
 * It provides functionality to start and stop a RabbitMQ container as needed.
 */
class RabbitMqExtension(filters: Collection<ExtensionFilter> = emptyList()) :
    ExternalToolExtension<RabbitMQContainer>(
        holder = RabbitMqHolder,
        filters = filters,
    ) {

    constructor(vararg filters: ExtensionFilter) : this(filters.toSet())
    constructor() : this(emptyList())
}

/**
 * Creates a [RabbitMqExtension] with the specified predicates for annotation [A].
 *
 * @param predicates The predicates used to filter the annotations.
 * @return The [RabbitMqExtension] object.
 */
inline fun <reified A : Annotation> RabbitMqExtension(vararg predicates: (A) -> Boolean): RabbitMqExtension {
    val filters = when {
        predicates.isEmpty() -> emptyList()
        else -> predicates.map { AnnotationExtensionFilter(A::class, it) }
    }
    return RabbitMqExtension(filters)
}

/**
 * Creates a [RabbitMqExtension] with the specified predicate for annotation [A].
 *
 * @param predicate The predicate used to filter the annotations.
 * @return The [RabbitMqExtension] object.
 */
inline fun <reified A : Annotation> RabbitMqExtension(noinline predicate: (A) -> Boolean) = RabbitMqExtension<A>(*arrayOf(predicate))
