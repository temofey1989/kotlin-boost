val kotlinSerializationVersion: String by project
val kotlinCoroutinesVersion: String by project

dependencies {
    api(project(":boost-commons"))
    api(project(":boost-serialization-json"))
    api("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinSerializationVersion")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinCoroutinesVersion")
}
