# Observability - Logging Core

## Overview

Core logging API with lightweight Kotlin-friendly Logger, message builders, and context fields.

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
kotlin-boost-observability-logging-core = { group = 'io.justdevit.kotlin.boost', name = 'logging-core', version.ref = 'kotlin-boost' }
```

```kotlin
// build.gradle.kts

dependencies {
  implementation(libs.kotlin.boost.observability.logging.core)
}
```

---

## Levels

* `TRACE`
* `DEBUG`
* `INFO`
* `WARN`
* `ERROR`

---

## Usage

* **Logger by function**
  ```kotlin
  class Service {

      private val log: Logger = logger { }    
  
      fun doAction() {
          log.info { "Action has started." }
          ...
      }
  }
  ```


* **Logger by name**
  ```kotlin
  class Service {

      private val log: Logger = logger("service")    
  
      fun doAction() {
          log.info { "Action has started." }
          ...
      }
  }
  ```
  ```kotlin
  class Service : Loggable("service") {    
  
      fun doAction() {
          log.info { "Action has started." }
          ...
      }
  }
  ```


* **Logger by class**
  ```kotlin
  class Service {

      private val log: Logger = logger(this::class)       
  
      fun doAction() {
          log.info { "Action has started." }
          ...
      }
  }
  ```
  ```kotlin
  class Service : Loggable {    
  
      fun doAction() {
          log.info { "Action has started." }
          ...
      }
  }
  ```
  ```kotlin
  class Service {    
  
      companion object : Loggable(Service::class.java)
  
      fun doAction() {
          log.info { "Action has started." }
          ...
      }
  }
  ```


* **Logging with fields**
  ```kotlin
  log.info {
      "id" to task.id
      "The task has been created. ID: ${task.id}"
  }
  ```
  In case you want logging in JSON format, you can use fields to include them as attributes of the logging message.


* **Logging Scope**
  ```kotlin
  logScope(
      "id" to task.id
  ) {
      log.debug { "Task creation has been started." }
      ...
      log.info { "The task has been created. ID: ${task.id}" }
  }
  ```
  In scope of this block, all messages will have `id` field included.


* **Logging Scope with Coroutines**
  ```kotlin
  coLogScope(
      "id" to task.id
  ) {
      log.debug { "Task creation has been started." }
      ...
      log.info { "The task has been created. ID: ${task.id}" }
  }
  ```
  In scope of this block, all messages will have `id` field included within Coroutine Context.
