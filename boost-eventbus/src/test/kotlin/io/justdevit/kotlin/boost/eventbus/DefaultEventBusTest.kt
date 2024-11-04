package io.justdevit.kotlin.boost.eventbus

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldContainExactly

class DefaultEventBusTest :
    FreeSpec(
        {

            val eventBus = DefaultEventBus()
            val listener = TestListener()
            eventBus.register(listener)
            beforeEach {
                listener.clear()
            }

            "Should not register listener twice" {
                eventBus.register(listener)
                val event = TestEvent("TEST")

                eventBus.publish(event)

                listener.listenedEvents.shouldContainExactly(event)
            }

            "Should detect and exclude cycles in event execution" {
                val event1 = TestEvent("EVENT-1")
                val event2 = TestEvent("EVENT-2")
                val event3 = TestEvent("EVENT-3")

                event1.registerNext(event2)
                event2.registerNext(event3)
                event3.registerNext(event1)

                eventBus.publish(event1)

                listener.listenedEvents.shouldContainExactly(event1, event2, event3)
            }
        },
    )
