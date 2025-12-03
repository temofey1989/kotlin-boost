# Testing - Kotest Testcontainers Keycloak

## Overview

[Kotest](https://kotest.io) extension for running a Keycloak container via [Testcontainers](https://testcontainers.com/) during tests.

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
kotlin-boost-testing-kotest-testcontainers-keycloak = { group = 'io.justdevit.kotlin.boost', name = 'kotest-testcontainers-keycloak', version.ref = 'kotlin-boost' }
```

```kotlin
// build.gradle.kts

dependencies {
  testImplementation(libs.kotlin.boost.testing.kotest.testcontainers.keycloak)
}
```

---

## Usage

* **Apply extension globally**
  ```kotlin
  object ProjectConfig : AbstractProjectConfig() {
      override val extensions = listOf(KeycloakExtension())
  }
  ```
* **Manual extension applying in tests**
  ```kotlin
  class MyTest : FreeSpec({
      install {
          keycloak()  
      }
  })
  ```
