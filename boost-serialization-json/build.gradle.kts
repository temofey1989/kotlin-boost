val kotlinSerializationVersion: String by project

dependencies {
    api(project(":boost-shared"))
    api("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinSerializationVersion")
}
