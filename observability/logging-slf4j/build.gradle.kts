dependencies {
    api(projects.observability.loggingCore)
    api(libs.logback.classic)

    implementation(libs.janino)
    implementation(libs.slf4j.extension.jul)
    implementation(libs.logstash.logback.encoder)
}
