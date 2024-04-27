package io.justdevit.kotlin.boost.extension

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

enum class TestEnum {
    TEST,
}

class StringKtTest :
    FreeSpec(
        {

            "Enum conversion" - {
                "test" {
                    "TEST".toEnum<TestEnum>() shouldBe TestEnum.TEST
                }
            }

            "Truncated length tests" - {
                data class Input(
                    val given: String,
                    val length: Int,
                    val expected: String,
                )
                listOf(
                    Input("12345", 100, "12345"),
                    Input("12345", 4, "1..."),
                    Input("1234567890", 7, "1234..."),
                    Input("12", 1, "1"),
                    Input("123", 2, "12"),
                    Input("1234", 3, "123"),
                ).forEach {
                    "${it.given} should be trunked to [${it.length}]: ${it.expected}" {
                        it.given.truncateToLength(it.length) shouldBe it.expected
                    }
                }
            }
        },
    )
