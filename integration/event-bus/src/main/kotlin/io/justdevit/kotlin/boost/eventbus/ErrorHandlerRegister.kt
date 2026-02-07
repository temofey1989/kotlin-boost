package io.justdevit.kotlin.boost.eventbus

/**
 * Interface for registering and unregistering error handlers in an event bus system.
 */
interface ErrorHandlerRegister {

    /**
     * Registers one or more error handlers to the event bus system.
     *
     * @param handlers Handlers to be registered.
     */
    fun register(vararg handlers: ErrorHandler<*>)

    /**
     * Unregisters one or more error handlers from the event bus system.
     *
     * @param handlers Handlers to be unregistered.
     */
    fun unregister(vararg handlers: ErrorHandler<*>)
}
