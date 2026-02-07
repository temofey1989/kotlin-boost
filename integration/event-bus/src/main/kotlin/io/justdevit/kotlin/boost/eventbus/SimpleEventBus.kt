package io.justdevit.kotlin.boost.eventbus

import kotlinx.coroutines.runBlocking

/**
 * A simple implementation of the [EventBus] interface, responsible for managing the lifecycle
 * of events and their listeners in the system. It facilitates the publishing of events
 * to registered listeners, allowing for processing and event chaining.
 *
 * @param listeners A list of initial event listeners to register with the event bus. Default is an empty list.
 * @param errorHandlers A list of initial error handlers to register with the event bus. Default is a list containing [LoggingErrorHandler].
 */
open class SimpleEventBus(listeners: List<EventListener<*>> = emptyList(), errorHandlers: List<ErrorHandler<*>> = listOf(LoggingErrorHandler)) :
    EventBus,
    EventListenerRegister,
    ErrorHandlerRegister {

    private val listeners: MutableList<EventListener<*>> = listeners.sortedBy { it.priority }.toMutableList()
    private val errorHandlers: MutableList<ErrorHandler<*>> = errorHandlers.sortedBy { it.priority }.toMutableList()

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
        this.listeners.sortBy { it.priority }
    }

    override fun unregister(vararg listeners: EventListener<*>) {
        if (listeners.isEmpty()) {
            return
        }
        this.listeners.removeAll(listeners.toSet())
    }

    override fun register(vararg handlers: ErrorHandler<*>) {
        if (errorHandlers.isEmpty()) {
            return
        }
        val distinct = handlers.toSet() - this.errorHandlers.toSet()
        this.errorHandlers.addAll(distinct)
        this.errorHandlers.sortBy { it.priority }
    }

    override fun unregister(vararg handlers: ErrorHandler<*>) {
        if (handlers.isEmpty()) {
            return
        }
        this.errorHandlers.removeAll(handlers.toSet())
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
        val listenersToApply = listeners.filter { it.supportedClass.isInstance(event) }
        listenersToApply.forEach {
            if (event.terminated) {
                return@forEach
            }
            it as EventListener<Event>
            try {
                it.onEvent(event)
            } catch (throwable: Throwable) {
                it.handlerThrowable(event, throwable)
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    private suspend fun EventListener<Event>.handlerThrowable(event: Event, error: Throwable) {
        if (errorHandlers.isEmpty()) {
            return
        }
        val context = ErrorContext(event = event, listener = this)
        errorHandlers
            .filter { it.supportedClass.isInstance(event) }
            .forEach {
                try {
                    (it as ErrorHandler<Event>).handle(error, context)
                } catch (throwable: Throwable) {
                    LoggingErrorHandler.handle(error = throwable, context)
                }
            }
    }
}
