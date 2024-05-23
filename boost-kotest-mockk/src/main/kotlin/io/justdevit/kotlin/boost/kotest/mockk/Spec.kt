package io.justdevit.kotlin.boost.kotest.mockk

import io.kotest.core.spec.Spec
import io.mockk.clearAllMocks

/**
 * Applies clearing of all mocks for the current spec.
 *
 * Usage:
 * ```
 * class MyTest : FreeSpec({
 *
 *     installMockkClearing()
 *
 *     ...
 * })
 * ```
 */
fun Spec.installMockkClearing(
    answers: Boolean = true,
    recordedCalls: Boolean = true,
    childMocks: Boolean = true,
    regularMocks: Boolean = true,
    objectMocks: Boolean = true,
    staticMocks: Boolean = true,
    constructorMocks: Boolean = true,
) {
    beforeEach {
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
