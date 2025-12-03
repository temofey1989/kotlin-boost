package io.justdevit.kotlin.boost.aws.s3

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

/**
 * Uploads an object to S3.
 *
 * @param objectId The identifier of the object to upload.
 * @param body The content to upload as an [AsyncRequestBody].
 * @param mutation A lambda to customize the [UploadRequest.Builder] and [PutObjectRequest.Builder].
 * @return An [Upload] object representing the initiated upload operation.
 */
fun S3TransferManager.upload(
    objectId: ObjectId,
    body: AsyncRequestBody,
    mutation: context(UploadRequest.Builder, PutObjectRequest.Builder) () -> Unit = {},
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

/**
 * Uploads a file to an S3 bucket.
 *
 * @param source The path to the source file to be uploaded.
 * @param objectId The identifier for the object to be created in the S3 bucket.
 * @param mutation A lambda to customize the upload and put object request builders.
 * @return A [FileUpload] instance representing the S3 file upload operation.
 */
fun S3TransferManager.uploadFile(
    source: Path,
    objectId: ObjectId,
    mutation: context(UploadFileRequest.Builder, PutObjectRequest.Builder) () -> Unit = {},
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

/**
 * Resumes upload of a file to an S3 bucket.
 *
 * @param source The path to the source file to be uploaded.
 * @param objectId The identifier for the object to be created in the S3 bucket.
 * @param mutation A lambda to customize the upload and put object request builders.
 * @return A [FileUpload] instance representing the S3 file upload operation.
 */
fun S3TransferManager.resumeUploadFile(
    source: Path,
    objectId: ObjectId,
    mutation: context(UploadFileRequest.Builder, PutObjectRequest.Builder) () -> Unit = {},
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

/**
 * Uploads a directory to an S3 bucket.
 *
 * @param source The path to the source file to be uploaded.
 * @param bucket The name of the S3 bucket where the directory will be uploaded.
 * @param mutation A lambda to customize the upload and put object request builders.
 * @return A [DirectoryUpload] instance representing the S3 directory upload operation.
 */
fun S3TransferManager.uploadDirectory(
    source: Path,
    bucket: BucketName,
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

/**
 * Initiates a download operation from Amazon S3.
 *
 * @param mutation A lambda used to configure the download operation.
 * @return An instance of [Download] representing the initiated download operation.
 */
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

/**
 * Resumes a download operation from Amazon S3.
 *
 * @param objectId The identifier of the object to download.
 * @param destination The destination path where the downloaded file will be saved.
 * @param mutation A lambda used to configure the download operation.
 * @return An instance of [FileDownload] representing the resumed download operation.
 */
fun S3TransferManager.resumeDownloadFile(
    objectId: ObjectId,
    destination: Path,
    mutation: context(ResumableFileDownload.Builder, DownloadFileRequest.Builder, GetObjectRequest.Builder) () -> Unit = {},
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

/**
 * Initiates a directory-download operation from Amazon S3.
 *
 * @param bucket The name of the S3 bucket where the directory will be downloaded.
 * @param destination The destination path where the downloaded directory will be saved.
 * @param mutation A lambda used to configure the download operation.
 * @return An instance of [Download] representing the initiated download operation.
 */
fun S3TransferManager.downloadDirectory(
    bucket: BucketName,
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

/**
 * Initiates the copying of an object from a source location to a destination location within S3 storage.
 *
 * @param sourceId The identifier of the source object, including the bucket name and object key.
 * @param destinationId The identifier of the destination object, including the bucket name and object key.
 * @param mutation A lambda to modify the copy request and copy object request before execution.
 * @return An instance of [Copy] representing the initiated copy operation.
 */
fun S3TransferManager.copy(
    sourceId: ObjectId,
    destinationId: ObjectId,
    mutation: context(CopyRequest.Builder, CopyObjectRequest.Builder) () -> Unit = {},
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
