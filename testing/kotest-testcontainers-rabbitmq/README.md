# Testing - Kotest Testcontainers RabbitMQ

## Overview

[Kotest](https://kotest.io) extension for running a RabbitMQ container via [Testcontainers](https://testcontainers.com/) during tests.

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
kotlin-boost-testing-kotest-testcontainers-rabbitmq = { group = 'io.justdevit.kotlin.boost', name = 'kotest-testcontainers-rabbitmq', version.ref = 'kotlin-boost' }
```

```kotlin
// build.gradle.kts

dependencies {
  testImplementation(libs.kotlin.boost.testing.kotest.testcontainers.rabbitmq)
}
```

---

## Usage

* **Apply extension globally**
  ```kotlin
  object ProjectConfig : AbstractProjectConfig() {
      override val extensions = listOf(RabbitMqExtension())
  }
  ```
* **Manual extension applying in tests**
  ```kotlin
  class MyTest : FreeSpec({
      install {
          rabbitMq()  
      }
  })
  ```
