package io.justdevit.kotlin.boost.eventbus

import io.justdevit.kotlin.boost.logging.Logging
import kotlinx.coroutines.runBlocking

/**
 * A simple implementation of the [EventBus] interface, responsible for managing the lifecycle
 * of events and their listeners in the system. It facilitates the publishing of events
 * to registered listeners, allowing for processing and event chaining.
 *
 * @constructor Creates an instance of [SimpleEventBus], optionally with a list of predefined event listeners.
 * @param listeners A list of initial event listeners to register with the event bus. Default is an empty list.
 */
open class SimpleEventBus(listeners: List<EventListener<*>> = emptyList()) : EventBus {

    private val listeners: MutableList<EventListener<*>> = listeners.toMutableList()

    companion object : Logging()

    override fun publish(vararg events: Event) {
        runBlocking {
            publishAll(events.toList())
        }
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
            .filter { it.supportedClass.isInstance(event) }
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
