# Commons

## Overview

Kotlin extensions and utilities for standard types to improve readability.

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
kotlin-boost = "<latest>"

[libraries]
kotlin-boost-commons = { group = 'io.justdevit.kotlin.boost', name = 'commons', version.ref = 'kotlin-boost' }
```

```kotlin
// build.gradle.kts

dependencies {
    implementation(libs.kotlin.boost.commons)
}
```

---

## Usage

**Constants:**

* `ISO8601_FORMATTER`
* `EUROPEAN_DATE_FORMATTER`
* `EUROPEAN_DATE_TIME_FORMATTER`

Example:

```kotlin
val text = ISO8601_FORMATTER.format(Instant.now())
```

TBD...
