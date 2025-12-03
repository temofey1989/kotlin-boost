package io.justdevit.kotlin.boost.environment

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import java.math.BigDecimal

class PropsTest :
    FreeSpec(
        {

            val key = "var.test"

            beforeEach {
                System.setProperty(key, "1.2")
            }

            "Should be able to extract property as String" {
                val value = propertyAsString(key)
                value shouldBe "1.2"
            }

            "Should be able to extract property as Float" {
                val value = property<Float>(key)
                value shouldBe 1.2f
            }

            "Should be able to extract property as Double" {
                val value = property<Double>(key)
                value shouldBe 1.2
            }

            "Should be able to extract property as BigDecimal" {
                val value = property<BigDecimal>(key)
                value shouldBe 1.2.toBigDecimal()
            }

            "Should be able to extract property list as BigDecimal" {
                val value = propertyList<BigDecimal>(key)
                value shouldContainExactly listOf(1.2.toBigDecimal())
            }

            "Should be able to use default value supplier" {
                val value = property<Int>("val.test") { 10 }
                value shouldBe 10
            }

            "Should be able reject unsupported type" {
                shouldThrow<IllegalArgumentException> {
                    property<PropsTest>("var.test")
                }
            }
        },
    )
