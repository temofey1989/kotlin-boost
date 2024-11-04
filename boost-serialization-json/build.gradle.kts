val kotlinSerializationVersion: String by project

dependencies {
    api(project(":boost-commons"))
    api("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinSerializationVersion")
}
