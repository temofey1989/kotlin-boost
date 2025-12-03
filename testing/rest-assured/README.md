# Testing - Rest-Assured

## Overview

Small extensions to improve Rest-Assured usage in Kotlin tests.

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
kotlin-boost-testing-rest-assured = { group = 'io.justdevit.kotlin.boost', name = 'rest-assured', version.ref = 'kotlin-boost' }
```

```kotlin
// build.gradle.kts

dependencies {
  testImplementation(libs.kotlin.boost.testing.rest.assured)
}
```

---

## Usage

```kotlin
// Extract response body to a Kotlin type
val user: User = given()
  .get("/api/users/1") k
  .then()
  .extract()
  .body()
```
