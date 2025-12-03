dependencies {
    api(rootProject.libs.testcontainers.rabbitmq)

    implementation(project(":commons"))
    implementation(project(":testing:kotest-testcontainers"))
}
