dependencies {
    api(projects.testing.kotest)
    api(platform(libs.testcontainers.bom))
    api(libs.testcontainers)
}
