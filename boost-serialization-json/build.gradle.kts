val kotlinSerializationVersion: String by project

dependencies {
    api(project(":boost-utils"))
    api("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinSerializationVersion")
}
