package io.justdevit.kotlin.boost.eventbus

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlin.reflect.KClass

/**
 * A simple implementation of the [CoroutineEventBus] interface used for handling events.
 */
open class SimpleCoroutineEventBus : CoroutineEventBus {

    private val mutableSharedFlow: MutableSharedFlow<Event> = MutableSharedFlow<Event>()
    private val sharedFlow: SharedFlow<Event> = mutableSharedFlow.asSharedFlow()

    override fun <T : Event> subscribe(eventType: KClass<T>): Flow<T> = sharedFlow.filterIsInstance(eventType)

    override suspend fun publish(vararg events: Event) {
        events.forEach {
            mutableSharedFlow.emit(it)
        }
    }
}

/**
 * Subscribes to a stream of events of type [T] from the [CoroutineEventBus].
 *
 * This method allows consumers to listen for specific event types using Kotlin's
 * coroutine [Flow]. The event type is inferred from the reified type [T].
 *
 * @return A [Flow] of events of type [T]` emitted by the [CoroutineEventBus].
 */
inline fun <reified T : Event> CoroutineEventBus.subscribe(): Flow<T> = subscribe(T::class)
