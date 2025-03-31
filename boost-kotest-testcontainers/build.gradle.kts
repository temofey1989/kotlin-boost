dependencies {
    api(project(":boost-commons"))
    api(project(":boost-kotest"))
    api(platform(rootProject.libs.testcontainers.bom))
    api(rootProject.libs.kotest.extensions.testcontainers)
}
