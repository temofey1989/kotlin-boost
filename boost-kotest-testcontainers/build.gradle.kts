val kotestExtensionTestcontainersVersion: String by project
val testcontainersVersion: String by project

dependencies {
    api(project(":boost-kotest"))
    api(project(":boost-shared"))

    // https://github.com/testcontainers/testcontainers-java/issues/8338
    @Suppress("VulnerableLibrariesLocal", "RedundantSuppression")
    api("org.testcontainers:testcontainers:$testcontainersVersion")

    api("io.kotest.extensions:kotest-extensions-testcontainers:$kotestExtensionTestcontainersVersion")
}
