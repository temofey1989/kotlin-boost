val janinoVersion: String by project
val julToSlf4jVersion: String by project
val logbackVersion: String by project
val logstashLogbackEncoderVersion: String by project

dependencies {
    api(project(":boost-logging-core"))

    api("ch.qos.logback:logback-classic:$logbackVersion")
    api("org.codehaus.janino:janino:$janinoVersion")
    api("org.slf4j:jul-to-slf4j:$julToSlf4jVersion")
    api("net.logstash.logback:logstash-logback-encoder:$logstashLogbackEncoderVersion")
}
