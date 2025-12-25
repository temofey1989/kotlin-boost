dependencies {
    api(projects.observability.loggingCore)
    api(libs.logback.classic)

    implementation(rootProject.libs.janino)
    implementation(rootProject.libs.slf4j.extension.jul)
    implementation(rootProject.libs.logstash.logback.encoder)
}
