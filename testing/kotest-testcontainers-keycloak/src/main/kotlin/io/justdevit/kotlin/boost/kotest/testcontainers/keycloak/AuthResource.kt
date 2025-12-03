package io.justdevit.kotlin.boost.kotest.testcontainers.keycloak

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents the response received after Authentication.
 */
@Serializable
data class AuthResource(
    /**
     * The access token generated for the user.
     */
    @SerialName("access_token")
    val accessToken: String,
    /**
     * The expiration time in seconds for the access token.
     */
    @SerialName("expires_in")
    val expiresIn: Long,
    /**
     * The expiration time in seconds for the refresh token.
     */
    @SerialName("refresh_expires_in")
    val refreshExpiresIn: Long,
    /**
     * The refresh token generated for the user.
     */
    @SerialName("refresh_token")
    val refreshToken: String,
    /**
     * The type of the token.
     */
    @SerialName("token_type")
    val tokenType: String,
    /**
     * The session state.
     */
    @SerialName("session_state")
    val sessionState: String,
    /**
     * The scope of the token.
     */
    @SerialName("scope")
    val scope: String,
    /**
     * The not-before policy.
     */
    @SerialName("not-before-policy")
    val notBeforePolicy: Int,
) {
    /**
     * This variable represents the authorization header in the form of a string. It is obtained by concatenating the
     * `tokenType` and `accessToken` properties of the `AuthResponse` object.
     */
    val authorizationHeader: String
        get() = "$tokenType $accessToken"
}
