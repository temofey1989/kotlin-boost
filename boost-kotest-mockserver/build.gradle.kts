dependencies {
    api(project(":boost-commons"))
    api(project(":boost-kotest"))
    api(rootProject.libs.kotlin.reflect)
    api(rootProject.libs.kotest.extensions.mockserver).also {
        api("org.yaml:snakeyaml:2.0")
        api("io.netty:netty-common:4.1.118.Final")
        api("io.netty:netty-codec-http:4.1.108.Final")
        api("io.netty:netty-codec-http2:4.1.100.Final")
        api("org.bouncycastle:bcprov-jdk18on:1.78")
        api("net.minidev:json-smart:2.5.2")
        api("commons-io:commons-io:2.14.0")
        api("com.google.guava:guava:32.0.1-android")
        api("com.jayway.jsonpath:json-path:2.9.0")
        api("org.xmlunit:xmlunit-core:2.10.0")
    }
}
