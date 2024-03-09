package io.justdevit.kotlin.boost.kotest.testcontainers.keycloak

import io.restassured.specification.RequestSpecification

/**
 * Represents the specification for making requests to the Keycloak API.
 *
 * The `KEYCLOAK_SPEC` variable is a lazily initialized `RequestSpecification` object
 * provided by the RestAssured library for building RESTful API requests.
 *
 * It is configured with a base URI obtained from the system property "KEYCLOAK_BASE_URL".
 *
 * Example usage:
 * ```
 * Given(KEYCLOAK_SPEC) {
 *     auth().none()
 * } When {
 *     contentType(URLENC)
 *     formParams(
 *         mapOf(
 *             "grant_type" to listOf("password"),
 *             "client_id" to listOf("connected-app"),
 *             "username" to listOf(username),
 *             "password" to listOf(username),
 *         )
 *     )
 *     post("/realms/myrealm/protocol/openid-connect/token")
 * } Then {
 *     statusCode(200)
 *     contentType(ContentType.JSON)
 * } Extract {
 *     JSON.decodeFromString(body().asString())
 * }
 */
val KEYCLOAK_SPEC: RequestSpecification by lazy {
    io.restassured.module.kotlin.extensions.Given {
        baseUri(System.getProperty("KEYCLOAK_BASE_URL"))
    }
}
