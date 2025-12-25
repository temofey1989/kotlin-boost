dependencies {
    api(projects.testing.kotest)
    api(platform(rootProject.libs.testcontainers.bom))
    api(rootProject.libs.testcontainers)
}
