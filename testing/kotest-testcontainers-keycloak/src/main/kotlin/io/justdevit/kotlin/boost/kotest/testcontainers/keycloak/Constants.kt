package io.justdevit.kotlin.boost.kotest.testcontainers.keycloak

import io.restassured.specification.RequestSpecification

const val KEYCLOAK_BASE_URL_PROPERTY: String = "KEYCLOAK_BASE_URL"
const val KEYCLOAK_REALMS_PROPERTY: String = "KEYCLOAK_REALMS"
const val KEYCLOAK_ISSUER_PROPERTY: String = "KEYCLOAK_ISSUER"
const val KEYCLOAK_DEFAULT_REALM_PROPERTY: String = "KEYCLOAK_DEFAULT_REALM"
const val KEYCLOAK_DEFAULT_CLIENT_ID_PROPERTY: String = "KEYCLOAK_DEFAULT_CLIENT_ID"

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
 *             "client_id" to listOf("my-app"),
 *             "username" to listOf(username),
 *             "password" to listOf(username),
 *         )
 *     )
 *     post("/realms/my-realm/protocol/openid-connect/token")
 * } Then {
 *     statusCode(200)
 *     contentType(ContentType.JSON)
 * } Extract {
 *     JSON.decodeFromString(body().asString())
 * }
 */
val KEYCLOAK_SPEC: RequestSpecification by lazy {
    io.restassured.module.kotlin.extensions.Given {
        baseUri(System.getProperty(KEYCLOAK_BASE_URL_PROPERTY))
    }
}

/**
 * Represents the list of Keycloak realms.
 * This variable lazily retrieves all the realms from the Keycloak admin client and maps them to a list of realm names.
 */
val KEYCLOAK_REALMS: List<String>
    get() = KeycloakHolder.tool
        .keycloakAdminClient
        .realms()
        .findAll()
        .map { it.realm }

/**
 * Default realm for Keycloak authentication.
 *
 * The value is determined by the system property `KEYCLOAK_DEFAULT_REALM_PROPERTY`.
 * If the system property value matches any of the realms specified in `KEYCLOAK_REALMS`,
 * that realm is used as the default. Otherwise, the value is set to "test" by default.
 *
 * @see KEYCLOAK_DEFAULT_REALM_PROPERTY
 * @see KEYCLOAK_REALMS
 */
val KEYCLOAK_DEFAULT_REALM: String
    get() {
        val defaultName = System.getProperty(KEYCLOAK_DEFAULT_REALM_PROPERTY)
        return KEYCLOAK_REALMS.firstOrNull { defaultName == it } ?: "test"
    }

/**
 * The default client ID used for authentication.
 *
 * The value of this variable is fetched from the system property `KEYCLOAK_DEFAULT_CLIENT_ID`.
 * If the system property is not set, the default value is "test".
 */
val KEYCLOAK_DEFAULT_CLIENT_ID: String by lazy {
    System.getProperty(KEYCLOAK_DEFAULT_CLIENT_ID_PROPERTY) ?: "test"
}
