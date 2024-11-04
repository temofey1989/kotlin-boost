package io.justdevit.kotlin.boost.eventbus

import io.justdevit.kotlin.boost.logging.Logging
import kotlinx.coroutines.runBlocking

/**
 * Represents a system that allows for publishing and listening to events.
 *
 * Implementations of this interface can publish events to listeners
 * and register or unregister listeners dynamically.
 */
interface EventBus {

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

    /**
     * Registers one or more event listeners to the event bus system.
     *
     * @param listeners Listeners to be registered.
     */
    fun register(vararg listeners: EventListener<*>)

    /**
     * Unregisters one or more event listeners from the event bus system.
     *
     * @param listeners Listeners to be unregistered.
     */
    fun unregister(vararg listeners: EventListener<*>)
}

open class DefaultEventBus(private val listeners: MutableList<EventListener<*>> = mutableListOf()) : EventBus {

    companion object : Logging()

    override fun publish(vararg events: Event) {
        runBlocking {
            publishAll(events.toList())
        }
    }

    override suspend fun coPublish(vararg events: Event) {
        publishAll(events.toList())
    }

    override fun register(vararg listeners: EventListener<*>) {
        if (listeners.isEmpty()) {
            return
        }
        val distinct = listeners.toSet() - this.listeners.toSet()
        this.listeners.addAll(distinct)
    }

    override fun unregister(vararg listeners: EventListener<*>) {
        if (listeners.isEmpty()) {
            return
        }
        this.listeners.removeAll(listeners.toSet())
    }

    private suspend fun publishAll(events: List<Event>, executedEvents: MutableSet<Event> = mutableSetOf()) {
        val nextEvents = buildList {
            events.forEach {
                if (!it.terminated) {
                    process(it)
                    executedEvents += it
                    addAll(it.nextEvents)
                }
            }
            removeAll(events.toSet())
            removeAll(executedEvents)
        }
        if (nextEvents.isNotEmpty()) {
            publishAll(events = nextEvents, executedEvents = executedEvents)
        }
    }

    @Suppress("UNCHECKED_CAST")
    private suspend fun process(event: Event) {
        if (event.terminated) {
            return
        }
        val listenersToApply = listeners
            .filter { it.supportedClass == event::class.java }
            .sortedBy { it.priority }
        listenersToApply.forEach {
            if (event.terminated) {
                return@forEach
            }
            try {
                (it as EventListener<Event>).onEvent(event)
            } catch (throwable: Throwable) {
                log.error(throwable) { "Listener [${it::class.qualifiedName}] while processing event: $event" }
            }
        }
    }
}
