dependencies {
    api(rootProject.libs.testcontainers.keycloak)

    implementation(projects.commons)
    implementation(projects.testing.kotestTestcontainers)
    implementation(projects.serialization.serializationJson)
    implementation(projects.testing.restAssured)
}
