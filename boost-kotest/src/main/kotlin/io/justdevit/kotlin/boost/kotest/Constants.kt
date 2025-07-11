package io.justdevit.kotlin.boost.kotest

import io.kotest.core.extensions.Extension
import java.util.ServiceLoader

/**
 * Represents a test username.
 */
const val TEST_USER = "peter.tester"

/**
 * A global list of Boost extensions, dynamically loaded via the [ServiceLoader] mechanism.
 */
val BOOST_EXTENSIONS: List<Extension> by lazy {
    ServiceLoader.load(Extension::class.java).toList()
}
