dependencies {
    api(project(":boost-kotest"))
    with(rootProject) {
        api(libs.kotlin.reflect)
        api(libs.mockk)
    }
}
