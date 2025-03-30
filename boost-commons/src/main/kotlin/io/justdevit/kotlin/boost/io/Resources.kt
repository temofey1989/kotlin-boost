package io.justdevit.kotlin.boost.io

import java.io.File
import java.net.URI
import java.net.URL
import java.nio.file.FileSystems
import java.nio.file.Files

/**
 * Lists all resource files present in the specified resource directory.
 *
 * This function attempts to locate the specified directory from the application's classpath,
 * and retrieves a list of file names contained within it. It supports both file system
 * and JAR protocols.
 *
 * @param directory The path to the directory within the resources to list files from.
 *                  This path should be relative to the classpath's root.
 *                  For example, "someFolder/subFolder".
 * @return A list of file names present in the specified directory.
 *
 * @throws IllegalArgumentException If the specified directory is not found.
 * @throws IllegalStateException If the resource protocol is unsupported.
 */
fun listResourceFiles(directory: String): List<String> {
    val resource: URL =
        Thread
            .currentThread()
            .contextClassLoader
            .getResource(directory)
            ?: throw IllegalArgumentException("Directory not found: $directory")

    return if (resource.protocol == "file") {
        File(resource.toURI())
            .walk()
            .filter { it.isFile }
            .map { it.name }
            .toList()
    } else if (resource.protocol == "jar") {
        val path = resource.path.substringBeforeLast("!") + "!/$directory"
        val uri = URI.create("jar:$path")
        FileSystems.newFileSystem(uri, emptyMap<String, Any>()).use { fs ->
            Files
                .walk(fs.getPath(directory))
                .filter { Files.isRegularFile(it) }
                .map { it.fileName.toString() }
                .toList()
        }
    } else {
        throw IllegalStateException("Unsupported resource protocol: ${resource.protocol}")
    }
}
