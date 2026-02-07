package io.justdevit.kotlin.boost.eventbus

/**
 * Represents the context of an error that occurred during event processing.
 *
 * @param T The type of event being processed, which is a subclass of [Event].
 * @property event The event that was being handled when the error occurred.
 * @property listener The listener that was processing the event at the time of the error.
 */
data class ErrorContext<T : Event>(val event: T, val listener: EventListener<T>)
