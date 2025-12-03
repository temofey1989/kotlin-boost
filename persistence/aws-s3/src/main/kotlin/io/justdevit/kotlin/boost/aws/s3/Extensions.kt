package io.justdevit.kotlin.boost.aws.s3

import kotlinx.coroutines.future.await
import software.amazon.awssdk.transfer.s3.model.CompletedCopy
import software.amazon.awssdk.transfer.s3.model.CompletedDirectoryDownload
import software.amazon.awssdk.transfer.s3.model.CompletedDirectoryUpload
import software.amazon.awssdk.transfer.s3.model.CompletedDownload
import software.amazon.awssdk.transfer.s3.model.CompletedFileDownload
import software.amazon.awssdk.transfer.s3.model.CompletedFileUpload
import software.amazon.awssdk.transfer.s3.model.CompletedUpload
import software.amazon.awssdk.transfer.s3.model.Copy
import software.amazon.awssdk.transfer.s3.model.DirectoryDownload
import software.amazon.awssdk.transfer.s3.model.DirectoryUpload
import software.amazon.awssdk.transfer.s3.model.Download
import software.amazon.awssdk.transfer.s3.model.FileDownload
import software.amazon.awssdk.transfer.s3.model.FileUpload
import software.amazon.awssdk.transfer.s3.model.Upload

/**
 * Awaits the completion of an S3 upload operation and returns the result.
 *
 * @return The result of the upload operation.
 */
suspend fun Upload.await(): CompletedUpload = completionFuture().await()

/**
 * Awaits the completion of an S3 file-upload operation and returns the result.
 *
 * @return The result of the upload operation.
 */
suspend fun FileUpload.await(): CompletedFileUpload = completionFuture().await()

/**
 * Awaits the completion of an S3 directory-upload operation and returns the result.
 *
 * @return The result of the upload operation.
 */
suspend fun DirectoryUpload.await(): CompletedDirectoryUpload = completionFuture().await()

/**
 * Awaits the completion of an S3 download operation and returns the result.
 *
 * @return The result of the download operation.
 */
suspend fun <T> Download<T>.await(): CompletedDownload<T> = completionFuture().await()

/**
 * Awaits the completion of an S3 file-download operation and returns the result.
 *
 * @return The result of the download operation.
 */
suspend fun FileDownload.await(): CompletedFileDownload = completionFuture().await()

/**
 * Awaits the completion of an S3 directory-download operation and returns the result.
 *
 * @return The result of the download operation.
 */
suspend fun DirectoryDownload.await(): CompletedDirectoryDownload = completionFuture().await()

/**
 * Awaits the completion of an S3 copy operation and returns the result.
 *
 * @return The result of the copy operation.
 */
suspend fun Copy.await(): CompletedCopy = completionFuture().await()
