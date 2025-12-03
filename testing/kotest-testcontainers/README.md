# Testing - Kotest Testcontainers

## Overview

Base utilities for integrating [Testcontainers](https://testcontainers.com/) with [Kotest](https://kotest.io). Provides common abstractions used by specific container modules.

---

## Table of Contents

- [Configuration](#configuration)

---

## Configuration

Add the library to your project dependencies.

```toml
# libs.versions.toml

[versions]
kotlin-boost = '<latest>'

[libraries]
kotlin-boost-testing-kotest-testcontainers = { group = 'io.justdevit.kotlin.boost', name = 'kotest-testcontainers', version.ref = 'kotlin-boost' }
```

```kotlin
// build.gradle.kts

dependencies {
  testImplementation(libs.kotlin.boost.testing.kotest.testcontainers)
}
```
