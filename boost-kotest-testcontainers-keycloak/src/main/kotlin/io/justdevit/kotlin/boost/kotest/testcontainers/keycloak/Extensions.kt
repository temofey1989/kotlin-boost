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
 * Logs in a user and returns the authentication response.
 *
 * @param username The username of the user. Defaults to TEST_USER if not provided.
 * @return The authentication response containing the access token, expiration time, refresh token, token type, session state, scope, and not-before policy.
 */
fun login(username: String = TEST_USER): AuthResource =
    Given(KEYCLOAK_SPEC) {
        auth().none()
    } When {
        contentType(URLENC)
        formParams(
            mapOf(
                "grant_type" to listOf("password"),
                "client_id" to listOf("connected-app"),
                "username" to listOf(username),
                "password" to listOf(username),
            ),
        )
        post("/realms/connected/protocol/openid-connect/token")
    } Then {
        statusCode(200)
        contentType(ContentType.JSON)
    } Extract {
        JSON.decodeFromString(body().asString())
    }
