package io.justdevit.kotlin.boost.kotest.mockk

import io.kotest.core.listeners.BeforeEachListener
import io.kotest.core.test.TestCase
import io.mockk.clearAllMocks

/**
 * MockkExtension is an object that extends the [BeforeEachListener] interface.
 * It provides a single method, `beforeEach`, which is called before each test case
 * is executed.
 *
 * The purpose of this class is to clear all mocks using the `clearAllMocks()`
 * function before each test case, ensuring a clean state for mocking.
 */
object MockkExtension : BeforeEachListener {

    override suspend fun beforeEach(testCase: TestCase) {
        clearAllMocks()
    }
}
