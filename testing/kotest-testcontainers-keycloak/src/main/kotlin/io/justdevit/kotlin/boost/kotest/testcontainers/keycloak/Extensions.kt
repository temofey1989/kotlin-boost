package io.justdevit.kotlin.boost.kotest.testcontainers.keycloak

import io.restassured.specification.RequestSpecification

/**
 * Executes a REST API request using the given request specification and configuration block.
 *
 * @param spec The request specification to be used for the API request.
 * @param block The configuration block that defines the request specifications.
 *
 * @return The request specification after applying the configuration block.
 */
@Suppress("FunctionName")
fun Given(spec: RequestSpecification, block: RequestSpecification.() -> RequestSpecification) =
    io.restassured.module.kotlin.extensions.Given {
        spec(spec).run(block)
    }
