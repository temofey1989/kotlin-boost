# Version Catalog

## Overview

Represents a collection of libraries and a set of bundles to boost your project development.

---

## Table of Contents

- [Configuration](#configuration)
- [Basic usage](#usage)

---

## Configuration

Add the library to your project settings:

```kotlin
// settings.gradle.kts

dependencyResolutionManagement {
  ...
  versionCatalogs {
    create("boostLibs") {
      from("io.justdevit.kotlin.boost:version-catalog:<version>")
    }
  }
}
```

---

## Usage

Add a particular library to your project:

```kotlin
// build.gradle.kts

dependencies {
  ...
  implementation(boostLibs.boost.logging.slf4j)
  ...
}
```
