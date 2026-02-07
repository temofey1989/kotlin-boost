package io.justdevit.kotlin.boost.eventbus

import io.justdevit.kotlin.boost.logging.logger

/**
 * A concrete implementation of [ErrorHandler] that logs errors encountered during event processing.
 *
 * This implementation ensures that the error is captured and properly logged for debugging or monitoring
 * purposes without interfering with the event processing lifecycle.
 */
object LoggingErrorHandler : ErrorHandler<Event> {

    private val log = logger(LoggingErrorHandler::class.java)

    override val supportedClass: Class<Event> = Event::class.java

    override val priority: Int = Int.MIN_VALUE

    override suspend fun handle(error: Throwable, context: ErrorContext<Event>) {
        log.error(error) {
            "Listener [${context.listener::class.qualifiedName}] while processing event: ${context.event}"
        }
    }
}
