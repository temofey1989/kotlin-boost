package io.justdevit.kotlin.boost.eventbus

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldContainExactly
import org.awaitility.Awaitility.await
import java.time.Duration

class EventsFlowTest :
    FreeSpec(
        {

            val events = EventsFlow()

            "Should be able to collect events" {
                val collector = mutableListOf<Event>()
                val event1 = TestEvent1("TEST")
                val event2 = TestEvent2("TEST")
                events.subscribe<TestEvent1> { collector += it }

                events.send(event1, event2)

                await().atMost(Duration.ofSeconds(1)).untilAsserted {
                    collector.shouldContainExactly(event1)
                }
            }
        },
    )
