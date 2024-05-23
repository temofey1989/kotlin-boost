val mockkVersion: String by project

dependencies {
    api(project(":boost-kotest"))
    api(kotlin("reflect"))
    api("io.mockk:mockk:$mockkVersion")
}
