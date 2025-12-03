# Domain

## Overview

Utilities and base types to help model Domain-Driven Design (DDD) concepts such as identities and aggregates.

---

## Table of Contents

- [Configuration](#configuration)
- [Basic usage](#usage)

---

## Configuration

Add the library to your project dependencies.

```toml
[versions]
kotlin-boost = '<latest>'

[libraries]
kotlin-boost-domain = { group = 'io.justdevit.kotlin.boost', name = 'domain', version.ref = 'kotlin-boost' }
```

```kotlin
dependencies {
    implementation(libs.kotlin.boost.domain)
}
```

---

## Usage

**Classes:**

* `Identity` - A marker class for your identity classes.
  ```kotlin
  @Serializable
  data class UserId(override val value: UUID) : Identity<UUID>
  ```
* `CompositeIdentity` - class represents a
  ```kotlin
  @Serializable
  data class UserId(override val value: UUID) : CompsiteIdentity<UUID, Long>()
  
  val id = UserId(uuid).apply {
      internal = lazyInternalIdentifier { repository.getIdByPublicId(uuid) }
  }
  ```

TBD...
