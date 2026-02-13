# Testing - Kotest Logging Asserts

## Overview

[Kotest](https://kotest.io) extension to test log records.

---

## Table of Contents

- [Configuration](#configuration)
- [Features](#features)

---

## Configuration

Add the library to your project dependencies.

```toml
# libs.versions.toml

[versions]
kotlin-boost = '<latest>'

[libraries]
kotlin-boost-testing-kotest-logging = { group = 'io.justdevit.kotlin.boost', name = 'kotest-logging-asserts', version.ref = 'kotlin-boost' }
```

```kotlin
// build.gradle.kts

dependencies {
  testImplementation(libs.kotlin.boost.testing.kotest.logging)
}
```

### Apply extension globally

  ```kotlin
  object ProjectConfig : AbstractProjectConfig() {
  override val extensions = listOf(LoggingExtension)
}
  ```

### Manual extension applying in tests

  ```kotlin
  class MyTest : FreeSpec({
  install {
    logging()
  }
})
  ```

---

## Features

```kotlin
class MyTest : FreeSpec({

  ...

  "TEST" {
    assertLogs(ordered = true) {            // (1)
      message("Hello world!")               // (2)

      !trace("TEST")                        // (3)
      debug(!"TEST")                        // (4)
      info("Hello world!", exactly = 2)     // (5)
      warn("TEST-.+".toRegex())             // (6)
      error(!"TEST-.+".toRegex())           // (7)
    }
  }

})
```

1. Basic block for log assertion configuration. Attribute `ordered` defines if logs should be checked in order or not.
2. Simple assert that logs contain a given text.
3. Asserts that no `TRACE` log record with a given text.
4. Asserts that no `DEBUG` log record with a given text.
5. Asserts that logs contain a given text exactly `N` times for `INFO` level. In this case `N = 2`.
6. Asserts that logs contain a given regular expression with `WARN` level.
7. Asserts that no `ERROR` log record with a given regular expression.
