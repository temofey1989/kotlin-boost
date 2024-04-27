val kotlinCoroutinesVersion: String by project

dependencies {
    api(project(":boost-utils"))
    compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinCoroutinesVersion")
    compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j:$kotlinCoroutinesVersion")
}
