package io.justdevit.kotlin.boost.eventbus

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldContainExactly
import kotlinx.coroutines.CoroutineStart.UNDISPATCHED
import kotlinx.coroutines.launch
import org.awaitility.Awaitility.await
import java.time.Duration

class SimpleCoroutineEventBusTest :
    FreeSpec(
        {

            val eventBus = SimpleCoroutineEventBus()

            "Should not register listener twice" {
                val collector = mutableListOf<Event>()
                val event1 = TestEvent1("TEST")
                val event2 = TestEvent2("TEST")
                val job = launch(start = UNDISPATCHED) {
                    eventBus
                        .subscribe<TestEvent1>()
                        .collect { collector += it }
                }

                eventBus.publish(event1, event2)

                await().atMost(Duration.ofSeconds(1)).untilAsserted {
                    collector.shouldContainExactly(event1)
                }
                job.cancel()
            }
        },
    )
