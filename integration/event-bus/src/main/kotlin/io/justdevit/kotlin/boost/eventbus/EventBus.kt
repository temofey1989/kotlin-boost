package io.justdevit.kotlin.boost.eventbus

/**
 * EventBus is an interface that serves as a contract for an event bus system.
 */
interface EventBus :
    EventPublisher,
    AutoCloseable {

    override fun close() = Unit
}
