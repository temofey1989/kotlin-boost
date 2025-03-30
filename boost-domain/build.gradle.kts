dependencies {
    api(project(":boost-commons"))
    with(rootProject) {
        api(libs.ksuid)
        api(libs.kotlin.coroutines.core)
    }
}
