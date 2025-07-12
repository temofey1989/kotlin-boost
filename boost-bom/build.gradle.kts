import java.net.HttpURLConnection
import java.net.URI
import java.time.Duration

plugins {
    `java-platform`
    `maven-publish`
    signing
    alias(libs.plugins.nexus.publish)
}

version = (
    gradle.parent
        ?.rootProject
        ?.version ?: project.version
).toString().trim()

javaPlatform {
    allowDependencies()
}

dependencies {
    constraints {
        if (gradle.parent != null) {
            gradle.parent!!.rootProject.subprojects.forEach {
                if (it.name != "boost-bom") {
                    api("${it.group}:${it.name}:${it.version}")
                }
            }
        }
    }
}

fun prop(name: String, defaultValue: String = ""): String {
    val envName = name.replace(".", "_").uppercase()
    return System.getenv(envName) ?: project.findProperty(name)?.toString() ?: defaultValue
}

val projectDescription: String by lazy { prop("project.description", project.description ?: "") }

val scmUrl: String by lazy { prop("scm.url") }

val scmConnection: String by lazy { prop("scm.connection") }

val scmDeveloperConnection: String by lazy { prop("scm.developer.connection") }

val licenseName: String by lazy { prop("license.name") }

val licenseUrl: String by lazy { prop("license.url") }

val developerId: String by lazy { prop("developer.id") }

val developerName: String by lazy { prop("developer.name") }

val developerEmail: String by lazy { prop("developer.email") }

fun alreadyPublished(): Boolean {
    val group = project.group
        .toString()
        .trim()
    val artifact = project.name.trim()
    val version = (
        gradle.parent
            ?.rootProject
            ?.version ?: project.version
    ).toString().trim()
    if (version.isBlank()) {
        throw IllegalStateException("No version found for BOM.")
    }
    val extension = "pom"
    val baseUrl = "https://repo1.maven.org/maven2"
    val url = "$baseUrl/${group.replace('.', '/')}/$artifact/$version/$artifact-$version.$extension"

    val artifactUrl = URI.create(url).toURL()
    val connection = artifactUrl.openConnection() as HttpURLConnection
    connection.requestMethod = "HEAD"
    connection.connect()
    val responseCode = connection.responseCode
    connection.disconnect()

    return (responseCode == HttpURLConnection.HTTP_OK).also {
        println("INFO: Artifact '$group:$artifact:$version' existence: $it")
    }
}

val ci: Boolean by lazy { System.getenv("CI")?.toBoolean() ?: false }

if (ci && !alreadyPublished()) {
    println("INFO: Applying Maven Publication for project: ${project.name}")

    publishing {
        publications {
            create<MavenPublication>("maven") {
                from(components["javaPlatform"])
                pom {
                    name.set("${project.group}:${project.name}")
                    description.set(projectDescription)
                    url.set(scmUrl)
                    licenses {
                        license {
                            name.set(licenseName)
                            url.set(licenseUrl)
                        }
                    }
                    developers {
                        developer {
                            id.set(developerId)
                            name.set(developerName)
                            email.set(developerEmail)
                        }
                    }
                    scm {
                        url.set(scmUrl)
                        connection.set(scmConnection)
                        developerConnection.set(scmDeveloperConnection)
                    }
                }
                suppressPomMetadataWarningsFor("runtimeElements")
            }
        }
    }

    configure<SigningExtension> {
        val signingKeyId: String? by project
        val signingKey: String? by project
        val signingPassword: String? by project
        useInMemoryPgpKeys(signingKeyId, signingKey, signingPassword)

        val publishing = project.extensions["publishing"] as PublishingExtension
        sign(publishing.publications["maven"])
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
