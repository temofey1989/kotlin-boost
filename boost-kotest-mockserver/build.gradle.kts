val kotestExtensionMockServerVersion: String by project

dependencies {
    api(project(":boost-commons"))
    api(project(":boost-kotest"))
    api(kotlin("reflect"))
    @Suppress("VulnerableLibrariesLocal", "RedundantSuppression")
    api("io.kotest.extensions:kotest-extensions-mockserver:$kotestExtensionMockServerVersion")
}
