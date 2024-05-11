package io.justdevit.kotlin.boost.aws.s3

import io.justdevit.kotlin.boost.model.NonBlankString

data class ObjectId(val bucket: NonBlankString, val key: NonBlankString)
