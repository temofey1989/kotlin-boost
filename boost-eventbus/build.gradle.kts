dependencies {
    api(project(":boost-logging-core"))
    testImplementation(project(":boost-logging-slf4j"))
    api(rootProject.libs.kotlin.coroutines.core)
    testImplementation(rootProject.libs.awaitility)
}
