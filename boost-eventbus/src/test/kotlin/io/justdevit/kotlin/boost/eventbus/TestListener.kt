package io.justdevit.kotlin.boost.eventbus

class TestListener : EventListener<TestEvent1> {

    val listenedEvents = mutableListOf<TestEvent1>()

    override val supportedClass = TestEvent1::class.java

    override suspend fun onEvent(event: TestEvent1) {
        listenedEvents += event
    }

    fun clear() {
        listenedEvents.clear()
    }
}
