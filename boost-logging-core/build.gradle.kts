dependencies {
    api(project(":boost-commons"))
    with(rootProject) {
        implementation(libs.ksuid)
        compileOnly(libs.kotlin.coroutines.core)
        compileOnly(libs.kotlin.coroutines.slf4j)
    }
}
