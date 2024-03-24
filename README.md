![example event parameter](https://github.com/temofey1989/kotlin-boost/actions/workflows/build.yml/badge.svg?branch=main)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=temofey1989_kotlin-boost&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=temofey1989_kotlin-boost)
[![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](https://www.apache.org/licenses/LICENSE-2.0)

# Overview

Kotlin Boost is a set of libraries which can be useful for startup of your project.

# Table of Contents

<!-- TOC -->
* [Modules](#modules)
  * [`Boost Shared`](#boost-shared)
    * [Configuration](#configuration)
      * [Maven](#maven)
      * [Gradle (.kts)](#gradle-kts)
    * [Usage](#usage)
  * [`Boost Serialization JSON`](#boost-serialization-json)
    * [Configuration](#configuration-1)
      * [Maven](#maven-1)
      * [Gradle (.kts)](#gradle-kts-1)
    * [Usage](#usage-1)
      * [Preconfigured `JSON` constant](#preconfigured-json-constant)
  * [`Boost Logging Core`](#boost-logging-core)
    * [Levels](#levels)
    * [Usage](#usage-2)
  * [`Boost Logging SLF4J`](#boost-logging-slf4j)
    * [Configuration](#configuration-2)
      * [Maven](#maven-2)
      * [Gradle (.kts)](#gradle-kts-2)
  * [`Boost Kotest`](#boost-kotest)
    * [Configuration](#configuration-3)
      * [Maven](#maven-3)
      * [Gradle (.kts)](#gradle-kts-3)
  * [`Boost Kotest MockServer`](#boost-kotest-mockserver)
    * [Configuration](#configuration-4)
      * [Maven](#maven-4)
      * [Gradle (.kts)](#gradle-kts-4)
    * [Usage](#usage-3)
  * [`Boost Kotest TestContainers`](#boost-kotest-testcontainers)
  * [`Boost Kotest TestContainers Keycloak`](#boost-kotest-testcontainers-keycloak)
    * [Configuration](#configuration-5)
      * [Maven](#maven-5)
      * [Gradle (.kts)](#gradle-kts-5)
    * [Usage](#usage-4)
  * [`Boost Kotest TestContainers Postgres`](#boost-kotest-testcontainers-postgres)
    * [Configuration](#configuration-6)
      * [Maven](#maven-6)
      * [Gradle (.kts)](#gradle-kts-6)
    * [Usage](#usage-5)
  * [`Boost Kotest TestContainers RabbitMQ`](#boost-kotest-testcontainers-rabbitmq)
    * [Configuration](#configuration-7)
      * [Maven](#maven-7)
      * [Gradle (.kts)](#gradle-kts-7)
    * [Usage](#usage-6)
<!-- TOC -->

# Modules

## `Boost Shared`

The module has a set of extensions for the core classes like `String`, `Collections`, `Boolean` and others.  
This extensions can bring some boost to your code to be more clean and readable.

### Configuration

#### Maven

```xml

<dependency>
  <groupId>io.justdevit.kotlin</groupId>
  <artifactId>boost-shared</artifactId>
  <version>${kotlin-boost.version}</version>
</dependency>
```

#### Gradle (.kts)

```kotlin
implementation("io.justdevit.kotlin:boost-shared:$kotlinBoostVersion")
```

### Usage

The IntelliJ IDEA will suggest you useful function for your usecase.

**Constants:**

* `ISO8601_FORMATTER`
* `EUROPEAN_DATE_FORMATTER`
* `EUROPEAN_DATE_TIME_FORMATTER`

---

## `Boost Serialization JSON`

If you use Kotlin Serialization, this module will provide you a useful constants (e.g. `JSON`)
and extension functions to make your work with this framework even easier.

### Configuration

#### Maven

```xml

<dependency>
  <groupId>io.justdevit.kotlin</groupId>
  <artifactId>boost-serialization-json</artifactId>
  <version>${kotlin-boost.version}</version>
</dependency>
```

#### Gradle (.kts)

```kotlin
implementation("io.justdevit.kotlin:boost-serialization-json:$kotlinBoostVersion")
```

### Usage

#### Preconfigured `JSON` constant

The `JSON` constant is a pre-configured `Json` to be compatible with structure with business attribute `type` inside. So, there is no conflicts with `sealed` classes.
Also, the constant ignores unknown fields while deserialization.

```kotlin
import io.justdevit.kotlin.boost.serialization.json.JSON

...
JSON.encodeToString(value)
...
```

---

## `Boost Logging Core`

The module provides the API to make your logging easy and also very powerful.  
The module was expired by [Kotlin Logging](https://github.com/oshai/kotlin-logging) project.

### Levels

* `TRACE`
* `DEBUG`
* `INFO`
* `WARN`
* `ERROR`

### Usage

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

---

## `Boost Logging SLF4J`

The module is implementation of the API of `Boost Logging Core` based on Slf4J framework.

### Configuration

#### Maven

```xml

<dependency>
  <groupId>io.justdevit.kotlin</groupId>
  <artifactId>boost-logging-slf4j</artifactId>
  <version>${kotlin-boost.version}</version>
</dependency>
```

#### Gradle (.kts)

```kotlin
implementation("io.justdevit.kotlin:boost-logging-slf4j:$kotlinBoostVersion")
```

---

## `Boost Kotest`

The module is focused on perform writing test using [Kotest](https://kotest.io/) and [Rest Assured](https://github.com/rest-assured/rest-assured) frameworks.

### Configuration

#### Maven

```xml

<dependency>
  <groupId>io.justdevit.kotlin</groupId>
  <artifactId>boost-kotest</artifactId>
  <version>${kotlin-boost.version}</version>
</dependency>
```

#### Gradle (.kts)

```kotlin
implementation("io.justdevit.kotlin:boost-kotest:$kotlinBoostVersion")
```

---

## `Boost Kotest MockServer`

The module provides a Kotest extension to simplifies Rest API mocking for integration tests.

### Configuration

#### Maven

```xml

<dependency>
  <groupId>io.justdevit.kotlin</groupId>
  <artifactId>boost-kotest-mockserver</artifactId>
  <version>${kotlin-boost.version}</version>
</dependency>
```

#### Gradle (.kts)

```kotlin
implementation("io.justdevit.kotlin:boost-kotest-mockserver:$kotlinBoostVersion")
```

### Usage

* **Apply extension globally**
  ```kotlin
  object ProjectConfig : AbstractProjectConfig() {
      override fun extensions() =
          listOf(
              MockServerExtension(MicronautTest::class),
          )
  }
  ```
* **Install extension in test spec**
  ```kotlin
  class MyTest : FreeSpec({
      installMockServer()
      ...
  })
  ```

---

## `Boost Kotest TestContainers`

The module provides API for all TestContainers modules. Also it provides Stubbing Extension for the tests.

### Configuration

#### Maven

```xml

<dependency>
  <groupId>io.justdevit.kotlin</groupId>
  <artifactId>boost-kotest-testcontainers</artifactId>
  <version>${kotlin-boost.version}</version>
</dependency>
```

#### Gradle (.kts)

```kotlin
implementation("io.justdevit.kotlin:boost-kotest-testcontainers:$kotlinBoostVersion")
```

### Usage

* **Apply stubbing extension globally**
  ```kotlin
  object ProjectConfig : AbstractProjectConfig() {
      override fun extensions() =
          listOf(
              StubbingExtension({ System.getenv("DOCKER_HOST").isNullOrBlank() }),
          )
  }
  ```
* **Install stubbing extension in test spec**
  ```kotlin
  class MyTest : FreeSpec({
      installStubbing{ System.getenv("DOCKER_HOST").isNullOrBlank() }
      ...
  })
  ```

---

## `Boost Kotest TestContainers Keycloak`

The module provides a Kotest extension to run Keycloak with TestContainers framework for integration tests.

### Configuration

#### Maven

```xml

<dependency>
  <groupId>io.justdevit.kotlin</groupId>
  <artifactId>boost-kotest-testcontainers-keycloak</artifactId>
  <version>${kotlin-boost.version}</version>
</dependency>
```

#### Gradle (.kts)

```kotlin
implementation("io.justdevit.kotlin:boost-kotest-testcontainers-keycloak:$kotlinBoostVersion")
```

### Usage

* **Apply extension globally**
  ```kotlin
  object ProjectConfig : AbstractProjectConfig() {
      override fun extensions() =
          listOf(
              KeycloakExtension(MicronautTest::class),
          )
  }
  ```
* **Install extension in test spec**
  ```kotlin
  class MyTest : FreeSpec({
      installKeycloak()
      ...
  })
  ```

---

## `Boost Kotest TestContainers Postgres`

The module provides a Kotest extension to run Postgres Database Server with TestContainers framework for integration tests.

### Configuration

#### Maven

```xml

<dependency>
  <groupId>io.justdevit.kotlin</groupId>
  <artifactId>boost-kotest-testcontainers-postgres</artifactId>
  <version>${kotlin-boost.version}</version>
</dependency>
```

#### Gradle (.kts)

```kotlin
implementation("io.justdevit.kotlin:boost-kotest-testcontainers-postgres:$kotlinBoostVersion")
```

### Usage

* **Apply extension globally**
  ```kotlin
  object ProjectConfig : AbstractProjectConfig() {
      override fun extensions() =
          listOf(
              PostgresExtension(MicronautTest::class),
          )
  }
  ```
* **Install extension in test spec**
  ```kotlin
  class MyTest : FreeSpec({
      installPostgres()
      ...
  })
  ```

---

## `Boost Kotest TestContainers RabbitMQ`

The module provides a Kotest extension to run RabbitMQ Broker with TestContainers framework for integration tests.

### Configuration

#### Maven

```xml

<dependency>
  <groupId>io.justdevit.kotlin</groupId>
  <artifactId>boost-kotest-testcontainers-rabbitmq</artifactId>
  <version>${kotlin-boost.version}</version>
</dependency>
```

#### Gradle (.kts)

```kotlin
implementation("io.justdevit.kotlin:boost-kotest-testcontainers-rabbitmq:$kotlinBoostVersion")
```

### Usage

* **Apply extension globally**
  ```kotlin
  object ProjectConfig : AbstractProjectConfig() {
      override fun extensions() =
          listOf(
              RabbitMqExtension,
          )
  }
  ```
* **Install extension in test spec**
  ```kotlin
  class MyTest : FreeSpec({
      installRabbitMq()
      ...
  })
  ```

