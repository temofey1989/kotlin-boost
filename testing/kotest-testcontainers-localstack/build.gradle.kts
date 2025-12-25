dependencies {
    api(rootProject.libs.testcontainers.localstack)

    implementation(projects.commons)
    implementation(projects.testing.kotestTestcontainers)
}
