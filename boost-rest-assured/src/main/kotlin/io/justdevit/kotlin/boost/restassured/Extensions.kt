package io.justdevit.kotlin.boost.restassured

import io.restassured.response.ExtractableResponse
import io.restassured.response.Response

/**
 * Extracts the body of the response and returns it as the specified type [T].
 *
 * @return The body of the response as type [T].
 */
@Suppress("EXTENSION_SHADOWED_BY_MEMBER")
inline fun <reified T> ExtractableResponse<Response>.body(): T = body().`as`(T::class.java)
