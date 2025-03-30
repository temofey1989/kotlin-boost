package io.justdevit.kotlin.boost.kotest

import io.kotest.core.extensions.Extension
import java.util.ServiceLoader

/**
 * Represents a test username.
 */
const val TEST_USER = "peter.tester"

val BOOST_EXTENSIONS: List<Extension> by lazy {
    ServiceLoader.load(Extension::class.java).toList()
}
