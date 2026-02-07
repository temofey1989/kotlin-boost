package io.justdevit.kotlin.boost.eventbus

import kotlinx.coroutines.delay
import kotlin.time.Clock.System.now
import kotlin.time.Instant

class TestListener(override val priority: Int = 0) : EventListener<TestEvent> {

    val testEventRecords = mutableListOf<TestEventRecord>()

    override val supportedClass = TestEvent::class.java

    override suspend fun onEvent(event: TestEvent) {
        testEventRecords += TestEventRecord(event)
        delay(10)
    }

    fun clear() {
        testEventRecords.clear()
    }

    data class TestEventRecord(val event: TestEvent, val timestamp: Instant = now())
}
