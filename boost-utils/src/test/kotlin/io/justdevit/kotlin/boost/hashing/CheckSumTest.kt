package io.justdevit.kotlin.boost.hashing

import io.justdevit.kotlin.boost.extension.encode
import io.justdevit.kotlin.boost.extension.randomString
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldStartWith
import io.kotest.matchers.types.shouldBeInstanceOf

class CheckSumTest :
    FreeSpec(
        {
            "Should be able to create checksum" {
                val text = "Loooooooong text"

                val checksum = text.to<Sha256Checksum>()

                with(checksum) {
                    algorithm shouldBe SHA256_CHECKSUM_ALGORITHM
                    asString() shouldStartWith "sha-256$CHECKSUM_SEPARATOR"
                }
            }

            "Should be able to construct checksum object from text" {
                val text = randomString()
                val value = "sha-256:${text.encode()}"

                val checksum = value.toChecksum()

                with(checksum) {
                    this.shouldBeInstanceOf<Sha256Checksum>()
                    this.value.decodeToString() shouldBe text
                }
            }
        },
    )
