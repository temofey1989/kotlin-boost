val kotlinCoroutinesVersion: String by project

dependencies {
    api(project(":boost-logging-core"))
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinCoroutinesVersion")
    testImplementation(project(":boost-logging-slf4j"))
}
