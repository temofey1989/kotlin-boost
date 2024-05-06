val testcontainersVersion: String by project

dependencies {
    api(project(":boost-kotest-testcontainers"))
    api(kotlin("reflect"))
    api("org.testcontainers:localstack:$testcontainersVersion")
}
