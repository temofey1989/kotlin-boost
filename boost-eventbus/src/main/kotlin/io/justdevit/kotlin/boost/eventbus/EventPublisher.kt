package io.justdevit.kotlin.boost.eventbus

/**
 * Interface representing an object capable of publishing events to an event bus system.
 */
interface EventPublisher {

    /**
     * Publishes one or more events to the event bus system.
     *
     * @param events Events to be published.
     */
    fun publish(vararg events: Event)

    /**
     * Publishes one or more events to the event bus system.
     *
     * @param events Events to be published.
     */
    suspend fun coPublish(vararg events: Event)
}
