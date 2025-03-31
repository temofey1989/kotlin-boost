dependencies {
    api(project(":boost-commons"))
    api(project(":boost-kotest"))
    api(platform(rootProject.libs.testcontainers.bom)).apply {
        api("org.apache.commons:commons-compress:1.26.0")
    }
    api(rootProject.libs.testcontainers)
}
