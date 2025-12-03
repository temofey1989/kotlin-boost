dependencies {
    api(rootProject.libs.testcontainers.localstack)

    implementation(project(":commons"))
    implementation(project(":testing:kotest-testcontainers"))
}
