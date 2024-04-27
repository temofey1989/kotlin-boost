import org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED
import org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED
import org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED
import java.time.Duration

plugins {
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.serialization") version "1.9.23"
    id("org.jmailen.kotlinter") version "4.1.1"
    `maven-publish`
    signing
    id("io.github.gradle-nexus.publish-plugin") version "2.0.0"
}

allprojects {

    repositories {
        mavenLocal()
        mavenCentral()
    }
}

subprojects {

    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.serialization")
    apply(plugin = "org.jmailen.kotlinter")

    val kotestVersion: String by project
    val mockkVersion: String by project

    dependencies {
        implementation(kotlin("stdlib"))

        testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
        testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
        testImplementation("io.kotest:kotest-property:$kotestVersion")

        testImplementation("io.mockk:mockk:$mockkVersion")
    }

    java.sourceCompatibility = JavaVersion.VERSION_17

    tasks {
        formatKotlin {
        }

        lintKotlin {
        }

        classes {
            dependsOn("formatKotlin")
        }

        compileKotlin {
            kotlinOptions {
                freeCompilerArgs =
                    listOf(
                        "-Xjsr305=strict",
                        "-Xcontext-receivers",
                    )
                jvmTarget = java.sourceCompatibility.toString()
            }
        }

        test {
            useJUnitPlatform()
            testLogging {
                events(PASSED, FAILED, SKIPPED)
            }
        }
    }

    apply(from = "${rootProject.projectDir.path}/gradle/release.gradle.kts")
}

tasks {
    named("clean") {
        dependsOn(gradle.includedBuild("boost-bom").task(":clean"))
    }
    named("build") {
        dependsOn(gradle.includedBuild("boost-bom").task(":build"))
    }
    named("publishToMavenLocal") {
        dependsOn(gradle.includedBuild("boost-bom").task(":publishToMavenLocal"))
    }
}

// Should be moved to release.gradle.kts
// See: https://github.com/gradle-nexus/publish-plugin/issues/81
// See: https://github.com/gradle-nexus/publish-plugin/issues/84
nexusPublishing {
    val sonatypeNexusUrl: String? by project
    val sonatypeSnapshotRepositoryUrl: String? by project
    val ossrhUsername: String? by project
    val ossrhPassword: String? by project
    transitionCheckOptions {
        maxRetries.set(100)
        delayBetween.set(Duration.ofSeconds(5))
    }
    repositories {
        sonatype {
            if (!sonatypeNexusUrl.isNullOrBlank()) {
                nexusUrl.set(uri(sonatypeNexusUrl!!))
            }
            if (!sonatypeSnapshotRepositoryUrl.isNullOrBlank()) {
                snapshotRepositoryUrl.set(uri(sonatypeSnapshotRepositoryUrl!!))
            }
            if (!ossrhUsername.isNullOrBlank()) {
                username.set(ossrhUsername!!)
            }
            if (!ossrhPassword.isNullOrBlank()) {
                password.set(ossrhPassword!!)
            }
        }
    }
}
