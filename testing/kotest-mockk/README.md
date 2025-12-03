# Testing - Kotest MockK

## Overview

[Kotest](https://kotest.io) extension utilities for using [Mockk Framework](https://mockk.io/) with sensible defaults and cleanup helpers.

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
kotlin-boost-testing-kotest-mockk = { group = 'io.justdevit.kotlin.boost', name = 'kotest-mockk', version.ref = 'kotlin-boost' }
```

```kotlin
// build.gradle.kts

dependencies {
  testImplementation(libs.kotlin.boost.testing.kotest.mockk)
}
```

---

## Usage

* **Apply extension globally**
  ```kotlin
  object ProjectConfig : AbstractProjectConfig() {
      override val extensions = listOf(MockkExtension)
  }
  ```
* **Manual extension applying in tests**
  ```kotlin
  class MyTest : FreeSpec({
      install {
          mockk()  
      }
  })
  ```
