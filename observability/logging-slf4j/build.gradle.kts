dependencies {
    api(project(":observability:logging-core"))

    implementation(rootProject.libs.logback.classic)
    implementation(rootProject.libs.janino)
    implementation(rootProject.libs.slf4j.extension.jul)
    implementation(rootProject.libs.logstash.logback.encoder)
}
