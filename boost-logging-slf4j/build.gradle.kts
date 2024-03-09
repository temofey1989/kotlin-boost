val janinoVersion: String by project
val julToSlf4jVersion: String by project
val logbackVersion: String by project
val logstashLogbackEncoderVersion: String by project

dependencies {
    api(project(":boost-logging-core"))

    compileOnly("ch.qos.logback:logback-classic:$logbackVersion")
    compileOnly("org.codehaus.janino:janino:$janinoVersion")
    compileOnly("org.slf4j:jul-to-slf4j:$julToSlf4jVersion")
    implementation("net.logstash.logback:logstash-logback-encoder:$logstashLogbackEncoderVersion")
}
