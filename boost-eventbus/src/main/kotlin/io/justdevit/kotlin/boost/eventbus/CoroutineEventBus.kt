package io.justdevit.kotlin.boost.eventbus

/**
 * Interface representing an event bus supporting coroutine-based event publishing and subscription.
 *
 * This interface combines functionality for publishing events, subscribing to event streams,
 * and managing the event-bus lifecycle.
 */
interface CoroutineEventBus :
    CoroutineEventPublisher,
    EventBusSubscriber,
    AutoCloseable {

    override fun close() = Unit
}
