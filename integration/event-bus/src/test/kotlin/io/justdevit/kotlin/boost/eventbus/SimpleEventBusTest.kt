package io.justdevit.kotlin.boost.eventbus

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.comparables.shouldBeLessThan

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

                listener.testEventRecords
                    .map { it.event }
                    .shouldContainExactly(event)
            }

            "Should detect and exclude cycles in event execution" {
                val event1 = TestEvent1("EVENT-1")
                val event2 = TestEvent1("EVENT-2")
                val event3 = TestEvent1("EVENT-3")

                event1.registerNext(event2)
                event2.registerNext(event3)
                event3.registerNext(event1)

                eventBus.publish(event1)

                listener.testEventRecords
                    .map { it.event }
                    .shouldContainExactly(event1, event2, event3)
            }

            "Should accept abstract class" {
                val event1 = TestEvent1("TEST-1")
                val event2 = TestEvent2("TEST-2")

                eventBus.publish(event1, event2)

                listener.testEventRecords
                    .map { it.event }
                    .shouldContainExactly(event1, event2)
            }

            "Should be able to handle priority" {
                val eventBus = SimpleEventBus()
                val event = TestEvent1("TEST-1")

                val listener1 = TestListener(priority = 2)
                eventBus.register(listener1)
                val listener2 = TestListener(priority = 1)
                eventBus.register(listener2)

                eventBus.publish(event)

                listener1.testEventRecords
                    .shouldHaveSize(1)
                    .map { it.event }
                    .shouldContainExactly(event)
                listener2.testEventRecords
                    .shouldHaveSize(1)
                    .map { it.event }
                    .shouldContainExactly(event)

                val firstProcessingTimestamp = listener2.testEventRecords
                    .first()
                    .timestamp
                val secondProcessingTimestamp = listener1.testEventRecords
                    .first()
                    .timestamp
                firstProcessingTimestamp shouldBeLessThan secondProcessingTimestamp
            }
        },
    )
