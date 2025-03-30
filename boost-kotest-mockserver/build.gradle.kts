dependencies {
    api(project(":boost-commons"))
    api(project(":boost-kotest"))
    with(rootProject) {
        api(libs.kotlin.reflect)
        api(libs.kotest.extensions.mockserver)
    }
}
