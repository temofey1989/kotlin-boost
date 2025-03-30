package io.justdevit.kotlin.boost.kotest

import io.kotest.core.config.AbstractProjectConfig
import io.kotest.core.extensions.Extension

/**
 * An abstract configuration class for a project to boost testing configuration.
 *
 * This class integrates a list of explicitly declared extensions with globally available Boost extensions.
 *
 * @constructor Creates a configuration with a list of explicit extensions.
 * @param explicitExtensions The list of explicitly declared [Extension] instances to be included in the configuration.
 */
abstract class BoostProjectConfig(val explicitExtensions: List<Extension>) : AbstractProjectConfig() {
    constructor(vararg explicitExtensions: Extension) : this(listOf(*explicitExtensions))

    override fun extensions() = (BOOST_EXTENSIONS + explicitExtensions).sortedByPrecedence()
}
