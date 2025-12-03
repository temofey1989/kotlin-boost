# Integration - EventBus

## Overview

Lightweight event-bus interfaces and helpers for in-process _pub/sub_-style communication.

---

## Table of Contents

- [Configuration](#configuration)
- [Basic usage](#usage)

---

## Configuration

Add the library to your project dependencies.

```toml
# libs.versions.toml

[versions]
kotlin-boost = '<latest>'

[libraries]
kotlin-boost-integration-eventbus = { group = 'io.justdevit.kotlin.boost', name = 'eventbus', version.ref = 'kotlin-boost' }
```

```kotlin
// build.gradle.kts

dependencies {
  implementation(libs.kotlin.boost.integration.eventbus)
}
```

---

## Usage

```kotlin
// Define an event
class UserCreated(val id: UUID) : Event()

// Define a listener
object UserCreatedListener : EventListener<UserCreated> {
  override val supportedClass = UserCreated::class.java
  override val priority: Int = 0
  override suspend fun onEvent(event: UserCreated) {
    // handle event
    println("User created: ${event.id}")
  }
}

// Create an event-bus and publish
val bus = SimpleEventBus(UserCreatedListener)
bus.publish(UserCreated(id = UUID.randomUUID()))
```

TBD...
