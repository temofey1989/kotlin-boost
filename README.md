![example event parameter](https://github.com/temofey1989/kotlin-boost/actions/workflows/build.yml/badge.svg?branch=main)
[![Code Quality](https://github.com/temofey1989/kotlin-boost/actions/workflows/code-quality.yml/badge.svg)](https://github.com/temofey1989/kotlin-boost/actions/workflows/code-quality.yml)
[![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](https://www.apache.org/licenses/LICENSE-2.0)

# Overview

Kotlin Boost is a set of libraries which can be useful for startup of your project.

# Table of Contents

* [Table of Contents](#table-of-contents)
* [Dependency Management](#dependency-management)
    * [Maven](#maven)
    * [Gradle (.kts)](#gradle-kts)
* [Modules](#modules)
  * [`Boost Commons`](#boost-commons)
    * [Configuration](#configuration)
      * [Maven](#maven-1)
      * [Gradle (.kts)](#gradle-kts-1)
    * [Usage](#usage)
  * [`Boost Domain`](#boost-domain)
    * [Configuration](#configuration-1)
      * [Maven](#maven-2)
      * [Gradle (.kts)](#gradle-kts-2)
    * [Usage](#usage-1)
  * [`Boost Serialization JSON`](#boost-serialization-json)
    * [Configuration](#configuration-2)
      * [Maven](#maven-3)
      * [Gradle (.kts)](#gradle-kts-3)
    * [Usage](#usage-2)
      * [Preconfigured `JSON` constant](#preconfigured-json-constant)
  * [`Boost Logging Core`](#boost-logging-core)
    * [Levels](#levels)
    * [Usage](#usage-3)
  * [`Boost Logging SLF4J`](#boost-logging-slf4j)
    * [Configuration](#configuration-3)
      * [Maven](#maven-4)
      * [Gradle (.kts)](#gradle-kts-4)
  * [`Boost EventBus`](#boost-eventbus)
    * [Configuration](#configuration-4)
      * [Maven](#maven-5)
      * [Gradle (.kts)](#gradle-kts-5)
  * [`Boost Kotest`](#boost-kotest)
    * [Configuration](#configuration-5)
      * [Maven](#maven-6)
      * [Gradle (.kts)](#gradle-kts-6)
  * [`Boost Kotest Mockk`](#boost-kotest-mockk)
    * [Configuration](#configuration-6)
      * [Maven](#maven-7)
      * [Gradle (.kts)](#gradle-kts-7)
    * [Usage](#usage-4)
  * [`Boost Kotest MockServer`](#boost-kotest-mockserver)
    * [Configuration](#configuration-7)
      * [Maven](#maven-8)
      * [Gradle (.kts)](#gradle-kts-8)
    * [Usage](#usage-5)
  * [`Boost Kotest TestContainers`](#boost-kotest-testcontainers)
    * [Configuration](#configuration-8)
      * [Maven](#maven-9)
      * [Gradle (.kts)](#gradle-kts-9)
    * [Usage](#usage-6)
  * [`Boost Kotest TestContainers Keycloak`](#boost-kotest-testcontainers-keycloak)
    * [Configuration](#configuration-9)
      * [Maven](#maven-10)
      * [Gradle (.kts)](#gradle-kts-10)
    * [Usage](#usage-7)
  * [`Boost Kotest TestContainers Postgres`](#boost-kotest-testcontainers-postgres)
    * [Configuration](#configuration-10)
      * [Maven](#maven-11)
      * [Gradle (.kts)](#gradle-kts-11)
    * [Usage](#usage-8)
  * [`Boost Kotest TestContainers RabbitMQ`](#boost-kotest-testcontainers-rabbitmq)
    * [Configuration](#configuration-11)
      * [Maven](#maven-12)
      * [Gradle (.kts)](#gradle-kts-12)
    * [Usage](#usage-9)

---

# Dependency Management

Use BOM dependency to manage the Kotlin Boost versions.

### Maven

```xml
<dependencyManagement>
  <dependencies>
    <dependency>
      <groupId>io.justdevit.kotlin</groupId>
      <artifactId>boost-bom</artifactId>
      <version>${kotlin-boost.version}</version>
      <type>pom</type>
      <scope>import</scope>
    </dependency>
  </dependencies>
</dependencyManagement>
```

### Gradle (.kts)

```kotlin
implementation(platform("io.justdevit.kotlin:boost-bom:$kotlinBoostVersion"))
```

---

# Modules

## `Boost Commons`

The module has a set of extensions for the core classes like `String`, `Collections`, `Boolean` and others.  
This extensions can bring some boost to your code to be more clean and readable.

### Configuration

#### Maven

```xml

<dependency>
  <groupId>io.justdevit.kotlin</groupId>
  <artifactId>boost-commons</artifactId>
</dependency>
```

#### Gradle (.kts)

```kotlin
implementation("io.justdevit.kotlin:boost-commons")
```

### Usage

The IntelliJ IDEA will suggest you useful function for your usecase.

**Constants:**

* `ISO8601_FORMATTER`
* `EUROPEAN_DATE_FORMATTER`
* `EUROPEAN_DATE_TIME_FORMATTER`

---

## `Boost Domain`

The module has a set of classes which is useful for DDD.

### Configuration

#### Maven

```xml

<dependency>
  <groupId>io.justdevit.kotlin</groupId>
  <artifactId>boost-domain</artifactId>
</dependency>
```

#### Gradle (.kts)

```kotlin
implementation("io.justdevit.kotlin:boost-domain")
```

### Usage

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
</dependency>
```

#### Gradle (.kts)

```kotlin
implementation("io.justdevit.kotlin:boost-serialization-json")
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
</dependency>
```

#### Gradle (.kts)

```kotlin
implementation("io.justdevit.kotlin:boost-logging-slf4j")
```

---

## `Boost EventBus`

The module is implementation of the simple Event Bus system.

### Configuration

#### Maven

```xml

<dependency>
  <groupId>io.justdevit.kotlin</groupId>
  <artifactId>boost-eventbus</artifactId>
</dependency>
```

#### Gradle (.kts)

```kotlin
implementation("io.justdevit.kotlin:boost-eventbus")
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
</dependency>
```

#### Gradle (.kts)

```kotlin
implementation("io.justdevit.kotlin:boost-kotest")
```

---

## `Boost Kotest Mockk`

The module is focused on perform writing test [Mockk Framework](https://mockk.io/).

### Configuration

#### Maven

```xml

<dependency>
  <groupId>io.justdevit.kotlin</groupId>
  <artifactId>boost-kotest-mockk</artifactId>
</dependency>
```

#### Gradle (.kts)

```kotlin
implementation("io.justdevit.kotlin:boost-kotest-mockk")
```

### Usage

* **Apply extension globally**
  ```kotlin
  object ProjectConfig : AbstractProjectConfig() {
      override fun extensions() =
          listOf(
              MockkExtension,
          )
  }
  ```
* **Clear All Mocks**
  ```kotlin
  class MyTest : FreeSpec({
      installMockkClearing()
      ...
  })
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
</dependency>
```

#### Gradle (.kts)

```kotlin
implementation("io.justdevit.kotlin:boost-kotest-mockserver")
```

### Usage

* **Apply extension globally**
  ```kotlin
  object ProjectConfig : AbstractProjectConfig() {
      override fun extensions() =
          listOf(
              MockServerExtension<E2eTest> { MOCK_SERVER in it.profiles },
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
</dependency>
```

#### Gradle (.kts)

```kotlin
implementation("io.justdevit.kotlin:boost-kotest-testcontainers")
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
</dependency>
```

#### Gradle (.kts)

```kotlin
implementation("io.justdevit.kotlin:boost-kotest-testcontainers-keycloak")
```

### Usage

* **Apply extension globally**
  ```kotlin
  object ProjectConfig : AbstractProjectConfig() {
      override fun extensions() =
          listOf(
              KeycloakExtension<MicronautTest> { "keycloak" in it.enveronments },
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
</dependency>
```

#### Gradle (.kts)

```kotlin
implementation("io.justdevit.kotlin:boost-kotest-testcontainers-postgres")
```

### Usage

* **Apply extension globally**
  ```kotlin
  object ProjectConfig : AbstractProjectConfig() {
      override fun extensions() =
          listOf(
              PostgresExtension<MicronautTest> { "postgres" in it.environments },
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
</dependency>
```

#### Gradle (.kts)

```kotlin
implementation("io.justdevit.kotlin:boost-kotest-testcontainers-rabbitmq")
```

### Usage

* **Apply extension globally**
  ```kotlin
  object ProjectConfig : AbstractProjectConfig() {
      override fun extensions() =
          listOf(
              RabbitMqExtension<MicronautTest> { "rabbitmq" in it.environments },
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
