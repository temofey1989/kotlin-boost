package io.justdevit.kotlin.boost.domain.model

import io.justdevit.kotlin.boost.serialization.UUID
import io.justdevit.kotlin.boost.serialization.json.JSON
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import java.util.UUID.randomUUID

class IdentifierTest :
    FreeSpec(
        {

            "Serialization tests" - {

                @Serializable
                @SerialName("ACCOUNT_ID")
                data class AccountId(override val value: UUID) : CompositeIdentifier<UUID, Long>()

                "Should be able process UUID identifier with undefined internal ID" {
                    val id = AccountId(randomUUID())
                    println(id)

                    val json = JSON.encodeToString(id)
                    println(json)

                    val value = JSON.decodeFromString<AccountId>(json)
                    println(value)

                    value shouldBe id
                    value
                        .internal
                        .value
                        .shouldBeNull()
                }

                "Should be able process UUID identifier with internal ID" {
                    val id = AccountId(randomUUID()).apply {
                        internal = 123L.toInternalIdentifier()
                    }
                    println(id)

                    val json = JSON.encodeToString(id)
                    println(json)

                    val value = JSON.decodeFromString<AccountId>(json)
                    println(value)

                    value shouldBe id
                    value.internal.value shouldBe 123L
                }

                "Should be able process UUID identifier with lazy internal ID" {
                    val id = AccountId(randomUUID()).apply {
                        internal = lazyInternalIdentifier { 123L }
                    }
                    println(id)

                    val json = JSON.encodeToString(id)
                    println(json)

                    val value = JSON.decodeFromString<AccountId>(json)
                    println(value)

                    value shouldBe id
                    value.internal.value shouldBe 123L
                }

                "Should be able process UUID identifier with lazy undefined internal ID" {
                    val id = AccountId(randomUUID()).apply {
                        internal = lazyInternalIdentifier { null }
                    }
                    println(id)

                    val json = JSON.encodeToString(id)
                    println(json)

                    val value = JSON.decodeFromString<AccountId>(json)
                    println(value)

                    value shouldBe id
                    value.internal.value shouldBe null
                }

                "Should be able process UUID identifier with lazy internal ID (suspend)" {
                    val id = AccountId(randomUUID()).apply {
                        internal = coLazyInternalIdentifier { 123L }
                    }
                    println(id)

                    val json = JSON.encodeToString(id)
                    println(json)

                    val value = JSON.decodeFromString<AccountId>(json)
                    println(value)

                    value shouldBe id
                    value.internal.value shouldBe 123L
                }

                "Should be able to deserialize object without internal ID information" {
                    val uuid = randomUUID()

                    val json = """
                        {
                          "value": "$uuid"
                        }
                    """.trimIndent()
                    println(json)

                    val value = JSON.decodeFromString<AccountId>(json)
                    println(value)

                    value.value shouldBe uuid
                    value.internal.value shouldBe null
                }
            }
        },
    )
