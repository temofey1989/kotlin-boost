dependencies {
    api(project(":boost-logging-core"))
    testImplementation(project(":boost-logging-slf4j"))
    with(rootProject) {
        api(libs.kotlin.coroutines.core)
        testImplementation(libs.awaitility)
    }
}
