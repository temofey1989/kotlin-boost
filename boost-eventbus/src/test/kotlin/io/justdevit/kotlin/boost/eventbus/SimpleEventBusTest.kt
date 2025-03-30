package io.justdevit.kotlin.boost.eventbus

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldContainExactly

class SimpleEventBusTest :
    FreeSpec(
        {

            val eventBus = SimpleEventBus()
            val listener = TestListener()
            eventBus.register(listener)
            beforeEach {
                listener.clear()
            }

            "Should not register listener twice" {
                eventBus.register(listener)
                val event = TestEvent1("TEST")

                eventBus.publish(event)

                listener.listenedEvents.shouldContainExactly(event)
            }

            "Should detect and exclude cycles in event execution" {
                val event1 = TestEvent1("EVENT-1")
                val event2 = TestEvent1("EVENT-2")
                val event3 = TestEvent1("EVENT-3")

                event1.registerNext(event2)
                event2.registerNext(event3)
                event3.registerNext(event1)

                eventBus.publish(event1)

                listener.listenedEvents.shouldContainExactly(event1, event2, event3)
            }
        },
    )
