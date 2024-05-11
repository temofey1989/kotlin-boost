package io.justdevit.kotlin.boost.aws.s3

import kotlinx.coroutines.future.await
import software.amazon.awssdk.transfer.s3.model.Copy
import software.amazon.awssdk.transfer.s3.model.DirectoryDownload
import software.amazon.awssdk.transfer.s3.model.DirectoryUpload
import software.amazon.awssdk.transfer.s3.model.Download
import software.amazon.awssdk.transfer.s3.model.FileDownload
import software.amazon.awssdk.transfer.s3.model.FileUpload
import software.amazon.awssdk.transfer.s3.model.Upload

suspend fun Upload.await() = completionFuture().await()

suspend fun FileUpload.await() = completionFuture().await()

suspend fun DirectoryUpload.await() = completionFuture().await()

suspend fun <T> Download<T>.await() = completionFuture().await()

suspend fun FileDownload.await() = completionFuture().await()

suspend fun DirectoryDownload.await() = completionFuture().await()

suspend fun Copy.await() = completionFuture().await()
