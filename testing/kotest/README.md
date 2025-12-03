# Testing - Kotest

## Overview

Shared testing utilities and helpers to simplify writing tests with [Kotest](https://kotest.io/).

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
kotlin-boost-testing-kotest = { group = 'io.justdevit.kotlin.boost', name = 'kotest', version.ref = 'kotlin-boost' }
```

```kotlin
// build.gradle.kts

dependencies {
  testImplementation(libs.kotlin.boost.testing.kotest)
}
```

---

## Usage

```kotlin
// Install Boost testing helpers in your Kotest spec
install {
  boostExtensions()
}
```
