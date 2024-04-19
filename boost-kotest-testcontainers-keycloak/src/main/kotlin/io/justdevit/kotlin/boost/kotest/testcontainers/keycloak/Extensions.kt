package io.justdevit.kotlin.boost.kotest.testcontainers.keycloak

import io.justdevit.kotlin.boost.kotest.TEST_USER
import io.justdevit.kotlin.boost.serialization.json.JSON
import io.restassured.http.ContentType
import io.restassured.http.ContentType.URLENC
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
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

/**
 * Performs a login operation with the specified parameters.
 *
 * @param username The username to be used for authentication. Default value is `TEST_USER`.
 * @param realm The realm to be used for authentication.
 * If not specified, the first realm from the `KEYCLOAK_REALMS` system property will be used.
 * If the system property is not set or no realms are defined, the default realm is "test".
 * @param clientId The client ID to be used for authentication. If not specified, the `KEYCLOAK_DEFAULT_CLIENT_ID`
 * system property will be used. If the system property is not set, the default client is "test".
 *
 * @return An [AuthResource] object representing the authentication response.
 */
fun login(
    username: String = TEST_USER,
    realm: String = KEYCLOAK_DEFAULT_REALM,
    clientId: String = KEYCLOAK_DEFAULT_CLIENT_ID,
): AuthResource =
    Given(KEYCLOAK_SPEC) {
        auth().none()
    } When {
        contentType(URLENC)
        formParams(
            mapOf(
                "client_id" to listOf(clientId),
                "grant_type" to listOf("password"),
                "username" to listOf(username),
                "password" to listOf(username),
            ),
        )
        post("/realms/$realm/protocol/openid-connect/token")
    } Then {
        statusCode(200)
        contentType(ContentType.JSON)
    } Extract {
        JSON.decodeFromString(body().asString())
    }
