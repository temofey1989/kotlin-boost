dependencies {
    api(rootProject.libs.testcontainers.postgresql)

    implementation(projects.commons)
    implementation(projects.testing.kotestTestcontainers)
}
