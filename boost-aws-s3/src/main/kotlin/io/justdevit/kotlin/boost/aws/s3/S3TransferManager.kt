package io.justdevit.kotlin.boost.aws.s3

import io.justdevit.kotlin.boost.model.NonBlankString
import software.amazon.awssdk.core.async.AsyncRequestBody
import software.amazon.awssdk.core.async.AsyncResponseTransformer
import software.amazon.awssdk.services.s3.model.CopyObjectRequest
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import software.amazon.awssdk.services.s3.model.GetObjectResponse
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.transfer.s3.S3TransferManager
import software.amazon.awssdk.transfer.s3.model.Copy
import software.amazon.awssdk.transfer.s3.model.CopyRequest
import software.amazon.awssdk.transfer.s3.model.DirectoryDownload
import software.amazon.awssdk.transfer.s3.model.DirectoryUpload
import software.amazon.awssdk.transfer.s3.model.Download
import software.amazon.awssdk.transfer.s3.model.DownloadDirectoryRequest
import software.amazon.awssdk.transfer.s3.model.DownloadFileRequest
import software.amazon.awssdk.transfer.s3.model.DownloadRequest
import software.amazon.awssdk.transfer.s3.model.DownloadRequest.UntypedBuilder
import software.amazon.awssdk.transfer.s3.model.FileDownload
import software.amazon.awssdk.transfer.s3.model.FileUpload
import software.amazon.awssdk.transfer.s3.model.ResumableFileDownload
import software.amazon.awssdk.transfer.s3.model.ResumableFileUpload
import software.amazon.awssdk.transfer.s3.model.Upload
import software.amazon.awssdk.transfer.s3.model.UploadDirectoryRequest
import software.amazon.awssdk.transfer.s3.model.UploadFileRequest
import software.amazon.awssdk.transfer.s3.model.UploadRequest
import java.nio.file.Path

// --- UPLOAD ---

fun S3TransferManager.upload(
    objectId: ObjectId,
    body: AsyncRequestBody,
    mutation: context(UploadRequest.Builder, PutObjectRequest.Builder)
    () -> Unit = {},
): Upload {
    val putRequestBuilder = PutObjectRequest.builder().apply {
        bucket(objectId.bucket.value)
        key(objectId.key.value)
    }
    val uploadRequestBuilder = UploadRequest.builder().apply {
        requestBody(body)
    }
    mutation(uploadRequestBuilder, putRequestBuilder)
    return upload(uploadRequestBuilder.putObjectRequest(putRequestBuilder.build()).build())
}

fun S3TransferManager.uploadFile(
    source: Path,
    objectId: ObjectId,
    mutation: context(UploadFileRequest.Builder, PutObjectRequest.Builder)
    () -> Unit = {},
): FileUpload {
    val putRequestBuilder = PutObjectRequest.builder().apply {
        bucket(objectId.bucket.value)
        key(objectId.key.value)
    }
    val uploadRequest = UploadFileRequest.builder().apply {
        source(source)
    }
    mutation(uploadRequest, putRequestBuilder)
    return uploadFile(uploadRequest.putObjectRequest(putRequestBuilder.build()).build())
}

fun S3TransferManager.resumeUploadFile(
    source: Path,
    objectId: ObjectId,
    mutation: context(UploadFileRequest.Builder, PutObjectRequest.Builder)
    () -> Unit = {},
): FileUpload {
    val putRequestBuilder = PutObjectRequest.builder().apply {
        bucket(objectId.bucket.value)
        key(objectId.key.value)
    }
    val uploadRequest = UploadFileRequest.builder().apply {
        source(source)
    }
    val resumableFileUpload = ResumableFileUpload.builder()
    mutation(uploadRequest, putRequestBuilder)
    return resumeUploadFile(
        resumableFileUpload
            .uploadFileRequest(
                uploadRequest
                    .putObjectRequest(
                        putRequestBuilder.build(),
                    ).build(),
            ).build(),
    )
}

fun S3TransferManager.uploadDirectory(
    source: Path,
    bucket: NonBlankString,
    mutation: UploadDirectoryRequest.Builder.() -> Unit = {},
): DirectoryUpload {
    val uploadRequest = UploadDirectoryRequest.builder().apply {
        source(source)
        bucket(bucket.value)
        mutation()
    }
    return uploadDirectory(uploadRequest.build())
}

// --- DOWNLOAD ---

fun <T> S3TransferManager.download(mutation: UntypedBuilder.() -> AsyncResponseTransformer<GetObjectResponse, T>): Download<T> {
    val builder = DownloadRequest.builder()
    val responseTransfer = builder.mutation()
    return download(builder.responseTransformer(responseTransfer).build())
}

fun S3TransferManager.downloadFile(
    objectId: ObjectId,
    destination: Path,
    mutation: context(DownloadFileRequest.Builder, GetObjectRequest.Builder)
    () -> Unit = {},
): FileDownload {
    val getObjectRequest = GetObjectRequest.builder().apply {
        bucket(objectId.bucket.value)
        key(objectId.key.value)
    }
    val downloadRequest = DownloadFileRequest.builder().apply {
        destination(destination)
    }
    mutation(downloadRequest, getObjectRequest)
    return downloadFile(downloadRequest.getObjectRequest(getObjectRequest.build()).build())
}

fun S3TransferManager.resumeDownloadFile(
    objectId: ObjectId,
    destination: Path,
    mutation: context(ResumableFileDownload.Builder, DownloadFileRequest.Builder, GetObjectRequest.Builder)
    () -> Unit = {},
): FileDownload {
    val getObjectRequest = GetObjectRequest.builder().apply {
        bucket(objectId.bucket.value)
        key(objectId.key.value)
    }
    val downloadRequest = DownloadFileRequest.builder().apply {
        destination(destination)
    }
    val resumableFileDownload = ResumableFileDownload.builder()
    mutation(resumableFileDownload, downloadRequest, getObjectRequest)
    return resumeDownloadFile(
        resumableFileDownload
            .downloadFileRequest(
                downloadRequest
                    .getObjectRequest(
                        getObjectRequest.build(),
                    ).build(),
            ).build(),
    )
}

fun S3TransferManager.downloadDirectory(
    bucket: NonBlankString,
    destination: Path,
    mutation: DownloadDirectoryRequest.Builder.() -> Unit = {},
): DirectoryDownload {
    val downloadRequest = DownloadDirectoryRequest.builder().apply {
        bucket(bucket.value)
        destination(destination)
        mutation()
    }
    return downloadDirectory(downloadRequest.build())
}

// --- COPY ---

fun S3TransferManager.coup(
    sourceId: ObjectId,
    destinationId: ObjectId,
    mutation: context(CopyRequest.Builder, CopyObjectRequest.Builder)
    () -> Unit = {},
): Copy {
    val copyObjectRequest = CopyObjectRequest.builder().apply {
        sourceBucket(sourceId.bucket.value)
        sourceKey(sourceId.key.value)
        destinationBucket(destinationId.bucket.value)
        destinationKey(destinationId.key.value)
    }
    val copyRequest = CopyRequest.builder()
    mutation(copyRequest, copyObjectRequest)
    return copy(copyRequest.copyObjectRequest(copyObjectRequest.build()).build())
}
