dependencies {
    api(project(":boost-commons"))
    api(project(":boost-kotest"))
    with(rootProject) {
        api(platform(libs.testcontainers.bom))
        api(libs.kotest.extensions.testcontainers)
    }
}
