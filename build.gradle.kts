import org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED
import org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED
import org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.time.Duration

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlinter)
    alias(libs.plugins.nexus.publish)
    `maven-publish`
    signing
}

allprojects {

    repositories {
        mavenLocal()
        mavenCentral()
    }
}

subprojects {

    apply(
        plugin = rootProject.libs.plugins.kotlin.jvm
            .get()
            .pluginId,
    )
    apply(
        plugin = rootProject.libs.plugins.kotlin.serialization
            .get()
            .pluginId,
    )
    apply(
        plugin = rootProject.libs.plugins.kotlinter
            .get()
            .pluginId,
    )

    dependencies {
        implementation(rootProject.libs.kotlin.stdlib)
        testImplementation(rootProject.libs.bundles.testing)

        implementation("io.netty:netty-common:4.1.118.Final")
    }

    java.sourceCompatibility = JavaVersion.VERSION_21

    tasks {
        formatKotlin {
        }

        lintKotlin {
        }

        classes {
            dependsOn("formatKotlin")
        }

        compileKotlin {
            compilerOptions {
                freeCompilerArgs =
                    listOf(
                        "-Xjsr305=strict",
                        "-Xcontext-receivers",
                    )
                jvmTarget = JvmTarget.fromTarget(java.sourceCompatibility.toString())
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
