dependencies {
    api(rootProject.libs.testcontainers.postgresql)

    implementation(project(":commons"))
    implementation(project(":testing:kotest-testcontainers"))
}
