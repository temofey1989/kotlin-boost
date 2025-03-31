apply(
    plugin = rootProject.libs.plugins.kotlin.serialization
        .get()
        .pluginId,
)

dependencies {
    api(project(":boost-commons"))
    api(project(":boost-serialization-json"))
    api(project(":boost-kotest-testcontainers"))
    api(project(":boost-rest-assured"))
    api(rootProject.libs.kotlin.reflect)
    api(rootProject.libs.testcontainers.keycloak)
}
