package io.justdevit.kotlin.boost.kotest

import io.kotest.core.extensions.MountableExtension
import io.kotest.core.extensions.install
import io.kotest.core.spec.Spec

/**
 * Configures and installs extensions into the specified [Spec].
 *
 * @param configure A lambda function to configure the [SpecInstallation] instance associated with the [Spec].
 */
fun Spec.install(configure: SpecInstallation.() -> Unit) {
    val installer = SpecInstallation(this)
    installer.configure()
}

class SpecInstallation internal constructor(val spec: Spec) {

    /**
     * Installs the specified Mountable Extension for the current testing specification.
     *
     * @param T The type of the configuration object associated with the extension.
     * @param U The type of the extension object.
     * @param extension The [MountableExtension] to be installed.
     * @param configure A lambda function to configure the extension. Defaults to an empty configuration.
     */
    fun <T, U> install(extension: MountableExtension<T, U>, configure: T.() -> Unit = {}) = spec.install(extension, configure)

    /**
     * Iterates through all Boost extensions of type [MountableExtension] from the global [BOOST_EXTENSIONS] list
     * and installs each one into the current testing specification.
     *
     * The [BOOST_EXTENSIONS] list is dynamically populated using the service loader mechanism,
     * allowing integration of extensions seamlessly at runtime.
     */
    fun boostExtensions() {
        BOOST_EXTENSIONS
            .filterIsInstance<MountableExtension<*, *>>()
            .sortedByPrecedence()
            .forEach { install(it) }
    }
}
