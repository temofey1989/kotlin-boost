dependencies {
    api(rootProject.libs.kotlin.coroutines.core)
    implementation(project(":observability:logging-core"))

    testImplementation(project(":observability:logging-slf4j"))
    testImplementation(rootProject.libs.awaitility)
}
