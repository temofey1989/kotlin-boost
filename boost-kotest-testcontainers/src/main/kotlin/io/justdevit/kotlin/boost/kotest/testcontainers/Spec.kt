package io.justdevit.kotlin.boost.kotest.testcontainers

import io.kotest.core.extensions.install
import io.kotest.core.spec.Spec

/**
 * Installs a stubbing extension for the current specification based on a predicate.
 *
 * @param predicate A function that determines whether the stubbing should occur or not.
 */
fun Spec.installStubbing(predicate: () -> Boolean) = install(StubbingExtension(predicate))
