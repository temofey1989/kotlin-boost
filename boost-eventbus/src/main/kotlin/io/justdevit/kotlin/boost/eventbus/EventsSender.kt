package io.justdevit.kotlin.boost.eventbus

/**
 * Interface representing a coroutine-based event sender.
 *
 * This interface provides a mechanism to send events inside coroutine scope.
 */
interface EventsSender : AutoCloseable {

    /**
     * Sends one or more events to the event to the stream.
     *
     * @param events The events to be sent.
     */
    suspend fun send(vararg events: Event)
}
