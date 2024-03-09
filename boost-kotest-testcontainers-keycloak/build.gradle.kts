val keycloakTestcontainerVersion: String by project

apply(plugin = "org.jetbrains.kotlin.plugin.serialization")

dependencies {
    api(project(":boost-serialization-json"))
    api(project(":boost-kotest-testcontainers"))
    api(kotlin("reflect"))
    @Suppress("VulnerableLibrariesLocal", "RedundantSuppression")
    api("com.github.dasniko:testcontainers-keycloak:$keycloakTestcontainerVersion")
}
