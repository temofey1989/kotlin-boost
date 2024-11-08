package io.justdevit.kotlin.boost.eventbus

/**
 * Interface for registering and unregistering event listeners in an event bus system.
 */
interface EventListenerRegister {

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
