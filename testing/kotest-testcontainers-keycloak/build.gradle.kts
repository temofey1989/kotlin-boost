dependencies {
    api(rootProject.libs.testcontainers.keycloak)

    implementation(project(":commons"))
    implementation(project(":testing:kotest-testcontainers"))
    implementation(project(":serialization:serialization-json"))
    implementation(project(":testing:rest-assured"))
}
