# Serialization JSON

## Overview

Preconfigured `kotlinx.serialization` Json instance and helpers for sealed hierarchies and tolerant parsing.

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
kotlin-boost-serialization-json = { group = 'io.justdevit.kotlin.boost', name = 'serialization-json', version.ref = 'kotlin-boost' }
```

```kotlin
// build.gradle.kts

dependencies {
  implementation(libs.kotlin.boost.serialization.json)
}
```

---

## Usage

### Preconfigured `JSON` constant

The `JSON` constant is a pre-configured `Json` to be compatible with structure with business attribute `type` inside.  
So, there are no conflicts with `sealed` classes.
Also, the constant ignores unknown fields while deserialization.

```kotlin
import io.justdevit.kotlin.boost.serialization.json.JSON
...
JSON.encodeToString(value)
...
```
