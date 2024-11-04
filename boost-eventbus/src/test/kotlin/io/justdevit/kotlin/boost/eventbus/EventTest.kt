package io.justdevit.kotlin.boost.eventbus

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import java.lang.IllegalArgumentException

class EventTest :
    FreeSpec(
        {

            "Should reject to register itself to chain" {
                val event = TestEvent("TEST")

                shouldThrow<IllegalArgumentException> {
                    event.registerNext(event)
                }
            }
        },
    )
