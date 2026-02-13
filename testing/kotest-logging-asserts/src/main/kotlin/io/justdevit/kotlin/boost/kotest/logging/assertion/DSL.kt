package io.justdevit.kotlin.boost.kotest.logging.assertion

import io.justdevit.kotlin.boost.kotest.logging.LoggingExtension
import io.kotest.core.test.TestScope
import kotlin.DeprecationLevel.ERROR

/**
 * Marker annotation for defining the DSL scope of logging assertions.
 */
@DslMarker
annotation class LoggingAssertionsScope

/**
 * Asserts that the logs generated during the test execution meet the specified criteria.
 *
 * @param ordered Determines whether the log assertions should respect the order of their appearance.
 *                If `true`, the logs must appear in the specified order. Otherwise, order is not considered.
 *                Defaults to `false`.
 * @param configure A lambda used to define the log assertions using the [LogAssertExecutorBuilder].
 */
@Suppress("UnusedReceiverParameter")
fun TestScope.assertLogs(ordered: Boolean = false, configure: LogAssertExecutorBuilder.() -> Unit) {
    val builder = LogAssertExecutorBuilder(ordered)
    builder.configure()
    val executor = builder.build()
    executor.execute(LoggingExtension.observedRecords)
}

/**
 * Asserts that the `assertLogs` block is not occurring within another `assertLogs` block.
 *
 * @param configure A lambda expression to configure the nested [LogAssertExecutorBuilder].
 * @return Throws an error indicating that nested calls to `assertLogs` are not permitted.
 */
@Suppress("UnusedReceiverParameter")
@Deprecated("Nested assertLogs is not allowed!", level = ERROR)
fun LogAssertExecutorBuilder.assertLogs(
    @Suppress("unused") configure: LogAssertExecutorBuilder.() -> Unit,
): Nothing = kotlin.error("Nested assertLogs is not allowed!")
