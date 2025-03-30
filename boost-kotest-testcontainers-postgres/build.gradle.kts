dependencies {
    api(project(":boost-kotest-testcontainers"))
    with(rootProject) {
        api(libs.kotlin.reflect)
        api(libs.testcontainers.postgresql)
    }
}
