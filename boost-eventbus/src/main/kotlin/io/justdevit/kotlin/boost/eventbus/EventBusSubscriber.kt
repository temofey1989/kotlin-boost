package io.justdevit.kotlin.boost.eventbus

import kotlinx.coroutines.flow.Flow
import kotlin.reflect.KClass

interface EventBusSubscriber {
    fun <T : Event> subscribe(eventType: KClass<T>): Flow<T>
}
