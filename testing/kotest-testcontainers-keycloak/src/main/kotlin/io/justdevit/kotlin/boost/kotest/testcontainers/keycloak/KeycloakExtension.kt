package io.justdevit.kotlin.boost.kotest.testcontainers.keycloak

import dasniko.testcontainers.keycloak.KeycloakContainer
import io.justdevit.kotlin.boost.kotest.AnnotationExtensionFilter
import io.justdevit.kotlin.boost.kotest.ExtensionFilter
import io.justdevit.kotlin.boost.kotest.testcontainers.TestcontainerExtension

/**
 * Represents an extension that integrates Keycloak with the Kotest framework.
 *
 * The [KeycloakExtension] allows the Keycloak container to be started and managed during testing.
 *
 * @constructor Creates a [KeycloakExtension] with a given collection of filters for specifying the test specifications to which the extension applies.
 * @param filters A collection of [ExtensionFilter] instances used to determine the applicability of the extension to specific test specifications.
 */
class KeycloakExtension(filters: Collection<ExtensionFilter> = emptyList()) :
    TestcontainerExtension<KeycloakContainer>(
        holder = KeycloakHolder,
        filters = filters,
    ) {

    constructor(vararg filters: ExtensionFilter) : this(filters.toSet())
    constructor() : this(emptyList())
}

/**
 * Creates a [KeycloakExtension] with the specified predicates for annotation [A].
 *
 * @param predicates The predicates used to filter the annotations.
 * @return The [KeycloakExtension] object.
 */
inline fun <reified A : Annotation> KeycloakExtension(vararg predicates: (A) -> Boolean): KeycloakExtension {
    val filters = when {
        predicates.isEmpty() -> emptyList()
        else -> predicates.map { AnnotationExtensionFilter(A::class, it) }
    }
    return KeycloakExtension(filters)
}

/**
 * Creates a [KeycloakExtension] with the specified predicate for annotation [A].
 *
 * @param predicate The predicate used to filter the annotations.
 * @return The [KeycloakExtension] object.
 */
inline fun <reified A : Annotation> KeycloakExtension(noinline predicate: (A) -> Boolean) = KeycloakExtension<A>(*arrayOf(predicate))
