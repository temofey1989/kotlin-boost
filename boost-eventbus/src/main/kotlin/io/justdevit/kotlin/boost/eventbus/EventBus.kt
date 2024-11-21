package io.justdevit.kotlin.boost.eventbus

import io.justdevit.kotlin.boost.logging.Logging
import kotlinx.coroutines.runBlocking

/**
 * EventBus is an interface that serves as a contract for an event bus system, combining event publishing
 * capabilities and event listener management functionality.
 */
interface EventBus :
    EventPublisher,
    EventListenerRegister

open class DefaultEventBus(listeners: List<EventListener<*>> = emptyList()) : EventBus {

    private val listeners: MutableList<EventListener<*>> = listeners.toMutableList()

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
