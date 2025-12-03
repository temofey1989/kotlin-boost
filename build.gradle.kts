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
    if (project.name != "version-catalog") {

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
            testImplementation(rootProject.libs.bundles.testing.kotest)
        }

        java.sourceCompatibility = JavaVersion.VERSION_21

        tasks {
            kotlinter {
            }

            classes {
                dependsOn("formatKotlin")
            }

            compileKotlin {
                compilerOptions {
                    jvmTarget = JvmTarget.fromTarget(java.sourceCompatibility.majorVersion)
                    freeCompilerArgs =
                        listOf(
                            "-Xjsr305=strict",
                            "-Xcontext-parameters",
                        )
                }
            }

            test {
                useJUnitPlatform()
                testLogging {
                    events(PASSED, FAILED, SKIPPED)
                }
            }
        }
    }

    if (project.subprojects.isEmpty()) {
        apply(from = "${rootProject.projectDir.path}/gradle/release.gradle.kts")
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
            nexusUrl.set(uri(sonatypeNexusUrl ?: "https://ossrh-staging-api.central.sonatype.com/service/local/"))
            snapshotRepositoryUrl.set(uri(sonatypeSnapshotRepositoryUrl ?: "https://central.sonatype.com/repository/maven-snapshots/"))
            if (!ossrhUsername.isNullOrBlank()) {
                username.set(ossrhUsername!!)
            }
            if (!ossrhPassword.isNullOrBlank()) {
                password.set(ossrhPassword!!)
            }
        }
    }
}
