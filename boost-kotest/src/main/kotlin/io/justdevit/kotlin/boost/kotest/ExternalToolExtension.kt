package io.justdevit.kotlin.boost.kotest

import io.kotest.core.extensions.ConstructorExtension
import io.kotest.core.extensions.MountableExtension
import io.kotest.core.listeners.AfterProjectListener

/**
 * Represents an extension for an external tool.
 */
interface ExternalToolExtension<CONFIG, MATERIALIZED> :
    ConstructorExtension,
    AfterProjectListener,
    MountableExtension<CONFIG, MATERIALIZED>
