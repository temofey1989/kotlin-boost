package io.justdevit.kotlin.boost.eventbus

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec

class EventTest :
    FreeSpec(
        {

            "Should reject to register itself to chain" {
                val event = TestEvent1("TEST")

                shouldThrow<IllegalArgumentException> {
                    event.registerNext(event)
                }
            }
        },
    )
