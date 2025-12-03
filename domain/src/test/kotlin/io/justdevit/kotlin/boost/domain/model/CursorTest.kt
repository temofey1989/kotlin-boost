package io.justdevit.kotlin.boost.domain.model

import io.justdevit.kotlin.boost.extension.randomString
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class CursorTest :
    FreeSpec(
        {
            CursorDirection.entries.forEach {
                "Should be able to serialize and deserialize cursor object ($it)" {
                    val cursor = Cursor(
                        identifier = "id",
                        value = randomString(),
                        direction = it,
                    )

                    val text = cursor.serialize()
                    val result = Cursor.deserialize(text)

                    result shouldBe cursor
                }
            }
        },
    )
