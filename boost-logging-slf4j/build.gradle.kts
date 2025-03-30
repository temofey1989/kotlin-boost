dependencies {
    api(project(":boost-logging-core"))
    with(rootProject) {
        api(libs.logback.classic)
        api(libs.janino)
        api(libs.slf4j.extension.jul)
        api(libs.logstash.logback.encoder)
    }
}
