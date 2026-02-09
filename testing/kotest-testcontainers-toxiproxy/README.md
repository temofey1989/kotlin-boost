# Testing - Kotest Testcontainers Toxiproxy

## Overview

[Kotest](https://kotest.io) extension for running a Toxiproxy container via [Testcontainers](https://testcontainers.com/) during tests.

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
kotlin-boost-testing-kotest-testcontainers-rabbitmq = { group = 'io.justdevit.kotlin.boost', name = 'kotest-testcontainers-toxiproxy', version.ref = 'kotlin-boost' }
```

```kotlin
// build.gradle.kts

dependencies {
  testImplementation(libs.kotlin.boost.testing.kotest.testcontainers.toxiproxy)
}
```

---

## Usage

* **Apply extension globally**
  ```kotlin
  object ProjectConfig : AbstractProjectConfig() {
      override val extensions = listOf(ToxiproxyExtension())
  }
  ```
* **Manual extension applying in tests**
  ```kotlin
  class MyTest : FreeSpec({
      install {
          toxiproxy()  
      }
  })
  ```
* **Create Proxic**
  ```kotlin
  class MyTest : FreeSpec({
      val dbProxy = proxic("db", "postgres:5432")
      ...
  })
  ```
