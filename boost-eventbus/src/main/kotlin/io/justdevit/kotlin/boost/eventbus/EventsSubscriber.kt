package io.justdevit.kotlin.boost.eventbus

import kotlinx.coroutines.flow.Flow
import kotlin.reflect.KClass

/**
 * Interface for subscribing to a stream of events of a specified type.
 *
 * This interface is part of an event-bus system that allows components to react to events
 * of a specific type by utilizing Kotlin's coroutine-based [Flow].
 */
interface EventsSubscriber {
    fun <T : Event> subscribe(eventType: KClass<T>): Flow<T>
}
