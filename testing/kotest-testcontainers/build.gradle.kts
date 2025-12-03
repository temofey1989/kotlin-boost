dependencies {
    api(project(":testing:kotest"))
    api(platform(rootProject.libs.testcontainers.bom))
    api(rootProject.libs.testcontainers)
}
