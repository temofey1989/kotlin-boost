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
    with(rootProject) {
        api(libs.kotlin.reflect)
        api(libs.testcontainers.keycloak)
    }
}
