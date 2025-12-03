package io.justdevit.kotlin.boost.aws.s3

/**
 * Represents an identifier for an object stored in a bucket in the S3 storage system.
 *
 * @property bucket The name of the S3 bucket where the object is stored.
 * @property key The unique key within the bucket identifying the object.
 */
data class ObjectId(val bucket: BucketName, val key: ObjectKey)

/**
 * Represents the name of an S3 bucket, ensuring that the name is non-blank.
 *
 * @property value The non-blank bucket name string.
 * @throws IllegalArgumentException If the bucket name is blank.
 */
@JvmInline
value class BucketName(val value: String) {
    init {
        require(value.isNotBlank()) { "Bucket name must not be blank." }
    }
}

/**
 * Represents the key of an S3 object, ensuring that the name is non-blank.
 *
 * @property value The non-blank object kye string.
 * @throws IllegalArgumentException If the object key is blank.
 */
@JvmInline
value class ObjectKey(val value: String) {
    init {
        require(value.isNotBlank()) { "Object key must not be blank." }
    }
}
