package io.justdevit.kotlin.boost.eventbus

class TestListener : EventListener<TestEvent> {

    val listenedEvents = mutableListOf<TestEvent>()

    override val supportedClass = TestEvent::class.java

    override suspend fun onEvent(event: TestEvent) {
        listenedEvents += event
    }

    fun clear() {
        listenedEvents.clear()
    }
}
