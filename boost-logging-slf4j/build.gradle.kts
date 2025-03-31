dependencies {
    api(project(":boost-logging-core"))
    api(rootProject.libs.logback.classic)
    api(rootProject.libs.janino)
    api(rootProject.libs.slf4j.extension.jul)
    api(rootProject.libs.logstash.logback.encoder)
}
