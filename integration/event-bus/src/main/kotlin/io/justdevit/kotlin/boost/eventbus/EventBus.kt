package io.justdevit.kotlin.boost.eventbus

/**
 * EventBus is an interface that serves as a contract for an event bus system, combining event publishing
 * capabilities and event listener management functionality.
 */
interface EventBus :
    EventPublisher,
    EventListenerRegister,
    AutoCloseable {

    override fun close() = Unit
}
