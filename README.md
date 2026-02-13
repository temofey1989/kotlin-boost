![example event parameter](https://github.com/temofey1989/kotlin-boost/actions/workflows/build.yml/badge.svg?branch=main)
[![Code Quality](https://github.com/temofey1989/kotlin-boost/actions/workflows/code-quality.yml/badge.svg)](https://github.com/temofey1989/kotlin-boost/actions/workflows/code-quality.yml)
[![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](https://www.apache.org/licenses/LICENSE-2.0)

# Overview

Kotlin Boost is a set of libraries that can be used for boost of your project startup.

# Table of Contents

* [Dependency Management](#dependency-management)
* [Modules](#modules)
* [Build](#build)

---

# Dependency Management

To use Kotlin Boost in your project, use [Version Catalog](version-catalog/README.md).

---

# Modules

This repository is a multi-module project. Refer to individual module READMEs for details:

- [Commons](commons/README.md)
- [Domain](domain/README.md)
- Integration
  - [Event-Bus](integration/event-bus/README.md)
- Observability
  - [Logging Core](observability/logging-core/README.md)
  - [Logging SLF4J](observability/logging-slf4j/README.md)
- Persistence
  - [AWS S3](persistence/aws-s3/README.md)
- Serialization
  - [Serialization JSON](serialization/serialization-json/README.md)
- Testing
  - [Kotest](testing/kotest/README.md)
  - [Kotest Logging Asserts](testing/kotest-logging-asserts/README.md)
  - [Kotest MockK](testing/kotest-mockk/README.md)
  - [Kotest MockServer](testing/kotest-mockserver/README.md)
  - [Kotest Testcontainers (Core)](testing/kotest-testcontainers/README.md)
  - [Kotest Testcontainers Keycloak](testing/kotest-testcontainers-keycloak/README.md)
  - [Kotest Testcontainers Localstack](testing/kotest-testcontainers-localstack/README.md)
  - [Kotest Testcontainers Postgres](testing/kotest-testcontainers-postgres/README.md)
  - [Kotest Testcontainers RabbitMQ](testing/kotest-testcontainers-rabbitmq/README.md)
  - [Rest-Assured](testing/rest-assured/README.md)
- [Version Catalog](version-catalog/README.md)

---

# Build

To build the project locally:

```bash
./gradlew build
```

Run tests only:

```bash
./gradlew test
```

Publish to the local Maven repository (for trying out from another project):

```bash
./gradlew publishToMavenLocal
```

Useful tips:

- Use JDK 21 or newer.
- The project uses Gradle Version Catalog; see gradle/libs.versions.toml for managed versions.
