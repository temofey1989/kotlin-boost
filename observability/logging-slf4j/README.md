# Observability - Logging SLF4J

## Overview

SLF4J-based implementation for Boost Logging Core. Bridges Boost’s API to SLF4J backends.

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
kotlin-boost-observability-logging-slf4j = { group = 'io.justdevit.kotlin.boost', name = 'logging-slf4j', version.ref = 'kotlin-boost' }
```

```kotlin
// build.gradle.kts

dependencies {
  implementation(libs.kotlin.boost.observability.logging.slf4j)
}
```

---
