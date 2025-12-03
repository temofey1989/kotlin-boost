package io.justdevit.kotlin.boost.eventbus

import java.io.Serializable

/**
 * Abstract class representing an event that can be managed within an event-bus system.
 *
 * This class allows for registering subsequent events to create a chain or sequence of events,
 * and includes functionality to indicate if an event has been terminated.
 */
abstract class Event : Serializable {

    @Transient
    private val _nextEvents: MutableList<Event> = mutableListOf()
    val nextEvents: List<Event>
        get() = _nextEvents.toList()

    @Transient
    private var _terminated: Boolean = false
    val terminated: Boolean
        get() = _terminated

    /**
     * Marks the event as terminated.
     */
    @Suppress("MemberVisibilityCanBePrivate")
    fun terminate() {
        _terminated = true
    }

    /**
     * Registers a subsequent event to follow the current event.
     *
     * @param event The event to be registered as the next in sequence.
     */
    @Suppress("MemberVisibilityCanBePrivate")
    fun registerNext(event: Event) {
        if (event == this) {
            throw IllegalArgumentException("Registering event to itself is not allowed.")
        }
        _nextEvents += event
    }

    /**
     * Registers a subsequent event and then marks the current event as terminated.
     *
     * @param event The event to be registered as the next in sequence.
     */
    fun registerNextAndTerminate(event: Event) {
        registerNext(event)
        terminate()
    }
}
