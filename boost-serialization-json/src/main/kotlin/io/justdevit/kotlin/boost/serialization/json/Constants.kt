package io.justdevit.kotlin.boost.serialization.json

import kotlinx.serialization.json.Json

val JSON =
    Json {
        encodeDefaults = true
        classDiscriminator = "@type"
        ignoreUnknownKeys = true
    }
