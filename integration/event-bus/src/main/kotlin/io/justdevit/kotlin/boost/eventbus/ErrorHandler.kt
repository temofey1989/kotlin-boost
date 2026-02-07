package io.justdevit.kotlin.boost.eventbus

/**
 * Interface representing a handler for processing errors that occur during event handling.
 *
 * @param T The type of event handled by this error handler, which must extend [Event].
 */
interface ErrorHandler<T : Event> {

    /**
     * The class type of the events supported by this error handler.
     *
     * @param T The type of event handled by this error handler, which extends [Event].
     */
    val supportedClass: Class<T>

    /**
     * Defines the priority of the error handler. Handlers with higher
     * values are processed after those with lower values. The default priority is `0`.
     */
    val priority: Int get() = 0

    /**
     * Handles an error that occurred during the processing of an event.
     *
     * @param error The exception that occurred and triggered this handler.
     * @param context The context in which the error occurred, including
     *                details about the event being processed and the listener
     *                handling the event at the time of the error.
     */
    suspend fun handle(error: Throwable, context: ErrorContext<T>)
}
