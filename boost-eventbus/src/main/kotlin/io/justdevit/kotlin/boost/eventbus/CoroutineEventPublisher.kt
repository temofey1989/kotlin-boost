package io.justdevit.kotlin.boost.eventbus

/**
 * Interface representing a coroutine-based event publisher.
 *
 * This interface provides a mechanism to publish events using coroutines.
 */
interface CoroutineEventPublisher : AutoCloseable {

    /**
     * Publishes one or more events to the event-bus system.
     *
     * @param events The events to be published.
     */
    suspend fun publish(vararg events: Event)
}
