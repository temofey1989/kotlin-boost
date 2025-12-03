package io.justdevit.kotlin.boost.kotest.mockk

import io.justdevit.kotlin.boost.kotest.SpecInstallation
import io.mockk.clearAllMocks

/**
 * Applies clearing of all mocks for the current spec.
 *
 * Usage:
 * ```
 * class MyTest : FreeSpec({
 *
 *     install {
 *         mockk()
 *     }
 *
 *     ...
 * })
 * ```
 */
fun SpecInstallation.mockk(
    answers: Boolean = true,
    recordedCalls: Boolean = true,
    childMocks: Boolean = true,
    regularMocks: Boolean = true,
    objectMocks: Boolean = true,
    staticMocks: Boolean = true,
    constructorMocks: Boolean = true,
) {
    spec.beforeEach {
        clearAllMocks(
            answers = answers,
            recordedCalls = recordedCalls,
            childMocks = childMocks,
            regularMocks = regularMocks,
            objectMocks = objectMocks,
            staticMocks = staticMocks,
            constructorMocks = constructorMocks,
        )
    }
}
