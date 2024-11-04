val kotlinCoroutinesVersion: String by project
val ksuidVersion: String by project

dependencies {
    api(project(":boost-commons"))
    implementation("com.github.ksuid:ksuid:$ksuidVersion")
    compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinCoroutinesVersion")
    compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j:$kotlinCoroutinesVersion")
}
