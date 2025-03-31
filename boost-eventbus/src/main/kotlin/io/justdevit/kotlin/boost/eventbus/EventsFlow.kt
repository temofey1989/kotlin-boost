package io.justdevit.kotlin.boost.eventbus

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

/**
 * A simple implementation of the [Events] interface used for handling events.
 *
 * @property coroutineScope The [CoroutineScope] used for managing coroutines within the event system.
 * This scope is used for both the emission of events and the subscription to event flows.
 */
open class EventsFlow(override val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Unconfined)) : Events {

    private val mutableSharedFlow: MutableSharedFlow<Event> = MutableSharedFlow<Event>()
    private val sharedFlow: SharedFlow<Event> = mutableSharedFlow.asSharedFlow()

    override fun <T : Event> subscribe(eventType: KClass<T>): Flow<T> = sharedFlow.filterIsInstance(eventType)

    override suspend fun send(vararg events: Event) {
        events.forEach {
            mutableSharedFlow.emit(it)
        }
    }

    override fun close() {
        coroutineScope.cancel()
    }
}

/**
 * Subscribes to a stream of events of type [T] from the [Events].
 *
 * This method allows consumers to listen for specific event types using Kotlin's
 * coroutine [Flow]. The event type is inferred from the reified type [T].
 *
 * @return A [Flow] of events of type [T]` emitted by the [Events].
 */
inline fun <reified T : Event> Events.subscribe(): Flow<T> = subscribe(T::class)

/**
 * Subscribes to events of a specific type [T] and processes them using the provided action.
 *
 * This method launches a coroutine on the supplied [scope] to collect events of type [T]
 * and passes them to the [action] for processing. The coroutine scope defaults to the
 * [Events.coroutineScope] if not explicitly provided.
 *
 * @param T The type of events to subscribe to. Must be a subtype of [Event].
 * @param scope The [CoroutineScope] in which the subscription coroutine will run.
 *              Defaults to the [Events.coroutineScope].
 * @param action The [FlowCollector] that defines how to handle the collected events.
 */
inline fun <reified T : Event> Events.subscribe(scope: CoroutineScope = coroutineScope, action: FlowCollector<T>) =
    scope.launch(start = CoroutineStart.UNDISPATCHED) {
        subscribe<T>().collect(action)
    }
