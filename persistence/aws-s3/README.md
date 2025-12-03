# Persistence - AWS S3

## Overview

Convenience extensions for AWS SDK v2 S3 Transfer Manager to upload, download, and copy objects.

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
kotlin-boost-persistence-aws-s3 = { group = 'io.justdevit.kotlin.boost', name = 'aws-s3', version.ref = 'kotlin-boost' }
```

```kotlin
// build.gradle.kts

dependencies {
  implementation(libs.kotlin.boost.persistence.aws.s3)
}
```

---

## Usage

```kotlin
val tm = S3TransferManager.create()
val bucket = Bucket("my-bucket")
val key = Key("path/file.txt")
val id = ObjectId(bucket, key)

tm.uploadFile(Paths.get("./file.txt"), id)
```

TBD...
