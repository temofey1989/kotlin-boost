package io.justdevit.kotlin.boost.eventbus

import kotlinx.coroutines.CoroutineScope

/**
 * Interface representing an event-bus supporting coroutine-based event sending and subscription.
 *
 * This interface combines functionality for sending events to stream, subscribing to event streams,
 * and managing the [Events] lifecycle.
 */
interface Events :
    EventsSender,
    EventsSubscriber,
    AutoCloseable {

    val coroutineScope: CoroutineScope

    override fun close() = Unit
}
