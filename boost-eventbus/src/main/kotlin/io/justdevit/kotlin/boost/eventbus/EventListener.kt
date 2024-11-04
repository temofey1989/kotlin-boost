package io.justdevit.kotlin.boost.eventbus

/**
 * Interface representing an event listener that listens for specific types of events.
 *
 * @param T The type of event that this listener supports.
 */
interface EventListener<T : Event> {

    /**
     * Represents the class type of the supported event for this listener.
     */
    val supportedClass: Class<T>

    /**
     * Defines the priority of the event listener. Listeners with higher priority
     * values are processed after those with lower values. The default priority is `0`.
     */
    val priority: Int get() = 0

    /**
     * Handles an event of type T.
     *
     * @param event The event that is to be processed.
     */
    suspend fun onEvent(event: T)
}
