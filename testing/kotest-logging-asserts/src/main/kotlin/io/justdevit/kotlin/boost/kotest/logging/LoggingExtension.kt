package io.justdevit.kotlin.boost.kotest.logging

import io.kotest.core.extensions.MountableExtension
import io.kotest.core.listeners.AfterEachListener
import io.kotest.core.listeners.BeforeEachListener
import io.kotest.core.test.TestCase
import io.kotest.engine.test.TestResult
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.text.Charsets.UTF_8

/**
 * A Kotest extension that captures and observes standard output and error streams during test execution.
 */
object LoggingExtension : MountableExtension<Unit, Unit>, BeforeEachListener, AfterEachListener {

    private val defaultOutStream = System.out
    private val defaultErrStream = System.err
    private val observationStream = ByteArrayOutputStream()
    private val outStream = PrintStream(BroadcastingOutputStream(defaultOutStream, observationStream))
    private val errStream = PrintStream(BroadcastingOutputStream(defaultErrStream, observationStream))

    /**
     * A list of log records observed during test execution.
     */
    val observedRecords: List<LogRecord>
        get() = observationStream
            .toString(UTF_8)
            .lines()
            .filter { it.isNotBlank() }
            .mapIndexed { index, line -> LogRecord(index, line) }

    override fun mount(configure: Unit.() -> Unit) = Unit

    override suspend fun beforeEach(testCase: TestCase) {
        observationStream.reset()
        System.setOut(outStream)
        System.setErr(errStream)
    }

    override suspend fun afterEach(testCase: TestCase, result: TestResult) {
        System.setOut(defaultOutStream)
        System.setErr(defaultErrStream)
    }
}
