dependencies {
    api(libs.testcontainers.postgresql)

    implementation(projects.commons)
    implementation(projects.testing.kotestTestcontainers)
}
