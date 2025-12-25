dependencies {
    api(rootProject.libs.testcontainers.rabbitmq)

    implementation(projects.commons)
    implementation(projects.testing.kotestTestcontainers)
}
