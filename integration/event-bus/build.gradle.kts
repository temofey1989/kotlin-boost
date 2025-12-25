dependencies {
    api(libs.kotlin.coroutines.core)
    implementation(projects.observability.loggingCore)

    testImplementation(projects.observability.loggingSlf4j)
    testImplementation(libs.awaitility)
}
