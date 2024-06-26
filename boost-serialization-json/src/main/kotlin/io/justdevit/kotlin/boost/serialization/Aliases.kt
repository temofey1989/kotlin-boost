package io.justdevit.kotlin.boost.serialization

import kotlinx.serialization.Serializable

typealias UUID =
    @Serializable(with = UuidSerializer::class)
    java.util.UUID

typealias BigInteger =
    @Serializable(with = BigIntegerSerializer::class)
    java.math.BigInteger

typealias BigDecimal =
    @Serializable(with = BigDecimalSerializer::class)
    java.math.BigDecimal

typealias LocalDate =
    @Serializable(with = LocalDateSerializer::class)
    java.time.LocalDate

typealias LocalTime =
    @Serializable(with = LocalTimeSerializer::class)
    java.time.LocalTime

typealias LocalDateTime =
    @Serializable(with = LocalDateTimeSerializer::class)
    java.time.LocalDateTime

typealias OffsetDateTime =
    @Serializable(with = OffsetDateTimeSerializer::class)
    java.time.OffsetDateTime

typealias Instant =
    @Serializable(with = InstantSerializer::class)
    java.time.Instant

typealias ZoneIdTime =
    @Serializable(with = ZoneIdSerializer::class)
    java.time.ZoneId

typealias ZonedDateTime =
    @Serializable(with = ZonedDateTimeSerializer::class)
    java.time.ZonedDateTime

typealias Duration =
    @Serializable(with = DurationSerializer::class)
    java.time.Duration

typealias Period =
    @Serializable(with = PeriodSerializer::class)
    java.time.Period

typealias URI =
    @Serializable(with = UriSerializer::class)
    java.net.URI
