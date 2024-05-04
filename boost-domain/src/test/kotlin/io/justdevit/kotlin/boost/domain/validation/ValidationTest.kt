package io.justdevit.kotlin.boost.domain.validation

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldBeSingleton
import io.kotest.matchers.maps.shouldContain
import io.kotest.matchers.maps.shouldNotContain
import io.kotest.matchers.shouldBe

class ValidationTest :
    FreeSpec(
        {

            "Should be able to throw an exception on failure" {
                val exception = shouldThrow<ValidationException> {
                    validation("TEST") {
                        code = "test"
                        "a" to "b"
                        failure {
                            key = "message"
                            "c" to "d"
                            "MESSAGE"
                        }
                    }
                }

                with(exception) {
                    code shouldBe "test"
                    message shouldBe "TEST - FAILED"
                    metadata.shouldContain("a" to "b")
                    metadata.shouldNotContain("c" to "d")
                    failures.shouldBeSingleton {
                        it.key shouldBe "message"
                        it.message shouldBe "MESSAGE"
                        it.metadata.shouldNotContain("a" to "b")
                        it.metadata.shouldContain("c" to "d")
                    }
                }
            }
        },
    )
